package se.umu.yarn

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_call.*
import se.umu.yarn.model.Const.*
import se.umu.yarn.model.JavascriptInterface
import java.util.*


/**
 * Activity for handling an ongoing call between peers.
 * Uses Firebase to setup the call, and connects with
 * JavaScript to handle code running in an integrated web view.
 */
class CallActivity : AppCompatActivity() {

    // Needed for creating peer connection
    private var localUserAccountID = ""
    private var conversationTopic = ""
    private var conversationRoomID = ""
    private var isPeerConnected = false

    // Sets the root reference for the db to be "topics"
    private var firebaseRef = Firebase.database.getReference("topics")

    // For video / audio controls
    private var useMic = true
    private var useVideo = true

    /**
     * Get intent data and setup the UI and back-end systems.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        val localUserAccount = intent.getParcelableExtra<GoogleSignInAccount>("account")!!
        localUserAccountID = generateUniqueID()  //TODO: change back to --> localUserAccount.id!!
        conversationTopic = intent.getStringExtra("conversationTopic")!!

        setupUIListeners()
        setupWebViewClient()
        loadVideoCallHTML()
    }

    /**
     * Set up all the listeners used for the UI buttons etc.
     */
    private fun setupUIListeners() {
        // Call underlying call.js function for toggling the local microphone audio
        toggleMicBtn.setOnClickListener {
            useMic = !useMic
            callJavascriptFunction("javascript:toggleAudio(\"${useMic}\")")
            toggleMicBtn.setImageResource(if (useMic) R.drawable.ic_mic else R.drawable.ic_mic_off )
            toggleMicText.setText(if (useMic) R.string.mic_on else R.string.mic_off)
        }

        // Call underlying call.js function for toggling the local video stream
        toggleVideoBtn.setOnClickListener {
            useVideo = !useVideo
            callJavascriptFunction("javascript:toggleVideo(\"${useVideo}\")")
            toggleVideoBtn.setImageResource(if (useVideo) R.drawable.ic_videocam else R.drawable.ic_videocam_off )
            toggleVideoText.setText(if (useVideo) R.string.video_on else R.string.video_off)
        }

        sendPhotoBtn.setOnClickListener {
            Toast.makeText(this, "Sorry, sending photos is not supported right now!", Toast.LENGTH_LONG).show()
        }

        // When pressed, ask if user really wants to end the call
        endCallBtn.setOnClickListener {
            showEndCallDialog()
        }
    }

    /**
     * Setup the web view used to display the WebRTC video and audio.
     * Automatically grants browser permissions, as user should already
     * have granted those permissions in-app previously.
     * Enables JavaScript and JS-interface to communicate with call.js.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebViewClient() {

        webView.webChromeClient = object: WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest?) {
                request?.grant(request.resources)
            }
        }
        webView.settings.javaScriptEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        // Used to enable JavaScript to call Java/Kotlin functions.
        // The name "Android" will be used in call.js to call the onPeerConnected() method.
        webView.addJavascriptInterface(JavascriptInterface(this), "Android")
    }

    /**
     * Load the HTML file and init the peer once the page
     * has been loaded.
     */
    private fun loadVideoCallHTML() {
        // Web page is a local assets.
        val filePath = "file:android_asset/call.html"
        webView.loadUrl(filePath)
        webView.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                initPeer()
            }
        }
    }

    /**
     * Initialize the peer by calling the JavaScript-function init() from call.js
     * with the unique user id.
     */
    private fun initPeer() {
        localUserAccountID = generateUniqueID()
        callJavascriptFunction("javascript:init(\"${localUserAccountID}\")")
    }

    /**
     * Sends a call req. to the remote user(name) by instantiating
     * a "incoming" call field into the remote users Firebase data.
     * Needs to have a connection established (using PeerJS etc)
     */
    private fun sendCallRequest() {
        if (!isPeerConnected) {
            Toast.makeText(this, "You're not connected. Check your internet", Toast.LENGTH_LONG).show()
            return
        }

        // Peer is connected, send call request to remote user with the
        // value of the local username so they know who is calling.
        firebaseRef.child(conversationTopic).child(conversationRoomID)
            .child(INCOMING_CALLER).setValue(localUserAccountID)

        // Observe if the remote user has accepted the call.
        firebaseRef.child(conversationTopic).child(conversationRoomID)
            .child(CALL_ACCEPTED).addValueEventListener(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    // Snapshot will be true if the remote user has accepted the call.
                    if (snapshot.value.toString() == "true") {
                        listenForConnectionId()
                    }
                }
            })
    }

    /**
     * Observe the connection ID (their unique ID) of the remote user,
     * which is then used to start the call by calling JS func.
     */
    private fun listenForConnectionId() {
        // Child "connectionId" needs to be the same string as in onCallRequest()
        firebaseRef.child(conversationTopic).child(conversationRoomID)
            .child(CONVERSATION_OWNER).addValueEventListener(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value == null)
                        return
                    showChatControls()
                    callJavascriptFunction("javascript:startCall(\"${snapshot.value}\")")
                }
            })
    }

    /**
     * When a call request gets sent to the local user, show controls to
     * answer/decline the call and setup their listeners.
     */
    private fun onCallRequest(caller: String?) {
        if (caller == null) return

        // Accept the call.
        firebaseRef.child(conversationTopic).child(conversationRoomID)
            .child(CALL_ACCEPTED).setValue(true)

        // To make sure no one else tries to join (needs to be checked in ConversationFragment)
        firebaseRef.child(conversationTopic).child(conversationRoomID)
            .child(OPEN_TO_JOIN).setValue(false)

        // Set the local user Id to db so caller knows who to start the call with.
        firebaseRef.child(conversationTopic).child(conversationRoomID)
            .child(CONVERSATION_OWNER).setValue(localUserAccountID)

        showChatControls() // change to appropriate UI elements
    }

    /**
     * Show on/off control for the video and microphone,
     * and hide call control that are in the way.
     */
    private fun showChatControls() {
        waitingToJoinText.visibility = View.GONE
        chatControlLayout.visibility = View.VISIBLE
    }

    /**
     * Generates a unique random ID
     * Used to create unique chat rooms etc.
     */
    private fun generateUniqueID(): String {
        return UUID.randomUUID().toString()
    }

    /**
     * Helper method for calling JavaScript functions (in call.js).
     * The functions will be provided as a string, ex; "javascript:foo()"
     * NOTE: evaluateJavascript() is called asynchronously on a separate thread.
     */
    private fun callJavascriptFunction(functionString: String) {
        webView.post { webView.evaluateJavascript(functionString, null) }
    }

    /**
     * This method will be called from the JavascriptInterface class
     * (originally from call.js) to connect the Java code with the
     * WebRTC back end when two peers begin a connection.
     */
    fun onPeerConnected() {
        isPeerConnected = true

        // NOTE! This intent extra is mandatory.
        // Decides if the app should wait for someone to answer the call,
        // or if the call should be connected and started.
        if (intent.getBooleanExtra("isNewConversation", false)) {
            createNewTopicChatroom()
        } else {
            conversationRoomID = intent.getStringExtra("roomId")!!
            sendCallRequest()
        }
    }

    /**
     * Creates a new chatroom for the given topic and waits for
     * someone to connect to it.
     */
    private fun createNewTopicChatroom() {
        conversationRoomID = generateUniqueID();

        // Will tell other users that this conversation is open to join.
        firebaseRef.child(conversationTopic).child(conversationRoomID)
            .child(OPEN_TO_JOIN).setValue(true)

        // Is easier to get the room id this way instead of from the key.
        firebaseRef.child(conversationTopic).child(conversationRoomID)
            .child(ROOM_ID).setValue(conversationRoomID)

        // Listen for remote user to join the conversation
        // by sending a call request to the local user.
        firebaseRef.child(conversationTopic).child(conversationRoomID)
            .child(INCOMING_CALLER).addValueEventListener(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                // Will be called when web page is loaded.
                override fun onDataChange(snapshot: DataSnapshot) {
                    onCallRequest(snapshot.value as? String)
                }
            })
    }

    /**
     * Show an AlertDialog prompting the user to confirm that
     * they want to exit the app.
     */
    private fun showEndCallDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle("End call?")
        builder.setMessage("Are you sure you want to end the call?")
        // if positive, finish the activity, else do nothing.
        builder.setPositiveButton("Confirm") { _, _ -> finish() }
        builder.setNegativeButton(android.R.string.cancel) { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    /**
     * If user presses the back button, tell the activity that
     * it's done and should be closed.
     */
    override fun onBackPressed() {
        showEndCallDialog()
    }

    /**
     * When the Activity gets destroyed, remove the Firebase data regarding
     * the user and exit the webpage.
     */
    override fun onDestroy() {
        firebaseRef.child(conversationTopic).child(conversationRoomID).setValue(null)
        webView.loadUrl("about:blank")
        super.onDestroy()
    }
}
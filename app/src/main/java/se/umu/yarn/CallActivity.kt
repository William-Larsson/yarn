package se.umu.yarn

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_call.*
import se.umu.yarn.model.JavascriptInterface
import java.util.*


/**
 * Activity for handling an ongoing call between peers.
 * Uses Firebase to setup the call, and connects with
 * JavaScript to handle code running in an integrated web view.
 */
class CallActivity : AppCompatActivity() {

    private var localUsername = ""
    private var remoteUsername = ""
    private var uniqueId = ""
    private var isPeerConnected = false

    private var firebaseRef = Firebase.database.getReference("users")

    private var useMic = true
    private var useVideo = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        localUsername = intent.getStringExtra("username")!!

        // Send a call request to the remote user(name) written in the text field.
        callBtn.setOnClickListener {
            remoteUsername = remoteUsernameEdit.text.toString()
            sendCallRequest()
        }

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

        setupWebView()
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
        firebaseRef.child(remoteUsername).child("incoming").setValue(localUsername)

        // Observe if the remote user has accepted the call.
        firebaseRef.child(remoteUsername)
            .child("isAvailable").addValueEventListener(object: ValueEventListener {
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
        firebaseRef.child(remoteUsername)
            .child("connectionId").addValueEventListener(object: ValueEventListener {
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
     * Setup the web view used to display the WebRTC video and audio.
     * Automatically grants browser permissions, as user should already
     * have granted those permissions in-app previously.
     * Enables JavaScript and JS-interface to communicate with call.js.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {

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
        loadVideoCall()
    }

    /**
     * Laod the HTML file and init the peer once the page
     * has been loaded.
     */
    private fun loadVideoCall() {
        // Web page is a local assets.
        val filePath = "file:android_asset/call.html"
        webView.loadUrl(filePath)
        webView.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                initializePeer()
            }
        }
    }

    /**
     * init the peer by calling the JavaScript-function init() from call.js
     * with the unique user id.
     */
    private fun initializePeer() {
        uniqueId = getUniqueID()

        callJavascriptFunction("javascript:init(\"${uniqueId}\")")
        firebaseRef.child(localUsername)
            .child("incoming").addValueEventListener(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                // Will be called when web page is loaded.
                override fun onDataChange(snapshot: DataSnapshot) {
                    onCallRequest(snapshot.value as? String)
                }
            })
    }

    /**
     * When a call request gets sent to the local user, show controls to
     * answer/decline the call and setup their listeners.
     */
    private fun onCallRequest(caller: String?) {
        if (caller == null) return

        callLayout.visibility = View.VISIBLE
        incomingCallTxt.text = String.format("%s is calling...", caller)

        acceptBtn.setOnClickListener {
            firebaseRef.child(localUsername).child("connectionId").setValue(uniqueId)
            firebaseRef.child(localUsername).child("isAvailable").setValue(true)

            callLayout.visibility = View.GONE
            showChatControls() // change to appropriate UI elements
        }

        rejectBtn.setOnClickListener {
            firebaseRef.child(localUsername).child("incoming").setValue(null)
            callLayout.visibility = View.GONE
        }
    }

    /**
     * Show on/off control for the video and microphone,
     * and hide call control that are in the way.
     */
    private fun showChatControls() {
        callInputLayout.visibility = View.GONE
        chatControlLayout.visibility = View.VISIBLE
    }

    /**
     * Generates a unique random ID
     * TODO: Replace with some ID from the OAuth login?
     */
    private fun getUniqueID(): String {
        return UUID.randomUUID().toString()
    }

    /**
     * Helper method for calling JavaScript functions (in call.js).
     * The functions will be provided as a string, ex; "javascript:foo()"
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
    }

    /**
     * If user presses the back button, tell the activity that
     * it's done and should be closed.
     */
    override fun onBackPressed() {
        //finish()
    }

    /**
     * When Activity gets destroyed, remove the Firebase data regarding
     * the user and exit the webpage.
     */
    override fun onDestroy() {
        firebaseRef.child(localUsername).setValue(null)
        webView.loadUrl("about:blank")
        super.onDestroy()
    }

}
package se.umu.yarn.ui.conversations

import android.content.Intent
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_conversations.*
import kotlinx.android.synthetic.main.fragment_convo.joinNewConversationBtn
import se.umu.yarn.CallActivity
import se.umu.yarn.R

/**
 * Fragment for showing ongoing conversations as well as starting new ones
 */
class ConversationsFragment : Fragment() {

    private lateinit var conversationsViewModel: ConversationsViewModel
    private lateinit var account: GoogleSignInAccount
    // Sets the root reference for the db to be "topics"
    private var firebaseRef = Firebase.database.getReference("topics")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        account = requireActivity().intent.getParcelableExtra("se.umu.yarn.account")!!
        conversationsViewModel = ViewModelProvider(this).get(
            ConversationsViewModel::class.java
        )
        val root = inflater.inflate(R.layout.fragment_conversations, container, false)

        setupFirebaseListeners()

        return root
    }

    /**
     * This is called directly after onCreateView. Needed to place the
     * button listeners here to avoid NullPointerException(s).
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        joinNewConversationBtn.setOnClickListener {
            val intent = Intent(requireContext(), CallActivity::class.java)
            intent.putExtra("account", account)
            intent.putExtra("conversationTopic", "Gardening")
            intent.putExtra("isNewConversation", true)
            startActivity(intent)
        }
    }

    /**
     * Listen for changes in the Firebase database.
     */
    private fun setupFirebaseListeners() {
        firebaseRef.child("Gardening").addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    // TODO: Change this solution to a RecyclerView if expanded upon.
                    gardeningLayout.visibility = View.VISIBLE
                    gardeningNewConvoLayout.visibility = View.GONE

                    gardeningJoinBtn.setOnClickListener {
                        val intent = Intent(requireContext(), CallActivity::class.java)
                        intent.putExtra("account", account)
                        intent.putExtra("conversationTopic", "Gardening")
                        intent.putExtra("isNewConversation", false)
                        // TODO: NOTE! This works for getting a room key (assuming only one room open)
                        intent.putExtra("roomId", snapshot.children.elementAt(0).key.toString())
                        startActivity(intent)
                    }
                } else setupFirebaseListeners()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
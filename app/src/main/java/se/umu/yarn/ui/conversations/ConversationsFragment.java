package se.umu.yarn.ui.conversations;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import se.umu.yarn.CallActivity;
import se.umu.yarn.R;

public class ConversationsFragment extends Fragment {

    private ConversationsViewModel conversationsViewModel;
    private Button usernameSubmitBtn;
    private GoogleSignInAccount account;

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        account = requireActivity().getIntent().getParcelableExtra("se.umu.yarn.account");

        conversationsViewModel =
                new ViewModelProvider(this).get(ConversationsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_conversations, container, false);

        usernameSubmitBtn = root.findViewById(R.id.startChatBtn);
        usernameSubmitBtn.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), CallActivity.class);
            // TODO: change to account ID ?
            intent.putExtra("username", account.getDisplayName());
            startActivity(intent);
        });

        return root;
    }
}
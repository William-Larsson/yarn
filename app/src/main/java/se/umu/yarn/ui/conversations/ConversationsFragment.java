package se.umu.yarn.ui.conversations;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import se.umu.yarn.CallActivity;
import se.umu.yarn.R;

public class ConversationsFragment extends Fragment {

    private ConversationsViewModel conversationsViewModel;
    private EditText usernameInputText;
    private Button usernameSubmitBtn;

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        conversationsViewModel =
                new ViewModelProvider(this).get(ConversationsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_conversations, container, false);

        // TODO: Change later. Use username from OAuth login etc..
        usernameInputText = root.findViewById(R.id.usernameEdit);
        usernameSubmitBtn = root.findViewById(R.id.submitBtn);

        usernameSubmitBtn.setOnClickListener(view -> {
            String username = usernameInputText.getText().toString();
            Intent intent = new Intent(requireContext(), CallActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        return root;
    }
}
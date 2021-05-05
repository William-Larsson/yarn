package se.umu.yarn.ui.conversations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import se.umu.yarn.R;

public class ConversationsFragment extends Fragment {

    private ConversationsViewModel conversationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        conversationsViewModel =
                new ViewModelProvider(this).get(ConversationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_conversations, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        conversationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
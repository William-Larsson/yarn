package se.umu.yarn.ui.interests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

import se.umu.yarn.model.interests.InterestEntity;
import se.umu.yarn.R;

import static android.util.Log.d;

public class InterestsFragment extends Fragment {

    private InterestsViewModel interestsViewModel;
    private InterestsAdapter interestsAdapter;
    private GoogleSignInAccount account;

    /**
     * View is created, inflate the fragment, set up ViewModel, Views,
     * db-observers and retrieve the account from intent.
     * @param inflater = fragment inflater
     * @param container = not used
     * @param savedInstanceState = save state
     * @return = the fragment itself
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_interests, container, false);
        account = requireActivity().getIntent().getParcelableExtra("se.umu.yarn.account");

        setupInterestRecyclerView(root);

        TextView headline = root.findViewById(R.id.interests_headline);
        headline.setText("Hello " + account.getGivenName() + ", choose your interests!");

        Button saveBtn = root.findViewById(R.id.saveInterestBtn);
        saveBtn.setOnClickListener(view -> {
            Toast.makeText(requireContext(), "Interests has been saved!", Toast.LENGTH_LONG).show();
        });

        return root;
    }

    /**
     * Setup the RecyclerView with dynamic LiveData from Room db,
     * provided through the ViewModel.
     * @param root = the view root element.
     */
    private void setupInterestRecyclerView(View root){
        RecyclerView recyclerView = root.findViewById(R.id.interests_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        interestsAdapter = new InterestsAdapter();
        recyclerView.setAdapter(interestsAdapter);
        interestsViewModel = new ViewModelProvider(this).get(InterestsViewModel.class);
        interestsViewModel.getAllInterests().observe(
                getViewLifecycleOwner(), this::updateInterestRecyclerView);
    }

    /**
     * Update the list by calling submit list, which
     * tells the adapter that the list has changed.
     * @param interestEntities = new list
     */
    private void updateInterestRecyclerView(List<InterestEntity> interestEntities) {
        interestsAdapter.setInterests(interestEntities);
    }
}
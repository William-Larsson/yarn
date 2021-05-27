package se.umu.yarn.ui.interests;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import se.umu.yarn.R;
import se.umu.yarn.model.interests.InterestEntity;


public class InterestsAdapter extends RecyclerView.Adapter<InterestsAdapter.InterestsHolder> {

    private List<InterestEntity> interests = new ArrayList<>();

    @NotNull
    @Override
    public InterestsHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.interest_item, parent, false);

        return new InterestsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InterestsHolder holder, int position) {
        InterestEntity currentInterest = interests.get(position);
        holder.interestName.setText(currentInterest.getName());
    }

    @Override
    public int getItemCount() {
        return interests.size();
    }

    public void setInterests(List<InterestEntity> interests) {
        this.interests = interests;
        notifyDataSetChanged(); // TODO: should be replaced by more appropriate variants.
    }

    /**
     * ViewHolder for the adapter
     */
    class InterestsHolder extends RecyclerView.ViewHolder {
        private TextView interestName;

        public InterestsHolder(@NotNull View itemView) {
            super(itemView);
            interestName = itemView.findViewById(R.id.interest_item_name);
        }
    }

}

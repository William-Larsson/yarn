package se.umu.yarn.ui.interests;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import se.umu.yarn.model.interests.InterestEntity;
import se.umu.yarn.model.interests.InterestRepository;

/**
 * ViewModel for the InterestFragment.
 * Needs to be AndroidViewModel, not ViewModel as the former
 * gets the Application passed inside the constructor.
 */
public class InterestsViewModel extends AndroidViewModel {

    private InterestRepository interestRepository;
    private LiveData<List<InterestEntity>> allInterests;

    /**
     * Constructor. Sets up repository and LiveData
     * @param application = the application instance
     */
    public InterestsViewModel(@NonNull Application application) {
        super(application);
        interestRepository = new InterestRepository(application);
        allInterests = interestRepository.getAllInterests();
    }

    /**
     * Get all interests from db as LiveData
     * @return = list of interests.
     */
    public LiveData<List<InterestEntity>> getAllInterests() {
        return allInterests;
    }


    // TODO: These are not used / needed right now:

    public void insertInterest(InterestEntity interestEntity){
        interestRepository.insertInterest(interestEntity);
    }

    public void updateInterest(InterestEntity interestEntity){
        interestRepository.updateInterest(interestEntity);
    }

    public void deleteInterest(InterestEntity interestEntity){
        interestRepository.deleteInterest(interestEntity);
    }

    public void deleteAllInterests(InterestEntity interestEntity){
        interestRepository.deleteAllInterests();
    }
}
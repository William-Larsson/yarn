package se.umu.yarn.ui.interests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InterestsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InterestsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is interests fragment");
    }

    public void sendData(){


    }

    public LiveData<String> getText() {
        return mText;
    }
}
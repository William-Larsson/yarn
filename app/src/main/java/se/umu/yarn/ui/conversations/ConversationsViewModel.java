package se.umu.yarn.ui.conversations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConversationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConversationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is conversations fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
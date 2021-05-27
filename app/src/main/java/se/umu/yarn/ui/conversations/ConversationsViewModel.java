package se.umu.yarn.ui.conversations;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

public class ConversationsViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;

    public ConversationsViewModel(@NonNull @NotNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is conversations fragment");
    }



    public LiveData<String> getText() {
        return mText;
    }
}
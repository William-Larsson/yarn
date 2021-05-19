package se.umu.yarn;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import se.umu.yarn.DaoClass.DaoClass;
import se.umu.yarn.EntityClass.InterestModel;
import se.umu.yarn.EntityClass.UserModel;

class DataRepo
{

    private DaoClass interestData;
    private LiveData<List<InterestModel>> allInterests;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    DataRepo(Application application) {
        DatabaseClass db = DatabaseClass.getDatabase(application);
        interestData = db.getDao();
        allInterests = interestData.getAlphabetUsers();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<InterestModel>> getAllWords() {
        return allInterests;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(InterestModel interestModel) {
        DatabaseClass.databaseWriteExecutor.execute(() -> {
            interestData.insertAllData(interestModel);
        });
    }
}
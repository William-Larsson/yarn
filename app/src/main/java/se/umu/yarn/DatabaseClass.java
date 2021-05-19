package se.umu.yarn;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlin.random.URandomKt;
import se.umu.yarn.DaoClass.DaoClass;
import se.umu.yarn.EntityClass.InterestModel;
import se.umu.yarn.EntityClass.UserModel;

@Database(entities = {UserModel.class},version = 1, exportSchema = false)
public abstract class DatabaseClass extends RoomDatabase {



    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive

    
    public abstract DaoClass getDao();
    private static DatabaseClass instance;

    private static volatile DatabaseClass INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    static DatabaseClass getDatabase ( final Context context){
        if(instance != null)
        {
            synchronized (DatabaseClass.class){
                instance = Room.databaseBuilder(context, DatabaseClass.class, "DATABASE").build();


            }
        }
        return instance;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                DaoClass dao = INSTANCE.getDao();
                dao.deleteAll();

                UserModel user = new UserModel();
                user.setName("Knitting");
                //RAndom
                Random rand = new Random();
                int randomNum = rand.nextInt((0 - 9999) + 1) + 0;

                user.setKey(randomNum);
                dao.insert(user);
                user = new UserModel();
                dao.insert(user);
            });
        }
    };

}



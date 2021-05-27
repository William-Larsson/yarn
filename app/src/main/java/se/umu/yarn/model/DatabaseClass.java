package se.umu.yarn.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import se.umu.yarn.model.interests.InterestEntity;
import se.umu.yarn.model.interests.InterestDao;
import se.umu.yarn.model.user.UserDao;
import se.umu.yarn.model.user.UserEntity;

/**
 * Internal database class for storing local data.
 */
@Database(
        entities = {
                UserEntity.class,
                InterestEntity.class
        },
        version = 1
)
public abstract class DatabaseClass extends RoomDatabase {

    private static volatile DatabaseClass DB_INSTANCE;
    private static Application app;

    /**
     * Instantiate and get singleton database instance.
     * @param app = application for context
     * @return = the db instance
     */
    public static synchronized DatabaseClass getInstance(Application app){
        DatabaseClass.app = app;
        if (DB_INSTANCE == null){
            DB_INSTANCE = Room.databaseBuilder(
                    app.getApplicationContext(),
                    DatabaseClass.class,
                    "room_database")
                    .addCallback(populateWithInterestsCallback) // remove to not populate by default
                    .build();
        }
        return DB_INSTANCE;
    }

    /**
     * Used to get access the DAO operations
     * @return = InterestDao
     */
    public abstract InterestDao getInterestsDao();

    /**
     * Used to get access the DAO operations
     * @return = UserDao
     */
    public abstract UserDao getUserDao();

    /**
     * Callback method for seeding the Interests table with some
     * initial data.
     */
    private static final RoomDatabase.Callback populateWithInterestsCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new Thread(new PopulateWithInterestsRunnable(DB_INSTANCE)).start();
        }
    };

    /**
     * Runnable for populating the interests database table on new thread
     */
    private static class PopulateWithInterestsRunnable implements Runnable {
        private final InterestDao dao;

        private PopulateWithInterestsRunnable(DatabaseClass db) {
            dao = db.getInterestsDao();
        }

        @Override
        public void run() {
            InterestEntity knitting = new InterestEntity("Knitting", "knitting");
            InterestEntity movies = new InterestEntity("Movies", "cinema");
            InterestEntity music = new InterestEntity("Music", "guitar");
            InterestEntity gardening = new InterestEntity("Gardening", "gardening");
            InterestEntity books = new InterestEntity("Books", "book");
            InterestEntity rugby = new InterestEntity("Rugby", "rugby");
            InterestEntity gaming = new InterestEntity("Gaming", "joystick");

            dao.insertInterest(knitting);
            dao.insertInterest(movies);
            dao.insertInterest(music);
            dao.insertInterest(gardening);
            dao.insertInterest(books);
            dao.insertInterest(rugby);
            dao.insertInterest(gaming);
        }
    }
}



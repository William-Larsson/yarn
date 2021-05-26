package se.umu.yarn.model.interests;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import se.umu.yarn.model.DatabaseClass;

/**
 * Repo used for Database operations related to the Interests.
 * Will do all operations asynchronously and thread safe.
 */
public class InterestRepository {
    private final InterestDao interestsDao;

    public InterestRepository(Application app) {
        DatabaseClass database = DatabaseClass.getInstance(app);
        interestsDao = database.getInterestsDao();
    }

    /**
     *  Insert an interest
     * @param interest = object to insert to db
     */
    public void insertInterest(InterestEntity interest) {
        new Thread(new InsertRunnable(interestsDao, interest)).start();
    }

    /**
     *  Update an interest
     * @param interest = object to update in db
     */
    public void updateInterest(InterestEntity interest) {
        new Thread(new UpdateRunnable(interestsDao, interest)).start();
    }

    /**
     *  Delete an interest
     * @param interest = object to delete from db
     */
    public void deleteInterest(InterestEntity interest) {
        new Thread(new DeleteRunnable(interestsDao, interest)).start();
    }

    /**
     *  Delete all interests
     */
    public void deleteAllInterests() {
        new Thread(new DeleteAllRunnable(interestsDao)).start();
    }

    /**
     * Get all interests as LiveData
     * @return = all interests in db
     */
    public LiveData<List<InterestEntity>> getAllInterests() {
        return interestsDao.getAllInterests();
    }


    // ------------- Private classes for async operations ------------- //


    /**
     * Runnable class for inserting Interests and corresponding
     * ratings to the db tables.
     */
    private static class InsertRunnable implements Runnable {
        private final InterestDao dao;
        private final InterestEntity Interest;

        /**
         * Constructor
         * @param dao = the dao operations
         * @param Interest = the Interest and its ratings.
         */
        public InsertRunnable(InterestDao dao, InterestEntity Interest) {
            this.dao = dao;
            this.Interest = Interest;
        }

        /**
         * Runs on a new thread.
         */
        @Override
        public void run() {
            dao.insertInterest(Interest);
        }
    }

    /**
     * Runnable class for inserting Interests and corresponding
     * ratings to the db tables.
     */
    private static class UpdateRunnable implements Runnable {
        private final InterestDao dao;
        private final InterestEntity Interest;

        /**
         * Constructor
         * @param dao = the dao operations
         * @param Interest = the Interest and its ratings.
         */
        public UpdateRunnable(InterestDao dao, InterestEntity Interest) {
            this.dao = dao;
            this.Interest = Interest;
        }

        /**
         * Runs on a new thread.
         */
        @Override
        public void run() {
            dao.updateInterest(Interest);
        }
    }


    /**
     * Runnable class for deleting a Interest and corresponding
     * ratings from the db tables.
     */
    private static class DeleteRunnable implements Runnable {
        private final InterestDao dao;
        private final InterestEntity Interest;

        /**
         * Constructor
         * @param dao = the dao operations
         * @param Interest = the Interest and its ratings.
         */
        public DeleteRunnable(InterestDao dao, InterestEntity Interest) {
            this.dao = dao;
            this.Interest = Interest;
        }

        /**
         * Runs on a new thread.
         */
        @Override
        public void run() {
            dao.deleteInterest(Interest);
        }
    }


    /**
     * Runnable class for deleting all Interests and corresponding
     * ratings from the db tables.
     */
    private static class DeleteAllRunnable implements Runnable {
        private final InterestDao dao;

        /**
         * Constructor
         * @param dao = the dao operations
         */
        public DeleteAllRunnable(InterestDao dao) {
            this.dao = dao;
        }

        /**
         * Runs on a new thread.
         */
        @Override
        public void run() {
            dao.deleteAllInterests();
        }
    }
}

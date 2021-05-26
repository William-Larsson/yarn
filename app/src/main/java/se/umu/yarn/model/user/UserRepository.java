package se.umu.yarn.model.user;

import android.app.Application;
import se.umu.yarn.model.DatabaseClass;

public class UserRepository {
    private final UserDao dao;

    public UserRepository(Application app) {
        DatabaseClass database = DatabaseClass.getInstance(app);
        dao = database.getUserDao();
    }

    // TODO: Add CRUD operations and LiveData methods
}

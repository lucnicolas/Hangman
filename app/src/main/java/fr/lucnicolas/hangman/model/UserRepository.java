package fr.lucnicolas.hangman.model;

import android.app.Application;

import fr.lucnicolas.hangman.model.dao.UserDao;
import fr.lucnicolas.hangman.model.entity.User;

/**
 * The type User repository.
 */
public class UserRepository {

    private UserDao mUserDao;

    /**
     * Instantiates a new User repository.
     *
     * @param application the application
     */
    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);

        mUserDao = db.userDao();
    }

    /**
     * Gets all users.
     * Room executes all queries on a separate thread.
     * Observed LiveData will notify the observer when the data has changed.
     *
     * @return the all users
     */
    public User getUser(String pseudo) {
        return mUserDao.getUser(pseudo);
    }

    /**
     * Register a new user.
     * You must call this on a non-UI thread or your app will throw an exception. Room ensures that you're not doing any long running operations on the main thread, blocking the UI.
     *
     * @param user the user
     */
    public void register(final User user) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mUserDao.register(user);
            }
        });
    }
}
package fr.lucnicolas.hangman.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.lucnicolas.hangman.model.dao.UserDao;
import fr.lucnicolas.hangman.model.entity.User;

/**
 * The type User repository.
 */
public class UserRepository {

    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;

    /**
     * Instantiates a new User repository.
     *
     * @param application the application
     */
    UserRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);

        mUserDao = db.userDao();
        mAllUsers = mUserDao.getAllUsersByMaxScore();
    }

    /**
     * Gets all users.
     * Room executes all queries on a separate thread.
     * Observed LiveData will notify the observer when the data has changed.
     *
     * @return the all users
     */
    LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    /**
     * Register a new user.
     * You must call this on a non-UI thread or your app will throw an exception. Room ensures that you're not doing any long running operations on the main thread, blocking the UI.
     *
     * @param user the user
     */
    void register(final User user) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mUserDao.register(user);
            }
        });
    }
}
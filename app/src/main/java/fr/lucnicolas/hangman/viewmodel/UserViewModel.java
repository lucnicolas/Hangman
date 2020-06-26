package fr.lucnicolas.hangman.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.lucnicolas.hangman.model.UserRepository;
import fr.lucnicolas.hangman.model.entity.User;

/**
 * The type User view model.
 */
public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    private LiveData<List<User>> mAllUsers;

    /**
     * Instantiates a new User view model.
     *
     * @param application the application
     */
    public UserViewModel(@NonNull Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllUsers = mRepository.getAllUsers();
    }

    /**
     * Gets all users.
     *
     * @return the all users
     */
    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    /**
     * Register a new user.
     *
     * @param user the user
     */
    public void register(User user) {
        mRepository.register(user);
    }
}
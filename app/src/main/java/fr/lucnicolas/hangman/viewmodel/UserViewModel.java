package fr.lucnicolas.hangman.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import fr.lucnicolas.hangman.model.UserRepository;
import fr.lucnicolas.hangman.model.entity.User;

/**
 * The type User view model.
 */
public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    /**
     * Instantiates a new User view model.
     *
     * @param application the application
     */
    public UserViewModel(@NonNull Application application) {
        super(application);
        mRepository = new UserRepository(application);
    }

    /**
     * Gets all users.
     *
     * @return the all users
     */
    public LiveData<User> getUser(String pseudo) {
        return mRepository.getUser(pseudo);
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
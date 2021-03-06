package fr.lucnicolas.hangman.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import fr.lucnicolas.hangman.model.entity.User;

@Dao
public interface UserDao {

    // allowing the register of the same user multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void register(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM user_table WHERE pseudo = :pseudo")
    LiveData<User> getUser(String pseudo);

}

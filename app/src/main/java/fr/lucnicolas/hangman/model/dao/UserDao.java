package fr.lucnicolas.hangman.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import fr.lucnicolas.hangman.model.entity.User;

@Dao
public interface UserDao {

    // allowing the register of the same user multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void register(User user);

    @Query("SELECT * FROM user_table WHERE pseudo = :pseudo")
    User getUser(String pseudo);

}

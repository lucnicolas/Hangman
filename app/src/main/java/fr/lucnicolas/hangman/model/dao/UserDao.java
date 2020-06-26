package fr.lucnicolas.hangman.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.lucnicolas.hangman.model.entity.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void register(User user);

    @Query("SELECT * FROM user_table ORDER BY maximum_score DESC")
    LiveData<List<User>> getAllUsersByMaxScore();

}

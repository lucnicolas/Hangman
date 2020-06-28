package fr.lucnicolas.hangman.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.lucnicolas.hangman.model.entity.Word;

/**
 * The interface Word dao.
 */
@Dao
public interface WordDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();

    @Query("SELECT * FROM word_table WHERE LENGTH(word) BETWEEN 2 and 6 ORDER BY random() LIMIT 1")
    LiveData<Word> getBeginnerWord();

    @Query("SELECT * FROM word_table WHERE LENGTH(word) BETWEEN 4 and 8 ORDER BY random() LIMIT 1")
    LiveData<Word> getAverageWord();

    @Query("SELECT * FROM word_table WHERE LENGTH(word) BETWEEN 6 and 12 ORDER BY random() LIMIT 1")
    LiveData<Word> getConfirmedWord();

    @Query("SELECT * FROM word_table WHERE LENGTH(word) BETWEEN 8 and 30 ORDER BY random() LIMIT 1")
    LiveData<Word> getExpertWord();
}

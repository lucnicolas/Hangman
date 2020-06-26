package fr.lucnicolas.hangman.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.lucnicolas.hangman.model.dao.WordDao;
import fr.lucnicolas.hangman.model.entity.Word;

/**
 * The type Word repository.
 */
public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    /**
     * Instantiates a new Word repository.
     *
     * @param application the application
     */
    WordRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    /**
     * Gets all words.
     * Room executes all queries on a separate thread.
     * Observed LiveData will notify the observer when the data has changed.
     *
     * @return the all words
     */
    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    /**
     * Insert a new word.
     * You must call this on a non-UI thread or your app will throw an exception. Room ensures that you're not doing any long running operations on the main thread, blocking the UI.
     *
     * @param word the word
     */
    void insert(final Word word) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mWordDao.insert(word);
            }
        });
    }
}
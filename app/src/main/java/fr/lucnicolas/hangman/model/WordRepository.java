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
    private LiveData<Word> mBeginnerWord;
    private LiveData<Word> mAverageWord;
    private LiveData<Word> mConfirmedWord;
    private LiveData<Word> mExpertWord;


    /**
     * Instantiates a new Word repository.
     *
     * @param application the application
     */
    public WordRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
        mBeginnerWord = mWordDao.getBeginnerWord();
        mAverageWord = mWordDao.getAverageWord();
        mConfirmedWord = mWordDao.getConfirmedWord();
        mExpertWord = mWordDao.getExpertWord();
    }

    /**
     * Gets all words.
     * Room executes all queries on a separate thread.
     * Observed LiveData will notify the observer when the data has changed.
     *
     * @return the all words
     */
    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    /**
     * Gets beginner word.
     *
     * @return the beginner word
     */
    public LiveData<Word> getBeginnerWord() {
        return mBeginnerWord;
    }

    /**
     * Gets average word.
     *
     * @return the average word
     */
    public LiveData<Word> getAverageWord() {
        return mAverageWord;
    }

    /**
     * Gets confirmed word.
     *
     * @return the confirmed word
     */
    public LiveData<Word> getConfirmedWord() {
        return mConfirmedWord;
    }

    /**
     * Gets expert word.
     *
     * @return the expert word
     */
    public LiveData<Word> getExpertWord() {
        return mExpertWord;
    }


    /**
     * Insert a new word.
     * You must call this on a non-UI thread or your app will throw an exception. Room ensures that you're not doing any long running operations on the main thread, blocking the UI.
     *
     * @param word the word
     */
    public void insert(final Word word) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mWordDao.insert(word);
            }
        });
    }
}
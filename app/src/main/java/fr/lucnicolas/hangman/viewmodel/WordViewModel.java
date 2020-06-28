package fr.lucnicolas.hangman.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.lucnicolas.hangman.model.WordRepository;
import fr.lucnicolas.hangman.model.entity.Word;

/**
 * The type Word view model.
 */
public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;
    private LiveData<List<Word>> mAllWords;
    private LiveData<Word> mBeginnerWord;
    private LiveData<Word> mAverageWord;
    private LiveData<Word> mConfirmedWord;
    private LiveData<Word> mExpertWord;

    /**
     * Instantiates a new Word view model.
     *
     * @param application the application
     */
    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
        mBeginnerWord = mRepository.getBeginnerWord();
        mAverageWord = mRepository.getAverageWord();
        mConfirmedWord = mRepository.getConfirmedWord();
        mExpertWord = mRepository.getExpertWord();
    }

    /**
     * Gets all words.
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
     *
     * @param word the word
     */
    public void insert(Word word) {
        mRepository.insert(word);
    }
}
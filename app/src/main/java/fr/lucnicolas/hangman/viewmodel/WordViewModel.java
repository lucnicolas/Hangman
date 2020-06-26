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

    /**
     * Instantiates a new Word view model.
     *
     * @param application the application
     */
    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    /**
     * Gets all words.
     *
     * @return the all words
     */
    LiveData<List<Word>> getAllWords() {
        return mAllWords;
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
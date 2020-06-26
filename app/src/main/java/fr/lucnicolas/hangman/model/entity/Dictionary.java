package fr.lucnicolas.hangman.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dictionary_table")
public class Dictionary {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Dictionary(@NonNull String word) {
        this.mWord = word;
    }

    public String getWord() {
        return this.mWord;
    }
}

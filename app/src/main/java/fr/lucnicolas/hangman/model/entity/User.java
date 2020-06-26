package fr.lucnicolas.hangman.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pseudo")
    private String mPseudo;

    @ColumnInfo(name = "maximum_score")
    private int mMaximumScore;

    public User(@NonNull String pseudo) {
        this.mPseudo = pseudo;
    }

    public String getPseudo() {
        return this.mPseudo;
    }

    public int getMaximumScore() {
        return this.mMaximumScore;
    }

    public void setMaximumScore(int score) {
        this.mMaximumScore = score;
    }
}

package fr.lucnicolas.hangman.model.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pseudo")
    private String mPseudo;

    @Nullable
    @ColumnInfo(name = "maximum_score")
    private Integer mMaximumScore;

    public User(@NonNull String pseudo) {
        this.mPseudo = pseudo;
        this.mMaximumScore = null;
    }

    public String getPseudo() {
        return this.mPseudo;
    }

    public Integer getMaximumScore() {
        return this.mMaximumScore;
    }
}

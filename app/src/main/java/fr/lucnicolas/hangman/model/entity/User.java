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

    @ColumnInfo(name = "maximum_score_beginner", defaultValue = "0")
    private int mBeginnerMaximumScore;

    @ColumnInfo(name = "maximum_score_average", defaultValue = "0")
    private int mAverageMaximumScore;

    @ColumnInfo(name = "maximum_score_confirmed", defaultValue = "0")
    private int mConfirmedMaximumScore;

    @ColumnInfo(name = "maximum_score_expert", defaultValue = "0")
    private int mExpertMaximumScore;

    public User(@NonNull String pseudo) {
        this.mPseudo = pseudo;
    }

    public String getPseudo() {
        return this.mPseudo;
    }

    public int getBeginnerMaximumScore() {
        return mBeginnerMaximumScore;
    }

    public void setBeginnerMaximumScore(int beginnerMaximumScore) {
        this.mBeginnerMaximumScore = beginnerMaximumScore;
    }

    public int getAverageMaximumScore() {
        return mAverageMaximumScore;
    }

    public void setAverageMaximumScore(int averageMaximumScore) {
        this.mAverageMaximumScore = averageMaximumScore;
    }

    public int getConfirmedMaximumScore() {
        return mConfirmedMaximumScore;
    }

    public void setConfirmedMaximumScore(int confirmedMaximumScore) {
        this.mConfirmedMaximumScore = confirmedMaximumScore;
    }

    public int getExpertMaximumScore() {
        return mExpertMaximumScore;
    }

    public void setExpertMaximumScore(int expertMaximumScore) {
        this.mExpertMaximumScore = expertMaximumScore;
    }
}

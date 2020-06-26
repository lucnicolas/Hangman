package fr.lucnicolas.hangman.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.lucnicolas.hangman.model.dao.UserDao;
import fr.lucnicolas.hangman.model.dao.WordDao;
import fr.lucnicolas.hangman.model.entity.User;
import fr.lucnicolas.hangman.model.entity.Word;

/**
 * The type App database.
 */
@Database(entities = {User.class, Word.class}, version = 1, exportSchema = false)
// 'exportSchema' is to false here to avoid a build warning.
public abstract class AppDatabase extends RoomDatabase {


    // The database exposes DAOs through an abstract "getter" method for each @Dao.
    private static final int NUMBER_OF_THREADS = 4;
    /**
     * The Database write executor.
     */
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    // We've defined a singleton, AppDatabase, to prevent having multiple instances of the database opened at the same time.
    private static volatile AppDatabase INSTANCE;

    /**
     * It'll create the database the first time it's accessed, using Room's database builder to create a RoomDatabase object in the application context from the AppDatabase class and names it "hangman_database".
     *
     * @param context the context
     * @return the instance singleton
     */
    static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "hangman_database").build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * User dao user dao.
     *
     * @return the user dao
     */
    public abstract UserDao userDao();

    /**
     * Word dao word dao.
     *
     * @return the word dao
     */
    public abstract WordDao wordDao();
}

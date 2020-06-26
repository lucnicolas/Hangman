package fr.lucnicolas.hangman.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

    //
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        /**
         * Called when the database is created for the first time. This is called after all the
         * tables are created.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    // Populate the database in the background.
                    WordDao dao = INSTANCE.wordDao();
                    dao.deleteAll();

                    Word word = new Word("Ampoule");
                    dao.insert(word);
                    word = new Word("Maracas");
                    dao.insert(word);
                    word = new Word("Rotule");
                    dao.insert(word);
                    word = new Word("Diamant");
                    dao.insert(word);
                    word = new Word("Hypophyse");
                    dao.insert(word);
                    word = new Word("Cheval");
                    dao.insert(word);
                    word = new Word("Indubitablement");
                    dao.insert(word);
                    word = new Word("Procrastination");
                    dao.insert(word);
                    word = new Word("Phéromone");
                    dao.insert(word);
                }
            });
        }
    };

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
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "hangman_database")
                            .addCallback(sRoomDatabaseCallback) // Comment this line to not populate the database
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

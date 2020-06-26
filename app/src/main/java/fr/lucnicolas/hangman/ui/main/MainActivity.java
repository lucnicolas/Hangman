package fr.lucnicolas.hangman.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fr.lucnicolas.hangman.R;
import fr.lucnicolas.hangman.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}
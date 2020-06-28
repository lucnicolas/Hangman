package fr.lucnicolas.hangman.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.text.Normalizer;
import java.util.Objects;
import java.util.regex.Pattern;

import fr.lucnicolas.hangman.R;
import fr.lucnicolas.hangman.model.entity.User;
import fr.lucnicolas.hangman.model.entity.Word;
import fr.lucnicolas.hangman.viewmodel.UserViewModel;
import fr.lucnicolas.hangman.viewmodel.WordViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";
    private static final String ARG_LEVEL = "level";

    private String mUser;
    private int mLevel;
    private String mLevelStr;

    private UserViewModel mUserViewModel;
    /**
     * The Error number.
     */
    int errorNumber;
    private WordViewModel mWordViewModel;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userPseudo the user pseudo
     * @param gameLevel  the game level
     * @return A new instance of fragment DictonaryFragment.
     */
    public static GameFragment newInstance(String userPseudo, int gameLevel) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER, userPseudo);
        args.putInt(ARG_LEVEL, gameLevel);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Remove accents string.
     *
     * @param text the text
     * @return the string
     */
    public static String removeAccents(String text) {
        return text == null ? null :
                Normalizer.normalize(text, Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link #getActivity()} (Activity)} and before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     *
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(Bundle)}.
     *
     * <p>Any restored child fragments will be created before the base
     * <code>Fragment.onCreate</code> method returns.</p>
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getString(ARG_USER);
            mLevel = getArguments().getInt(ARG_LEVEL);
        }

        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null. This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     *
     * <p>It is recommended to <strong>only</strong> inflate the layout in this method and move
     * logic that operates on the returned View to {@link #onViewCreated(View, Bundle)}.
     *
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.game_fragment, container, false);

        switch (mLevel) {
            case 0:
                mLevelStr = getString(R.string.beginner);
                mWordViewModel.getBeginnerWord().observe(Objects.requireNonNull(getActivity()), new Observer<Word>() {
                    @Override
                    public void onChanged(Word word) {
                        hangman(word, view);
                    }
                });
                mUserViewModel.getUser(mUser).observe(Objects.requireNonNull(getActivity()), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        String strLevelFormat = view.getResources().getString(R.string.vous_jouez_en_niveau_X_votre_record_ce_niveau_est_de_Y, mLevelStr, user.getBeginnerMaximumScore());
                        TextView textViewLevel = view.findViewById(R.id.text_level);
                        textViewLevel.setText(strLevelFormat);
                    }
                });
                break;
            case 1:
                mLevelStr = getString(R.string.average);
                mWordViewModel.getAverageWord().observe(getActivity(), new Observer<Word>() {
                    @Override
                    public void onChanged(Word word) {
                        hangman(word, view);
                    }
                });
                mUserViewModel.getUser(mUser).observe(Objects.requireNonNull(getActivity()), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        String strLevelFormat = view.getResources().getString(R.string.vous_jouez_en_niveau_X_votre_record_ce_niveau_est_de_Y, mLevelStr, user.getAverageMaximumScore());
                        TextView textViewLevel = view.findViewById(R.id.text_level);
                        textViewLevel.setText(strLevelFormat);
                    }
                });
                break;
            case 2:
                mLevelStr = getString(R.string.confirmed);
                mWordViewModel.getConfirmedWord().observe(getActivity(), new Observer<Word>() {
                    @Override
                    public void onChanged(Word word) {
                        hangman(word, view);
                    }
                });
                mUserViewModel.getUser(mUser).observe(Objects.requireNonNull(getActivity()), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        String strLevelFormat = view.getResources().getString(R.string.vous_jouez_en_niveau_X_votre_record_ce_niveau_est_de_Y, mLevelStr, user.getConfirmedMaximumScore());
                        TextView textViewLevel = view.findViewById(R.id.text_level);
                        textViewLevel.setText(strLevelFormat);
                    }
                });
                break;
            case 3:
                mLevelStr = getString(R.string.expert);
                mWordViewModel.getExpertWord().observe(getActivity(), new Observer<Word>() {
                    @Override
                    public void onChanged(Word word) {
                        hangman(word, view);
                    }
                });
                mUserViewModel.getUser(mUser).observe(Objects.requireNonNull(getActivity()), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        String strLevelFormat = view.getResources().getString(R.string.vous_jouez_en_niveau_X_votre_record_ce_niveau_est_de_Y, mLevelStr, user.getExpertMaximumScore());
                        TextView textViewLevel = view.findViewById(R.id.text_level);
                        textViewLevel.setText(strLevelFormat);
                    }
                });
                break;
        }
        return view;
    }

    private void hangman(final Word word, final View view) {
        final String currentWord = word.getWord().toUpperCase();

        Log.e("WORD", currentWord);

        final String[] strWord = new String[currentWord.length()];

        for (int i = 0; i < currentWord.length(); i++) {
            strWord[i] = String.valueOf(currentWord.charAt(i));
        }

        // Set the word with visibility invisible.
        final TextView[] textViewWord = new TextView[30];
        final RelativeLayout relativeLayoutWord = view.findViewById(R.id.layout_word);
        for (int i = 0; i < strWord.length; i++) {
            textViewWord[i] = new TextView(getContext());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = i * 50;
            textViewWord[i].setText(strWord[i]);
            textViewWord[i].setTextSize((float) 25);
            textViewWord[i].setVisibility(View.INVISIBLE);
            textViewWord[i].setLayoutParams(params);
            relativeLayoutWord.addView(textViewWord[i]);
        }

        // Display underscores blow the word.
        final TextView[] textViewUnderscore = new TextView[30];
        for (int i = 0; i < strWord.length; i++) {
            textViewUnderscore[i] = new TextView(getContext());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = 8;
            params.leftMargin = i * 50;
            textViewUnderscore[i].setText("_ ");
            textViewUnderscore[i].setTextSize((float) 25);
            textViewUnderscore[i].setLayoutParams(params);
            relativeLayoutWord.addView(textViewUnderscore[i]);
        }
        Button buttonSubmit = view.findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextLetter = view.findViewById(R.id.edit_letter);
                String letter = editTextLetter.getText().toString();
                editTextLetter.setText("");
                TextView textViewError = null;
                TextView[] pastTextViewsError = new TextView[7];

                if (letter.length() == 1 && Pattern.matches("[a-zA-Z]+", letter)) {
                    int index = 0;
                    int textViewVisible = 0;
                    for (int i = 0; i < strWord.length; i++) {
                        if (removeAccents(strWord[i]).equals(removeAccents(letter))) {
                            textViewWord[i].setVisibility(View.VISIBLE);
                        } else {
                            index++;
                        }
                        if (textViewWord[i].getVisibility() == View.VISIBLE) {
                            textViewVisible++;
                        }
                    }

                    if (index == strWord.length) {
                        errorNumber++;
                        switch (errorNumber) {
                            case 1:
                                textViewError = view.findViewById(R.id.button_1);
                                break;
                            case 2:
                                textViewError = view.findViewById(R.id.button_2);
                                pastTextViewsError[0] = view.findViewById(R.id.button_1);

                                break;
                            case 3:
                                textViewError = view.findViewById(R.id.button_3);
                                pastTextViewsError[0] = view.findViewById(R.id.button_1);
                                pastTextViewsError[1] = view.findViewById(R.id.button_2);
                                break;
                            case 4:
                                textViewError = view.findViewById(R.id.button_4);
                                pastTextViewsError[0] = view.findViewById(R.id.button_1);
                                pastTextViewsError[1] = view.findViewById(R.id.button_2);
                                pastTextViewsError[2] = view.findViewById(R.id.button_3);
                                break;
                            case 5:
                                textViewError = view.findViewById(R.id.button_5);
                                pastTextViewsError[0] = view.findViewById(R.id.button_1);
                                pastTextViewsError[1] = view.findViewById(R.id.button_2);
                                pastTextViewsError[2] = view.findViewById(R.id.button_3);
                                pastTextViewsError[3] = view.findViewById(R.id.button_4);
                                break;
                            case 6:
                                textViewError = view.findViewById(R.id.button_6);
                                pastTextViewsError[0] = view.findViewById(R.id.button_1);
                                pastTextViewsError[1] = view.findViewById(R.id.button_2);
                                pastTextViewsError[2] = view.findViewById(R.id.button_3);
                                pastTextViewsError[3] = view.findViewById(R.id.button_4);
                                pastTextViewsError[4] = view.findViewById(R.id.button_5);
                                break;
                            case 7:
                                Toast.makeText(getContext(), "Vous avez creusé votre tombe et posé la croix dessus. Vous avez perdu. Le mot à deviner était " + currentWord, Toast.LENGTH_LONG).show();
                                FragmentManager fragmentManager = getParentFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.container, GameFragment.newInstance(mUser, mLevel));
                                fragmentTransaction.commit();

                            default:
                                Log.e("GAME", "Impossible de selectionner une case");
                                break;
                        }

                        int alreadyProposed = 0;

                        if (textViewError != null) {
                            for (TextView tv : pastTextViewsError) {
                                if (tv != null && tv.getText().equals(textViewError.getText())) {
                                    alreadyProposed++;
                                }
                            }
                            if (alreadyProposed == 0) {
                                textViewError.setText(letter);
                            } else {
                                Toast.makeText(getContext(), "Vous avez déjà proposé cette lettre", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (textViewVisible == strWord.length) {
                        final int score = 6 - errorNumber;
                        final FragmentManager fragmentManager = getParentFragmentManager();
                        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        mUserViewModel.getUser(mUser).observe(Objects.requireNonNull(getActivity()), new Observer<User>() {
                            @Override
                            public void onChanged(User user) {
                                switch (mLevel) {
                                    case 0:
                                        if (user.getBeginnerMaximumScore() < score) {
                                            user.setBeginnerMaximumScore(score);
                                            Toast.makeText(getContext(), "Vous avez gagné et battu votre record qui est maintenant de " + score, Toast.LENGTH_LONG).show();
                                            fragmentTransaction.replace(R.id.container, MenuFragment.newInstance(user.getPseudo(), user.getBeginnerMaximumScore(), user.getAverageMaximumScore(), user.getConfirmedMaximumScore(), user.getExpertMaximumScore()));
                                        } else {
                                            fragmentTransaction.replace(R.id.container, MenuFragment.newInstance(mUser));
                                            Toast.makeText(getContext(), "Vous avez gagné avec un score de " + score, Toast.LENGTH_LONG).show();
                                        }
                                        break;
                                    case 1:
                                        if (user.getAverageMaximumScore() < score) {
                                            user.setAverageMaximumScore(score);
                                            Toast.makeText(getContext(), "Vous avez gagné et battu votre record qui est maintenant de " + score, Toast.LENGTH_LONG).show();
                                            fragmentTransaction.replace(R.id.container, MenuFragment.newInstance(user.getPseudo(), user.getBeginnerMaximumScore(), user.getAverageMaximumScore(), user.getConfirmedMaximumScore(), user.getExpertMaximumScore()));
                                        } else {
                                            Toast.makeText(getContext(), "Vous avez gagné avec un score de " + score, Toast.LENGTH_LONG).show();
                                            fragmentTransaction.replace(R.id.container, MenuFragment.newInstance(mUser));
                                        }
                                        break;
                                    case 2:
                                        if (user.getConfirmedMaximumScore() < score) {
                                            user.setConfirmedMaximumScore(score);
                                            Toast.makeText(getContext(), "Vous avez gagné et battu votre record qui est maintenant de " + score, Toast.LENGTH_LONG).show();
                                            fragmentTransaction.replace(R.id.container, MenuFragment.newInstance(user.getPseudo(), user.getBeginnerMaximumScore(), user.getAverageMaximumScore(), user.getConfirmedMaximumScore(), user.getExpertMaximumScore()));
                                        } else {
                                            Toast.makeText(getContext(), "Vous avez gagné avec un score de " + score, Toast.LENGTH_LONG).show();
                                            fragmentTransaction.replace(R.id.container, MenuFragment.newInstance(mUser));
                                        }
                                        break;
                                    case 3:
                                        if (user.getExpertMaximumScore() < score) {
                                            user.setExpertMaximumScore(score);
                                            Toast.makeText(getContext(), "Vous avez gagné et battu votre record qui est maintenant de " + score, Toast.LENGTH_LONG).show();
                                            fragmentTransaction.replace(R.id.container, MenuFragment.newInstance(user.getPseudo(), user.getBeginnerMaximumScore(), user.getAverageMaximumScore(), user.getConfirmedMaximumScore(), user.getExpertMaximumScore()));
                                        } else {
                                            Toast.makeText(getContext(), "Vous avez gagné avec un score de " + score, Toast.LENGTH_LONG).show();
                                            fragmentTransaction.replace(R.id.container, MenuFragment.newInstance(mUser));
                                        }
                                        break;
                                }
                                fragmentTransaction.disallowAddToBackStack();
                                try {
                                    mUserViewModel.getUser(mUser).removeObservers(getActivity());
                                    fragmentTransaction.commit();
                                } catch (IllegalStateException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                } else
                    Toast.makeText(getContext(), "Veuillez proposer une lettre", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
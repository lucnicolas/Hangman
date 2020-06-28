package fr.lucnicolas.hangman.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import fr.lucnicolas.hangman.R;
import fr.lucnicolas.hangman.model.entity.User;
import fr.lucnicolas.hangman.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DictionaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER_PSEUDO = "user_pseudo";
    private static final String ARG_USER_SCORE_BEGINNER = "user_score_beginner";
    private static final String ARG_USER_SCORE_AVERAGE = "user_score_average";
    private static final String ARG_USER_SCORE_CONFIRMED = "user_score_confirmed";
    private static final String ARG_USER_SCORE_EXPERT = "user_score_expert";

    private String mUserPseudo;
    private int mUserBeginnerScore;
    private int mUserAverageScore;
    private int mUserConfirmedScore;
    private int mUserExpertScore;

    private UserViewModel mUserViewModel;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DictonaryFragment.
     */
    public static MenuFragment newInstance(String userPseudo) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_PSEUDO, userPseudo);
        fragment.setArguments(args);
        return fragment;
    }

    public static MenuFragment newInstance(String pseudo, int beginnerMaximumScore, int averageMaximumScore, int confirmedMaximumScore, int expertMaximumScore) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_PSEUDO, pseudo);
        args.putInt(ARG_USER_SCORE_BEGINNER, beginnerMaximumScore);
        args.putInt(ARG_USER_SCORE_AVERAGE, averageMaximumScore);
        args.putInt(ARG_USER_SCORE_CONFIRMED, confirmedMaximumScore);
        args.putInt(ARG_USER_SCORE_EXPERT, expertMaximumScore);
        fragment.setArguments(args);
        return fragment;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserPseudo = getArguments().getString(ARG_USER_PSEUDO);

            mUserBeginnerScore = getArguments().getInt(ARG_USER_SCORE_BEGINNER);
            mUserAverageScore = getArguments().getInt(ARG_USER_SCORE_AVERAGE);
            mUserConfirmedScore = getArguments().getInt(ARG_USER_SCORE_CONFIRMED);
            mUserExpertScore = getArguments().getInt(ARG_USER_SCORE_EXPERT);

            mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            User user = new User(mUserPseudo);
            user.setBeginnerMaximumScore(mUserBeginnerScore);
            user.setAverageMaximumScore(mUserAverageScore);
            user.setConfirmedMaximumScore(mUserConfirmedScore);
            user.setExpertMaximumScore(mUserExpertScore);
            if (mUserBeginnerScore != 0 || mUserAverageScore != 0 || mUserConfirmedScore != 0 || mUserExpertScore != 0)
                mUserViewModel.update(user);
        }
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.menu_fragment, container, false);

        Button buttonDictionary = view.findViewById(R.id.button_dictionary);
        buttonDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, DictionaryFragment.newInstance());
                // Ensure to return to MenuFragment on clicking back
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button buttonBeginner = view.findViewById(R.id.button_beginner);
        buttonBeginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction_to_game_fragment(0);
            }
        });

        Button buttonAverage = view.findViewById(R.id.button_average);
        buttonAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction_to_game_fragment(1);
            }
        });

        Button buttonConfirmed = view.findViewById(R.id.button_confirmed);
        buttonConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction_to_game_fragment(2);
            }
        });

        Button buttonExpert = view.findViewById(R.id.button_expert);
        buttonExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction_to_game_fragment(3);
            }
        });

        return view;
    }

    private void transaction_to_game_fragment(int level) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, GameFragment.newInstance(mUserPseudo, level));
        // Ensure to return to MenuFragment on clicking back
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

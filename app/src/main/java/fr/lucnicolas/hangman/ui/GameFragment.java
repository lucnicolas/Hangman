package fr.lucnicolas.hangman.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import fr.lucnicolas.hangman.R;
import fr.lucnicolas.hangman.model.entity.User;
import fr.lucnicolas.hangman.viewmodel.UserViewModel;

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
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
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
     * Called to do initial creation of a fragment.  This is called after
     * {@link #onAttach(Activity)} and before
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
            switch (mLevel) {
                case 0:
                    mLevelStr = getString(R.string.beginner);
                    break;
                case 1:
                    mLevelStr = getString(R.string.average);
                    break;
                case 2:
                    mLevelStr = getString(R.string.confirmed);
                    break;
                case 3:
                    mLevelStr = getString(R.string.expert);
                    break;
            }
        }

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

        mUserViewModel.getUser(mUser).observe(Objects.requireNonNull(getActivity()), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                String strLevelFormat = getResources().getString(R.string.vous_jouez_en_niveau_X_votre_record_ce_niveau_est_de_Y, mLevelStr, user.getMaximumScore());
                TextView textViewLevel = view.findViewById(R.id.text_level);
                textViewLevel.setText(strLevelFormat);
            }
        });

        return view;
    }
}
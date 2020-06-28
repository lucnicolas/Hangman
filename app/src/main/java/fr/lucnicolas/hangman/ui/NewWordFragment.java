package fr.lucnicolas.hangman.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import fr.lucnicolas.hangman.R;
import fr.lucnicolas.hangman.model.entity.Word;
import fr.lucnicolas.hangman.viewmodel.WordViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewWordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewWordFragment extends Fragment {

    private EditText mEditWordView;

    private WordViewModel mWordViewModel;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DictonaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewWordFragment newInstance() {
        return new NewWordFragment();
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
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

    }

    private static String capitalizeString(String str) {
        String retStr = str;
        try { // We can face index out of bound exception if the string is null
            retStr = str.substring(0, 1).toUpperCase() + str.substring(1);
        } catch (Exception ignored) {
        }
        return retStr;
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
        View view = inflater.inflate(R.layout.new_word_fragment, container, false);

        mEditWordView = view.findViewById(R.id.edit_word);

        final Button button = view.findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    Snackbar.make(view, R.string.champ_vide, BaseTransientBottomBar.LENGTH_LONG);
                } else {
                    String strWord = mEditWordView.getText().toString();
                    strWord = strWord.toLowerCase();
                    strWord = capitalizeString(strWord);
                    Word word = new Word(strWord);
                    mWordViewModel.insert(word);

                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, DictionaryFragment.newInstance());
                    // Ensure to return to LoginFragment on clicking back
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
        return view;
    }
}

package fr.quentinneyraud.www.hoolinotes.Notes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.quentinneyraud.www.hoolinotes.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesDetailFragment extends Fragment {

    private static final String NOTE = "NOTE";

    private Note note;

    public NotesDetailFragment() {
        // Required empty public constructor
    }

    public static NotesDetailFragment newInstance(String noteId){

        NotesDetailFragment fragment = new NotesDetailFragment();
//        Bundle args = new Bundle();
//        args.put(NOTE, note);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(getArguments() != null){
//            user = getArguments().get(NOTE);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_detail, container, false);
    }

}

package fr.quentinneyraud.www.hoolinotes.Notes;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.quentinneyraud.www.hoolinotes.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesMapFragment extends Fragment {

    private static final String TAG = "NOTES MAP FRAGMENT ===";

    public NotesMapFragment() {
        // Required empty public constructor
    }

    public void setLocation(Location location){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_map, container, false);
    }

}

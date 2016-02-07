package fr.quentinneyraud.www.hoolinotes;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesListFragment extends Fragment {

    private static final String TAG = "NOTES LIST FRAGMENT ===";

    public NotesListFragment() {
        // Required empty public constructor
        Log.d(TAG, "constructor");
    }

    public void setLocation(Location location){
        Log.d(TAG, "position");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "create view");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

}

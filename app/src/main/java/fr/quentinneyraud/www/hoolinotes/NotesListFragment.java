package fr.quentinneyraud.www.hoolinotes;


import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.quentinneyraud.www.hoolinotes.Notes.Note;
import fr.quentinneyraud.www.hoolinotes.Notes.NoteAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesListFragment extends Fragment {

    private static final String TAG = "NOTES LIST FRAGMENT ===";

    private NoteAdapter noteAdapter;

    public NotesListFragment() {}

    public void setLocation(Location location){
        Log.d(TAG, "position");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        // Get recycler view
        RecyclerView rcView = (RecyclerView) view.findViewById(R.id.fragment_notes_recycler_view);
        rcView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        // Create adapter
        noteAdapter = new NoteAdapter(new ArrayList<Note>());

        // Set listener
        noteAdapter.setNoteClickListener(new NoteAdapter.NoteClickListener() {
            @Override
            public void onClick(View v, long id) {
                Log.d(TAG, "Click on " + String.valueOf(id));
            }
        });

        rcView.setAdapter(noteAdapter);

        this.loadDatas();

        return view;
    }

    private void loadDatas(){
        // TODO : get notes, pass them to adapter & notify change
        // noteAdapter.addNotes(listNotes);
        // noteAdapter.notifyDataSetChanged();
    }

}

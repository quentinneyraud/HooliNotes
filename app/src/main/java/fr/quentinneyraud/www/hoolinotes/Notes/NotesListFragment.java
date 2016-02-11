package fr.quentinneyraud.www.hoolinotes.Notes;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

import fr.quentinneyraud.www.hoolinotes.Notes.Note;
import fr.quentinneyraud.www.hoolinotes.Notes.NoteAdapter;
import fr.quentinneyraud.www.hoolinotes.R;
import fr.quentinneyraud.www.hoolinotes.User.SessionManager;


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
            public void onClick(View v, String id) {
                Log.d(TAG, "Click on " + String.valueOf(id));
            }
        });

        rcView.setAdapter(noteAdapter);

        this.loadUserNotes();

        return view;
    }

    private void loadUserNotes(){
        SessionManager.getUser().getNotes(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                // Create instance pass it to adapter
                Note note = dataSnapshot.getValue(Note.class);
                note.setId(dataSnapshot.getKey());
                noteAdapter.addNote(note);
                noteAdapter.notifyItemInserted(noteAdapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}

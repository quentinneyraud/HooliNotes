package fr.quentinneyraud.www.hoolinotes.Notes;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.quentinneyraud.www.hoolinotes.R;
import fr.quentinneyraud.www.hoolinotes.User.SessionManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesListFragment extends Fragment implements NoteAdapter.NoteClickListener, SessionManager.NotesListener {

    private static final String TAG = "NOTES LIST FRAGMENT ===";

    private NoteAdapter noteAdapter;
    private NotesListListener notesListListener;

    public NotesListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        // Get recycler view
        RecyclerView rcView = (RecyclerView) view.findViewById(R.id.notes_list_recycler_view);
        rcView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        // Create adapter
        noteAdapter = new NoteAdapter(new ArrayList<Note>());

        // Set listener
        noteAdapter.setNoteClickListener(this);

        rcView.setAdapter(noteAdapter);

        return view;
    }

    public void setLocation(Location location){
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NotesListListener) {
            notesListListener = (NotesListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NotesListListener");
        }
    }

    public void onNoteAdded(Note note){
        noteAdapter.addNote(note);
        noteAdapter.notifyItemInserted(noteAdapter.getItemCount() - 1);
    }

    @Override
    public void onClick(View v, String id) {
        notesListListener.onItemSelectedOnList(id);
    }

    public interface NotesListListener{
        void onItemSelectedOnList(String id);
    }
}

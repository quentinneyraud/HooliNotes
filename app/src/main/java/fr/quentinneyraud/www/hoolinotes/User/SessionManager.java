package fr.quentinneyraud.www.hoolinotes.User;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.quentinneyraud.www.hoolinotes.Notes.Note;
import fr.quentinneyraud.www.hoolinotes.Notes.NoteList;
import fr.quentinneyraud.www.hoolinotes.R;

/**
 * Created by quentin on 08/02/16.
 */
public class SessionManager {

    private static ArrayList<NotesListener> notesListener = new ArrayList<NotesListener>();
    private static User user = null;
    public static Map<String, Note> notesList = new HashMap<String, Note>();

    public static User getUser(){
        return user;
    }

    public static void setNotesListener(NotesListener nl){
        notesListener.add(nl);
    }

    public static void setUser(Context context, String uId){
        user = new User(context, uId);
    }

    public static void auth(Context context, String email, String password, Firebase.AuthResultHandler firebaseAuthResultHandler){
        String firebaseUrl = context.getResources().getString(R.string.firebase_base);

        new Firebase(firebaseUrl).authWithPassword(email, password, firebaseAuthResultHandler);
    }

    public static void ListenNotes(){
        user.ListenNotes(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Note note = dataSnapshot.getValue(Note.class);
                note.setId(dataSnapshot.getKey());
                notesList.put(dataSnapshot.getKey(), note);

                for(NotesListener nl : notesListener){
                    nl.onNoteAdded(note);
                }
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

    public static void AddNote(HashMap<String, Object> note){
        user.AddNote(note);
    }

    public static Note getNoteById(String id){
        return (notesList.get(id) != null) ? notesList.get(id) : null;
    }

    public interface NotesListener{
        void onNoteAdded(Note note);
    }

}

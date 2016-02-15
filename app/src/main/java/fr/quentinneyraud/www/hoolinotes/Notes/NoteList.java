package fr.quentinneyraud.www.hoolinotes.Notes;

import java.util.ArrayList;

/**
 * Created by quentin on 15/02/16.
 */
public class NoteList {

    private ArrayList<Note> notes;

    public NoteList() {
    }

    public void AddNote(Note note){
        notes.add(note);
    }

    public void SortByDistance(double latitude, double longitude){

        if(notes.isEmpty()){
            return;
        }

        ArrayList<Note> sortedNotes;

        for (Note note: notes) {
            
        }
    }

    private double DegreeToRadian(double degree){
        return degree*Math.PI/180;
    }

    private double GetDistance(){
        return 4;
    }
}

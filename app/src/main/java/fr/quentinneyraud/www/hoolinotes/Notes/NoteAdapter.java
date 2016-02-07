package fr.quentinneyraud.www.hoolinotes.Notes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.quentinneyraud.www.hoolinotes.R;

/**
 * Created by quentin on 07/02/16.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{

    private List<Note> notes;
    private static NoteClickListener noteClickListener;

    public void setNoteClickListener(NoteClickListener pnoteClickListener) {
        noteClickListener = pnoteClickListener;
    }

    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NoteViewHolder holder;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_note, parent, false);

        holder = new NoteViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = notes.get(position);

        holder.setId((long) 55);
        holder.getTitleElement().setText(note.getTitle());
        holder.getTextElement().setText(note.getText());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void addNotes(List<Note> allNotes){
        notes.addAll(allNotes);
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        // UI getters

        public TextView getTitleElement() {
            return (TextView) itemView.findViewById(R.id.note_title);
        }

        public TextView getTextElement() {
            return (TextView) itemView.findViewById(R.id.note_description);
        }

        public NoteViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(noteClickListener == null){
                return;
            }

            noteClickListener.onClick(v, this.getId());
        }
    }

    public interface NoteClickListener{

        void onClick(View v, long i);

    }

}

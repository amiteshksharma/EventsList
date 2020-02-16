package com.example.eventslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {

    private List<NoteValues> notes;
    private Context context;
    private OnNoteListener mOnNoteListener;

    public NotesAdapter(Context context, List<NoteValues> notes, OnNoteListener onNoteListener){
        this.notes = notes;
        this.context = context;
        this.mOnNoteListener = onNoteListener;

    }

    public void setmOnNoteListener(OnNoteListener onNoteListener){
        this.mOnNoteListener = onNoteListener;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.create_tile_layout, parent, false);
        return new NoteHolder(v, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        final NoteValues note = getNote(position);
        if(note != null){
            holder.noteText.setText(note.getTitle());
            holder.noteDate.setText(note.getDate());
            holder.noteLocation.setText(note.getLocation());
            holder.noteDescription.setText(note.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    private NoteValues getNote(int position){
        return notes.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView noteText, noteDate, noteLocation, noteDescription;
        OnNoteListener onNoteListener;

        public NoteHolder(@NonNull final View itemView, final OnNoteListener onNoteListener) {
            super(itemView);
            noteDate = itemView.findViewById(R.id.eventDate);
            noteText = itemView.findViewById(R.id.eventTitle);
            noteLocation = itemView.findViewById(R.id.locationText);
            noteDescription = itemView.findViewById(R.id.eventDescription);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnNoteListener.onNoteLongClick(getAdapterPosition());
                    return false;
                }
            });
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }

    }

    public interface OnNoteListener{
        void onNoteClick(int position);

        void onNoteLongClick(int position);
    }
}

package com.example.notes;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView favoriteTextView;
    private final TextView categoryTextView;
    private final TextView titleTextView;
    private final TextView dateTextView;
    private final CardView cardView;
    private NoteEntity noteEntity;

    private final String favoriteString;

    public NoteViewHolder(@NonNull ViewGroup parent, NotesAdapter.OnItemClickListener onItemClickListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
        cardView = (CardView) itemView;
        favoriteTextView = itemView.findViewById(R.id.favorite_text_view);
        categoryTextView = itemView.findViewById(R.id.category_text_view);
        titleTextView = itemView.findViewById(R.id.title_text_view);
        dateTextView = itemView.findViewById(R.id.date_text_view);
        cardView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(noteEntity);
            }
        });

        favoriteString = itemView.getResources().getString(R.string.favorite);
    }

    public void bind(NoteEntity noteEntity) {
        this.noteEntity = noteEntity;
        favoriteTextView.setText(noteEntity.getIsFavorite() ? favoriteString : "");
        categoryTextView.setText(noteEntity.getCategory());
        titleTextView.setText(noteEntity.getTitle());
        dateTextView.setText(noteEntity.getStringDate());
    }
}

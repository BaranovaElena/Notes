package com.example.notes.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.repo.NoteEntity;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView favoriteTextView;
    private final TextView categoryTextView;
    private final TextView titleTextView;
    private final TextView dateTextView;
    private NoteEntity noteEntity;

    private final String favoriteString;

    public NoteViewHolder(@NonNull ViewGroup parent,
                          NotesAdapter.OnItemClickListener onItemClickListener,
                          NotesAdapter.OnItemDeleteListener onItemDeleteListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
        CardView cardView = (CardView) itemView;
        favoriteTextView = itemView.findViewById(R.id.favorite_text_view);
        categoryTextView = itemView.findViewById(R.id.category_text_view);
        titleTextView = itemView.findViewById(R.id.title_text_view);
        dateTextView = itemView.findViewById(R.id.date_text_view);

        cardView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(noteEntity);
            }
        });

        itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.setHeaderTitle(R.string.item_menu_title);
            menu.add(R.string.edit).setOnMenuItemClickListener(item -> {
                onItemClickListener.onItemClick(noteEntity);
                return false;
            });
            menu.add(R.string.delete).setOnMenuItemClickListener(item -> {
                onItemDeleteListener.onItemDelete(noteEntity);
                return false;
            });
        });

        cardView.setOnLongClickListener(v -> {
            itemView.showContextMenu();
            return true;
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

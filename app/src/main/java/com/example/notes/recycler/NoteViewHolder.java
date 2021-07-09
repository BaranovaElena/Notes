package com.example.notes.recycler;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private final CardView cardView;
    private NoteEntity noteEntity;

    private final String favoriteString;

    public NoteViewHolder(@NonNull ViewGroup parent,
                          NotesAdapter.OnItemClickListener onItemClickListener,
                          NotesAdapter.OnItemDialogDeleteListener onItemDialogDeleteListener) {
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

        itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.setHeaderTitle(R.string.item_menu_title);
            menu.add(R.string.edit).setOnMenuItemClickListener(item -> {
                createEditDialog(onItemClickListener);
                return false;
            });
            menu.add(R.string.delete).setOnMenuItemClickListener(item -> {
                createDeleteDialog(onItemDialogDeleteListener);
                return false;
            });
        });

        cardView.setOnLongClickListener(v -> {
            itemView.showContextMenu();
            return true;
        });

        favoriteString = itemView.getResources().getString(R.string.favorite);
    }

    private void createEditDialog(NotesAdapter.OnItemClickListener onItemClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cardView.getContext());
        builder.setTitle(R.string.dialog_edit_title)
                .setMessage(R.string.dialog_edit_message)
                .setPositiveButton(R.string.dialog_edit_btn_yes, (dialog, which) -> {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(noteEntity);
                    }
                })
                .setNegativeButton(R.string.dialog_edit_btn_no, (dialog, which) ->
                        Toast.makeText(itemView.getContext(), R.string.dialog_edit_btn_no_toast, Toast.LENGTH_SHORT).show())
                .show();
    }

    private void createDeleteDialog(NotesAdapter.OnItemDialogDeleteListener onItemDialogDeleteListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cardView.getContext());
        builder.setTitle(R.string.dialog_delete_title)
                .setMessage(R.string.dialog_delete_message)
                .setPositiveButton(R.string.dialog_delete_btn_yes, (dialog, which) -> {
                    if (onItemDialogDeleteListener != null) {
                        onItemDialogDeleteListener.onItemDialogDelete(noteEntity);
                    }
                })
                .setNegativeButton(R.string.dialog_delete_btn_no, (dialog, which) ->
                        Toast.makeText(itemView.getContext(), R.string.dialog_delete_btn_no_toast, Toast.LENGTH_SHORT).show())
                .show();
    }

    public void bind(NoteEntity noteEntity) {
        this.noteEntity = noteEntity;
        favoriteTextView.setText(noteEntity.getIsFavorite() ? favoriteString : "");
        categoryTextView.setText(noteEntity.getCategory());
        titleTextView.setText(noteEntity.getTitle());
        dateTextView.setText(noteEntity.getStringDate());
    }
}

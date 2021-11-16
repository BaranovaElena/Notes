package com.example.notes;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notes.recycler.NotesAdapter;
import com.example.notes.repo.NoteEntity;
import com.example.notes.repo.NotesRepo;
import com.example.notes.repo.NotesRepoImplDummy;

public class NotesFragment extends Fragment {
    private static NotesRepo notesRepo;
    private static RecyclerView recyclerView;
    private NotesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesRepo = new NotesRepoImplDummy();
        notesRepo.setListener(() -> adapter.setList(notesRepo.getNotes()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new NotesAdapter();
        adapter.setOnItemListener(new NotesAdapter.OnItemListener() {
            @Override
            public void onItemClick(@Nullable NoteEntity note) {
                createEditDialog(note);
            }

            @Override
            public void onItemDelete(@Nullable NoteEntity note) {
                createDeleteDialog(note);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                notesRepo.moveNote(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adapter.changeItemPosition(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                createDeleteDialog(adapter.getItemByPosition(viewHolder.getAdapterPosition()));
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.setList(notesRepo.getNotes());
    }

    private void createDeleteDialog(NoteEntity note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.dialog_delete_title)
                .setMessage(R.string.dialog_delete_message)
                .setPositiveButton(R.string.dialog_delete_btn_yes, (dialog, which) -> {
                    notesRepo.deleteNote(note);
                    adapter.removeItem(note);
                })
                .setNegativeButton(R.string.dialog_delete_btn_no, (dialog, which) -> {
                        Toast.makeText(getContext(), R.string.dialog_delete_btn_no_toast, Toast.LENGTH_SHORT).show();
                        adapter.setList(notesRepo.getNotes());
                })
                .show();
    }

    private void createEditDialog(NoteEntity note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.dialog_edit_title)
                .setMessage(R.string.dialog_edit_message)
                .setPositiveButton(R.string.dialog_edit_btn_yes, (dialog, which) ->
                        ((Controller) requireActivity()).openNoteScreen(note))
                .setNegativeButton(R.string.dialog_edit_btn_no, (dialog, which) ->
                        Toast.makeText(getContext(), R.string.dialog_edit_btn_no_toast, Toast.LENGTH_SHORT).show())
                .show();
    }

    public boolean saveEditResult(NoteEntity oldNote, NoteEntity newNote) {
        boolean isExistingNoteUpdated = notesRepo.updateNote(newNote);
        if (isExistingNoteUpdated) {
            adapter.removeItem(oldNote);
        }
        adapter.addItem(newNote);
        return isExistingNoteUpdated;
    }

    interface Controller {
        void openNoteScreen(NoteEntity note);
    }

    @Override
    public void onDestroy() {
        notesRepo.deleteListeners();
        super.onDestroy();
    }
}
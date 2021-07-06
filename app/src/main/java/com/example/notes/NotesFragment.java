package com.example.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NotesFragment extends Fragment {
    private static final NotesRepo notesRepo;
    private static RecyclerView recyclerView;
    private NotesAdapter adapter;

    //пока нет базы, будет статический блок инициализации
    //чтобы при повороте экрана не терять изменения в списке заметок
    static {
        notesRepo = new NotesRepo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        adapter.setOnItemClickListener(((Controller) requireActivity())::openNoteScreen);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setList(notesRepo.getNotesArray());
    }

    public boolean saveEditResult(NoteEntity newNote) {
        boolean isExistingNoteUpdated = notesRepo.updateNote(newNote);
        adapter.setList(notesRepo.getNotesArray());
        return isExistingNoteUpdated;
    }

    interface Controller {
        void openNoteScreen(NoteEntity note);
    }
}
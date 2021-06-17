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
    private static NotesRepo notesArray;
    private static RecyclerView recyclerView;
    private NotesAdapter adapter;

    //пока нет базы, будет статический блок инициализации
    //чтобы при повороте экрана не терять изменения в списке заметок
    static {
        notesArray = new NotesRepo();
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

        adapter.setList(notesArray.getNotesArray());
    }

    public boolean saveEditResult(NoteEntity newNote) {
        //если старая заметка редактировалась, обновляем и переносим в конец списка
        if (notesArray.contains(newNote)) {
            notesArray.updateNote(newNote);
            adapter.setList(notesArray.getNotesArray());
            return true;
        } else {
            //если новая заметка, добавляем
            notesArray.addNote(newNote);
            adapter.setList(notesArray.getNotesArray());
            return false;
        }
    }

    interface Controller {
        void openNoteScreen(NoteEntity note);
    }
}
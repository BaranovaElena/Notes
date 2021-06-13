package com.example.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;

public class NotesFragment extends Fragment {
    private ArrayList<NoteEntity> notesArray;
    private RecyclerView recyclerView;
    private NotesAdapter adapter;

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

        notesArray = new ArrayList<>();
        initNotesList();
    }

    private void initNotesList() {
        long dateInMills = Calendar.getInstance().getTimeInMillis();
        notesArray.add(new NoteEntity(
                "Note1", "first note", dateInMills, "my first note", "work", true));
        notesArray.add(new NoteEntity(
                "Note2", "second note", dateInMills, "my second note", "work", false));
        notesArray.add(new NoteEntity(
                "Note3", "third note", dateInMills, "my third note", "study", false));
        notesArray.add(new NoteEntity(
                "Note4", "fourth note", dateInMills, "my fourth note", "study", true));
        notesArray.add(new NoteEntity(
                "Note5", "fifth note", dateInMills, "my fifth note", "no category", false));

        adapter.setList(notesArray);
    }

    public boolean saveEditResult(NoteEntity newNote) {
        boolean isUpdated = false;
        for (int i = 0; i < notesArray.size(); i++) {
            //если старая заметка редактировалась, обновляем и переносим в конец списка
            if (notesArray.get(i).getIdentifier() == newNote.getIdentifier()) {
                notesArray.remove(i);
                notesArray.add(newNote);
                isUpdated = true;
            }
        }
        //если новая заметка, добавляем
        if (!isUpdated) {
            notesArray.add(newNote);
        }
        adapter.setList(notesArray);
        return isUpdated;
    }

    interface Controller {
        void openNoteScreen(NoteEntity note);
    }
}
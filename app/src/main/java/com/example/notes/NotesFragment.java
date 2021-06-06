package com.example.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class NotesFragment extends Fragment implements OneNoteFragment.Controller {
    private ArrayList<NoteEntity> notesArray;

    private LinearLayout layoutNotesList;

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
        layoutNotesList = view.findViewById(R.id.fragment_notes_layout);

        notesArray = new ArrayList<>();
        initNotesList();
    }

    private void initNotesList() {
        notesArray.add(new NoteEntity(
                "Note1", "first note", "20.05.2021", "my first note"));
        notesArray.add(new NoteEntity(
                "Note2", "second note", "21.05.2021", "my second note"));
        notesArray.add(new NoteEntity(
                "Note3", "third note", "22.05.2021", "my third note"));
        notesArray.add(new NoteEntity(
                "Note4", "fourth note", "23.05.2021", "my fourth note"));
        notesArray.add(new NoteEntity(
                "Note5", "fifth note", "24.05.2021", "my fifth note"));

        //кнопки для всего списка заметок
        for (NoteEntity note : notesArray) {
            addNewButton(note);
        }
    }

    private void addNewButton(NoteEntity note) {
        MaterialButton button = new MaterialButton(requireContext());

        setButtonProperties(button);
        //название заметки жирным шрифтом
        button.setText(Html.fromHtml("<b>" + note.getTitle() + "</b>\t(" + note.getDate() + ")"));
        button.setOnClickListener(v ->
                //по нажатию открываем фрагмент для редактирования
                ((Controller) requireActivity()).openNoteScreen(note));

        layoutNotesList.addView(button);
    }

    private void setButtonProperties(MaterialButton button) {
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.background));
        button.setStrokeWidthResource(R.dimen.button_stroke_width);
        button.setStrokeColorResource(R.color.button_stroke);
        button.setTextSize(getResources().getDimension(R.dimen.note_list_text_size));
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.button_text));
        button.setGravity(Gravity.CENTER);
    }

    @Override
    public void saveResult(NoteEntity newNote) {
        boolean isUpdated = false;
        for (int i = 0; i < notesArray.size(); i++) {
            //если старая заметка редактировалась, обновляем и переносим в конец списка
            if (notesArray.get(i).getIdentifier() == newNote.getIdentifier()) {
                layoutNotesList.removeViewAt(i+1); //+1 тк 1-й элемент - кнопка create_new_note
                addNewButton(newNote);
                notesArray.remove(i);
                notesArray.add(newNote);
                isUpdated = true;
            }
        }
        //если новая заметка, добавляем
        if (!isUpdated) {
            addNewButton(newNote);
            notesArray.add(newNote);
        }
    }

    interface Controller {
        void openNoteScreen(NoteEntity note);
    }
}
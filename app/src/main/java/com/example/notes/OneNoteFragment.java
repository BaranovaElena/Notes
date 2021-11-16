package com.example.notes;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.notes.repo.NoteEntity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class OneNoteFragment extends Fragment {
    public static final String GET_NOTE_EXTRA_KEY = "EXTRA_KEY";

    private NoteEntity noteEntity;

    private TextInputEditText titleEditText;
    private TextView creationDateTextView;
    private TextInputEditText descriptionEditText;
    private TextInputEditText noteEditText;
    private MaterialButton buttonSave;
    private TextView categoryEditText;
    private CheckBox favoriteCheck;

    public OneNoteFragment() {
        noteEntity = new NoteEntity();
    }

    public static OneNoteFragment newInstance(NoteEntity note) {
        OneNoteFragment fragment = new OneNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(GET_NOTE_EXTRA_KEY, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            NoteEntity args = getArguments().getParcelable(GET_NOTE_EXTRA_KEY);
            if (args != null) {
                noteEntity = args;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_note, null);

        titleEditText = view.findViewById(R.id.title_edit_text);
        creationDateTextView = view.findViewById(R.id.creation_date_text_view);
        descriptionEditText = view.findViewById(R.id.description_edit_text);
        noteEditText = view.findViewById(R.id.note_edit_text);
        categoryEditText = view.findViewById(R.id.category_edit_text);
        favoriteCheck = view.findViewById(R.id.is_favorite_checkbox);

        buttonSave = view.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(v -> saveAndExit());

        return view;
    }

    //обработчик кнопки сохранить
    private void saveAndExit() {
        //так как обновляем данные во фрагменте-списке, получаем к нему доступ по тегу
        Controller controller = (Controller) getActivity();

        //обновляем дату отредактированной заметки
        long currentDate = Calendar.getInstance().getTimeInMillis();

        if (controller != null) {
            //передаем заметку во фрагмент-список
            NoteEntity newNote = new NoteEntity(
                    noteEntity.getId(),
                    (titleEditText.getText() == null ? "" : titleEditText.getText().toString()),
                    (descriptionEditText.getText() == null ? "" : descriptionEditText.getText().toString()),
                    currentDate,
                    (noteEditText.getText() == null ? "" : noteEditText.getText().toString()),
                    (categoryEditText.getText() == null ? "" : categoryEditText.getText().toString()),
                    favoriteCheck.isChecked());
            controller.saveResult(noteEntity, newNote);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleEditText.setText(noteEntity.getTitle());
        creationDateTextView.setText(noteEntity.getStringDate());
        descriptionEditText.setText(noteEntity.getDescription());
        noteEditText.setText(noteEntity.getText());
        categoryEditText.setText(noteEntity.getCategory());
        favoriteCheck.setChecked(noteEntity.getIsFavorite());
    }

    public interface Controller {
        void saveResult(NoteEntity oldNote, NoteEntity newNote);
    }
}
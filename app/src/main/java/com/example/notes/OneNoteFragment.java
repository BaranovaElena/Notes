package com.example.notes;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class OneNoteFragment extends Fragment {
    public static final String GET_NOTE_EXTRA_KEY = "EXTRA_KEY";

    private NoteEntity noteEntity = null;

    private TextInputEditText titleEditText;
    private TextView creationDateTextView;
    private TextInputEditText descriptionEditText;
    private TextInputEditText noteEditText;
    private MaterialButton buttonSave;

    public OneNoteFragment() {}

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
                    noteEntity.getIdentifier(),
                    (titleEditText.getText() == null ? "" : titleEditText.getText().toString()),
                    (descriptionEditText.getText() == null ? "" : descriptionEditText.getText().toString()),
                    currentDate,
                    (noteEditText.getText() == null ? "" : noteEditText.getText().toString()));
            controller.saveResult(newNote);
        }
        //закрываем текущий фрагмент с редактированием
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(this)
                .commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleEditText.setText(noteEntity.getTitle());
        creationDateTextView.setText(String.valueOf(noteEntity.getDate()));
        descriptionEditText.setText(noteEntity.getDescription());
        noteEditText.setText(noteEntity.getText());
    }

    public interface Controller {
        void saveResult(NoteEntity note);
    }
}
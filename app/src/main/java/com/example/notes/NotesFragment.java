package com.example.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesFragment extends Fragment {
    private final NoteEntity[] notes = {
            new NoteEntity("Note1", "first note", "20.05.2021", "my first note"),
            new NoteEntity("Note2", "second note", "21.05.2021", "my second note"),
            new NoteEntity("Note3", "third note", "22.05.2021", "my third note"),
            new NoteEntity("Note4", "fourth note", "23.05.2021", "my fourth note"),
            new NoteEntity("Note5", "fifth note", "24.05.2021", "my fifth note")};

    NotesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNotesList(view);
    }

    private void initNotesList(View view) {
        LinearLayout layoutNotesList = (LinearLayout)view;

        for (NoteEntity note : notes) {
            TextView tv = new TextView(getContext());
            tv.setText(Html.fromHtml("<b>"+note.getTitle()+"</b>\t("+note.getDate()+")"));
            tv.setTextSize(getResources().getDimension(R.dimen.note_list_text_size));
            tv.setGravity(Gravity.CENTER);
            layoutNotesList.addView(tv);
        }
    }
}
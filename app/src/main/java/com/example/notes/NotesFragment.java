package com.example.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesFragment extends Fragment {

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
        String[] notes = getResources().getStringArray(R.array.notes_titles);

        for (String note : notes) {
            TextView tv = new TextView(getContext());
            tv.setText(note);
            tv.setTextSize(getResources().getDimension(R.dimen.note_list_text_size));
            tv.setGravity(Gravity.CENTER);
            layoutNotesList.addView(tv);
        }
    }
}
package com.example.notes;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class OneNoteFragment extends Fragment {
    public static final String EXTRA_KEY = "EXTRA_KEY";
    private static final String TAG = "@@@ OneNoteFragment";

    private NoteEntity noteEntity = null;

    private TextInputEditText title_edit_text;
    private TextView creation_date_text_view;
    private TextInputEditText description_edit_text;
    private TextInputEditText note_edit_text;

    public OneNoteFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment OneNoteFragment.
     */
    public static OneNoteFragment newInstance(NoteEntity note) {
        OneNoteFragment fragment = new OneNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_KEY, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            noteEntity = getArguments().getParcelable(EXTRA_KEY);
        }
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        View view = inflater.inflate(R.layout.fragment_one_note, null);

        title_edit_text = view.findViewById(R.id.title_edit_text);
        creation_date_text_view = view.findViewById(R.id.creation_date_text_view);
        description_edit_text = view.findViewById(R.id.description_edit_text);
        note_edit_text = view.findViewById(R.id.note_edit_text);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title_edit_text.setText(noteEntity.getTitle());
        creation_date_text_view.setText(noteEntity.getDate());
        description_edit_text.setText(noteEntity.getDescription());
        note_edit_text.setText(noteEntity.getText());

        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
    }
}
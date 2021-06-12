package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements NotesFragment.Controller, OneNoteFragment.Controller{
    public final String NOTES_FRAGMENT_TAG = "NOTES_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.layout_notes_list, new NotesFragment(), NOTES_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void openNoteScreen(NoteEntity note) {
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        getSupportFragmentManager()
                .beginTransaction()
                .add(isLandscape ? R.id.layout_one_note : R.id.layout_notes_list, OneNoteFragment.newInstance(note))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void saveResult(NoteEntity newNote) {
        NotesFragment notesFragment = (NotesFragment) getSupportFragmentManager()
                .findFragmentByTag(NOTES_FRAGMENT_TAG);
        if (notesFragment != null) {
            notesFragment.saveEditResult(newNote);
        }
    }
}
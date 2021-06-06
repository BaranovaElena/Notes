package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements NotesFragment.Controller{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.layout_notes_list, new NotesFragment())
                .commit();
    }

    @Override
    public void openNoteScreen(NoteEntity note) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.layout_notes_list, OneNoteFragment.newInstance(note))
                .addToBackStack(null)
                .commit();
    }
}
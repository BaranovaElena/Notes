package com.example.notes.repo;

import java.util.ArrayList;

public interface NotesRepo {
    void addNote(NoteEntity note);
    boolean updateNote(NoteEntity newNote);
    void deleteNote(NoteEntity note);
    ArrayList<NoteEntity> getNotes();

    void setListener(Notifier notifier);
    void deleteListener(Notifier notifier);
    void deleteListeners();

    interface Notifier {
        void onUpdateRepo();
    }
}

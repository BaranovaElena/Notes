package com.example.notes;

import java.util.ArrayList;

public interface NotesRepo {
    void addNote(NoteEntity note);
    boolean updateNote(NoteEntity newNote);
    void deleteNote(NoteEntity note);
    ArrayList<NoteEntity> getNotes();

    void setListener(Notifier notifier);

    public interface Notifier {
        void updateRepo();
    }
}

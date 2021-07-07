package com.example.notes.repo;

import java.util.ArrayList;
import java.util.Calendar;

public class NotesRepoImplDummy implements NotesRepo{
    private final ArrayList<NoteEntity> notesArray;

    NotesRepoImplDummy() {
        notesArray = new ArrayList<>();
        long dateInMills = Calendar.getInstance().getTimeInMillis();

        notesArray.add(new NoteEntity("1", "Note1", "first note",
                dateInMills, "my first note", "work", true));
        notesArray.add(new NoteEntity("2", "Note2", "second note",
                dateInMills, "my second note", "work", false));
        notesArray.add(new NoteEntity("3", "Note3", "third note",
                dateInMills, "my third note", "study", false));
        notesArray.add(new NoteEntity("4", "Note4", "fourth note",
                dateInMills, "my fourth note", "study", true));
        notesArray.add(new NoteEntity("5", "Note5", "fifth note",
                dateInMills, "my fifth note", "no category", false));
    }

    @Override
    public ArrayList<NoteEntity> getNotes() {
        return notesArray;
    }

    @Override
    public void setListener(Notifier notifier) {

    }

    @Override
    public void addNote(NoteEntity note) {
        notesArray.add(note);
    }

    @Override
    public boolean updateNote(NoteEntity newNote) {
        boolean isExistingNoteUpdated = false;
        //если старая заметка редактировалась, обновляем и переносим в конец списка
        //если новая заметка, только добавляем в конец списка
        for (NoteEntity thisNote : notesArray) {
            if (thisNote.getId() == newNote.getId()) {
                notesArray.remove(thisNote);
                isExistingNoteUpdated = true;
                break;
            }
        }
        notesArray.add(newNote);
        return isExistingNoteUpdated;
    }

    @Override
    public void deleteNote(NoteEntity note) {
        notesArray.remove(note);
    }

    boolean contains(NoteEntity note) {
        for (NoteEntity thisNote : notesArray) {
            if (thisNote.getId() == note.getId()) {
                return true;
            }
        }
        return false;
    }
}

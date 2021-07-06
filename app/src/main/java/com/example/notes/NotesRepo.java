package com.example.notes;

import java.util.ArrayList;
import java.util.Calendar;

public class NotesRepo {
    private final ArrayList<NoteEntity> notesArray;

    NotesRepo() {
        notesArray = new ArrayList<>();
        long dateInMills = Calendar.getInstance().getTimeInMillis();

        notesArray.add(new NoteEntity(
                "Note1", "first note", dateInMills, "my first note", "work", true));
        notesArray.add(new NoteEntity(
                "Note2", "second note", dateInMills, "my second note", "work", false));
        notesArray.add(new NoteEntity(
                "Note3", "third note", dateInMills, "my third note", "study", false));
        notesArray.add(new NoteEntity(
                "Note4", "fourth note", dateInMills, "my fourth note", "study", true));
        notesArray.add(new NoteEntity(
                "Note5", "fifth note", dateInMills, "my fifth note", "no category", false));
    }

    public ArrayList<NoteEntity> getNotesArray() {
        return notesArray;
    }

    void addNote(NoteEntity note) {
        notesArray.add(note);
    }

    boolean updateNote(NoteEntity newNote) {
        boolean isExistingNoteUpdated = false;
        //если старая заметка редактировалась, обновляем и переносим в конец списка
        //если новая заметка, только добавляем в конец списка
        for (NoteEntity thisNote : notesArray) {
            if (thisNote.getIdentifier() == newNote.getIdentifier()) {
                notesArray.remove(thisNote);
                isExistingNoteUpdated = true;
                break;
            }
        }
        notesArray.add(newNote);
        return isExistingNoteUpdated;
    }

    void deleteNote(NoteEntity note) {
        notesArray.remove(note);
    }

    boolean contains(NoteEntity note) {
        for (NoteEntity thisNote : notesArray) {
            if (thisNote.getIdentifier() == note.getIdentifier()) {
                return true;
            }
        }
        return false;
    }
}

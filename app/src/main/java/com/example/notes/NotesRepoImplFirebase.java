package com.example.notes;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NotesRepoImplFirebase implements NotesRepo {
    private static final String NOTES_COLLECTION_NAME = "notes";
    private final FirebaseFirestore db;
    private final CollectionReference collection;
    private ArrayList<NoteEntity> notesArray = new ArrayList<>();

    private Notifier notifier;

    NotesRepoImplFirebase() {
        db = FirebaseFirestore.getInstance();
        collection = db.collection(NOTES_COLLECTION_NAME);
        collection.get().addOnSuccessListener(queryDocumentSnapshots ->
            updateNotesFromDB(queryDocumentSnapshots.getDocuments()));
        collection.addSnapshotListener((value, error) -> {
            if (value != null) {
                updateNotesFromDB(value.getDocuments());
            }
        });
    }

    void updateNotesFromDB( List<DocumentSnapshot> docs) {
        notesArray = new ArrayList<>();
        for (DocumentSnapshot doc : docs) {
            notesArray.add(doc.toObject(NoteEntity.class));
        }
        notifier.updateRepo();
    }

    @Override
    public void addNote(NoteEntity note) {
        notesArray.add(note);
        collection.add(note);
    }

    @Override
    public boolean updateNote(NoteEntity newNote) {
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
        addNote(newNote);
        return isExistingNoteUpdated;
    }

    @Override
    public void deleteNote(NoteEntity note) {

    }

    @Override
    public ArrayList<NoteEntity> getNotes() {
        return notesArray;
    }

    @Override
    public void setListener(Notifier notifier) {
        this.notifier = notifier;
    }
}

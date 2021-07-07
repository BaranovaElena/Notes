package com.example.notes;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NotesRepoImplFirebase implements NotesRepo {
    private static final String NOTES_COLLECTION_NAME = "notes";
    private final CollectionReference collection;
    private ArrayList<NoteEntity> notesArray = new ArrayList<>();

    private Notifier notifier;

    NotesRepoImplFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        collection = db.collection(NOTES_COLLECTION_NAME);
        collection.get().addOnSuccessListener(queryDocumentSnapshots ->
                updateNotesFromDB(queryDocumentSnapshots.getDocuments()));
        collection.addSnapshotListener((value, error) -> {
            if (value != null) {
                updateNotesFromDB(value.getDocuments());
            }
        });
    }

    void updateNotesFromDB(List<DocumentSnapshot> docs) {
        notesArray = new ArrayList<>();
        for (DocumentSnapshot doc : docs) {
            notesArray.add(doc.toObject(NoteEntity.class));
        }
        notifier.updateRepo();
    }

    @Override
    public void addNote(NoteEntity note) {
        collection.add(note).addOnSuccessListener(documentReference -> {
            note.setId(documentReference.getId());
            notesArray.add(note);
            collection.document(documentReference.getId()).set(note);
        });

    }

    @Override
    public boolean updateNote(NoteEntity newNote) {
        boolean isExistingNoteUpdated = false;
        //если старая заметка редактировалась, обновляем и переносим в конец списка
        //если новая заметка, только добавляем в конец списка
        for (NoteEntity thisNote : notesArray) {
            if (thisNote.getId().equals(newNote.getId())) {
                deleteNote(thisNote);
                isExistingNoteUpdated = true;
                break;
            }
        }
        addNote(newNote);
        return isExistingNoteUpdated;
    }

    @Override
    public void deleteNote(NoteEntity note) {
        collection.document(note.getId()).delete().addOnSuccessListener(unused ->
                notesArray.remove(note));
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

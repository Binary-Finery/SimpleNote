package com.spencerstudios.simplenote.utilities;

import com.spencerstudios.simplenote.Note;
import com.spencerstudios.simplenote.ObjectBox;

import java.util.List;

import io.objectbox.Box;

public class DbUtils {

    private Box<Note> noteBox;

    public DbUtils() {
        noteBox = ObjectBox.get().boxFor(Note.class);
    }

    public List<Note> getAllNotes() {
        return noteBox.getAll();
    }

    public void addNote(String content) {
        Note note = new Note();
        note.content = content;
        note.date = System.currentTimeMillis();
        noteBox.put(note);
    }

    public void updateNote(long id, String content) {
        Note note = noteBox.get(id);
        note.content = content;
        note.date = System.currentTimeMillis();
        noteBox.put(note);
    }

    public void deleteNote(long id) {
        noteBox.remove(id);
    }

    public String getNote(long id){
        Note note = noteBox.get(id);
        return note.content;
    }
}

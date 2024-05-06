package com.example.noteee.listeners;

import com.example.noteee.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}

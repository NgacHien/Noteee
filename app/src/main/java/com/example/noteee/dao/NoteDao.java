package com.example.noteee.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.noteee.entities.Note;
import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY id DESC")
    LiveData<List<Note>> getAllNotes();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote (Note note);


    @Delete
    void  deleteNote(Note note);
}

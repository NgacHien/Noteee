package com.example.noteee.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.noteee.dao.NoteDao;
import com.example.noteee.entities.Note;

@Database(entities = {Note.class}, version = 2, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase notesDatabase;

    public static synchronized NotesDatabase getNotesDatabase(Context context){
        if (notesDatabase == null){
            notesDatabase = Room.databaseBuilder(
                            context.getApplicationContext(),
                            NotesDatabase.class,
                            "notes_db"
                    ).fallbackToDestructiveMigration() // Thêm vào đây để đảm bảo Room thực hiện cập nhật cơ sở dữ liệu một cách tự động bằng cách xóa cơ sở dữ liệu cũ và tạo lại từ đầu.
                    .build();
        }
        return notesDatabase;
    }

    public abstract NoteDao noteDao();
}

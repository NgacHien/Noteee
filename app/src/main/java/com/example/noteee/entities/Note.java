package com.example.noteee.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes" )
 public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "date time")
    public String dateTime;

    @ColumnInfo(name = "subtitle")
    private String subtitle;


    @ColumnInfo(name = "note_text")
    private String noteText;





    @ColumnInfo(name = "color")
    private String color;





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }



    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }



    @NonNull
    @Override
    public String toString() {
        return title + " : " + dateTime;
    }
}

package com.example.oaob.myapplication;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = Music.TABLE_MUSIC)
public class Music
{
    public static final String TABLE_MUSIC = "Music";

    @PrimaryKey(autoGenerate = true)
    public int key;

    public String title;
    public int resId;
    public Music()
    { }
    public Music(String title,int resId)
    {
        this.title = title;
        this.resId = resId;
    }
}
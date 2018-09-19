package com.example.oaob.myapplication;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
@Database(entities = {Music.class},version = 1)
public abstract class MusicDatabase extends RoomDatabase
{
    public abstract MusicDao musicDao();
}

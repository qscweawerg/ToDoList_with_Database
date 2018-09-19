package com.example.oaob.myapplication;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MusicDao
{
    @Query("select * from " + Music.TABLE_MUSIC)
    public List<Music> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Music expense);

    @Delete
    public void deleteData(Music expense);
}

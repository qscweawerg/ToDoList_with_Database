package com.example.oaob.myapplication;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ExpenseDao
{
    @Query("select * from " + Expense.TABLE_EXPENSE)
    public List<Expense> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Expense expense);

    @Delete
    public void deleteData(Expense expense);
}

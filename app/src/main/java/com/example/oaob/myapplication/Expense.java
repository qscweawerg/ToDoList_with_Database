package com.example.oaob.myapplication;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = Expense.TABLE_EXPENSE)
public class Expense
{
    public static final String TABLE_EXPENSE = "expense";

    @PrimaryKey(autoGenerate = true)
    public int key;

    public String title;
    public Expense()
    { }
}
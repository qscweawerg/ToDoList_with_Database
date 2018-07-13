package com.example.oaob.myapplication;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
@Database(entities = {Expense.class},version = 1)
public abstract class ExpenseDatabase extends RoomDatabase
{
    public abstract ExpenseDao expenseDao();
}

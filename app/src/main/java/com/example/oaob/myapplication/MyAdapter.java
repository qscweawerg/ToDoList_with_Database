package com.example.oaob.myapplication;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    public ArrayList<Expense> mData;
    public ExpenseDatabase db;
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTextView;
        Button myButton;
        ViewHolder(View v)
        {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.info_text);
            myButton = (Button) v.findViewById(R.id.button);
        }
    }

    MyAdapter(ArrayList<Expense> data, ExpenseDatabase database)
    {
        mData = data;
        db = database;
    }

    public void add_new_task(String s)
    {
        Expense expense=new Expense();
        expense.title=s;
        mData.add(expense);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position)
    {
        holder.mTextView.setText(mData.get(position).title);
        holder.myButton.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v)
            {
                db.expenseDao().deleteData(mData.get(position));
                mData.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }
}

package com.example.oaob.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    public ArrayList<Music> mData;
    public MusicDatabase db;
    public MainActivity activity;
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

    MyAdapter(ArrayList<Music> data, MusicDatabase database,MainActivity activity)
    {
        mData = data;
        db = database;
        this.activity = activity;
    }

    public void add_new_task(String s,int resId)
    {
        Music music =new Music();
        music.title=s;
        music.resId = resId;
        mData.add(music);
        notifyDataSetChanged();
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
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
                db.musicDao().deleteData(mData.get(position));
                mData.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.mTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(activity,MyService.class);
                Bundle bundle = new Bundle();
                bundle.putInt("resource",mData.get(position).resId);
                bundle.putString("title",mData.get(position).title);
                intent.putExtras(bundle);
                activity.startService(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }
}

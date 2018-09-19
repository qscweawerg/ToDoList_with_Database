package com.example.oaob.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    Toolbar toolbar;
    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MusicDatabase db = Room.databaseBuilder(getApplicationContext()
                ,MusicDatabase.class
                ,"database")
                .allowMainThreadQueries()
                .build();
        //ArrayList<Music> myDataset = (ArrayList<Music>)db.musicDao().getAll();
        ArrayList<Music> myDataset = new ArrayList<Music>();
        myDataset.add(new Music("music0",R.raw.music0));
        myDataset.add(new Music("music1",R.raw.music1));
        myDataset.add(new Music("music2",R.raw.music2));
        mAdapter = new MyAdapter(myDataset,db,this);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("有資料庫的App");
        toolbar.setSubtitle("按旁邊可以新增項目");
        setSupportActionBar(toolbar);

        btn = findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        @SuppressLint("InflateParams") View mView = getLayoutInflater().inflate(R.layout.dialog_add,null);
        final EditText editText = (EditText) mView.findViewById(R.id.task_name);
        Button submit = (Button) mView.findViewById(R.id.submit);
        Button cancel = (Button) mView.findViewById(R.id.cancel);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        submit.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                if(!editText.getText().toString().isEmpty())
                {
                    //Toast.makeText(MainActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                    //mAdapter.add_new_task(editText.getText().toString());
                    //mAdapter.db.musicDao().insert(mAdapter.mData.get(mAdapter.mData.size() - 1));
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"項目名不可為空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                dialog.dismiss();
            }
        });
        dialog.show();
        return super.onOptionsItemSelected(item);
    }
}

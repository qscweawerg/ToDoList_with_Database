package com.example.oaob.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    Toolbar toolbar;
    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExpenseDatabase db = Room.databaseBuilder(getApplicationContext()
                ,ExpenseDatabase.class
                ,"database")
                .allowMainThreadQueries()
                .build();
        ArrayList<Expense> myDataset = (ArrayList<Expense>)db.expenseDao().getAll();
        mAdapter = new MyAdapter(myDataset,db);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("沒資料庫的App");
        toolbar.setSubtitle("按旁邊可以新增項目");
        setSupportActionBar(toolbar);
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
                    Toast.makeText(MainActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                    mAdapter.add_new_task(editText.getText().toString());
                    mAdapter.db.expenseDao().insert(mAdapter.mData.get(mAdapter.mData.size() - 1));
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

package com.example.oaob.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.nio.file.FileSystemNotFoundException;

public class MyService extends Service
{
    public class LocalBinder extends Binder
    {
        public MyService getService()
        {
            return  MyService.this;
        }
    }
    public LocalBinder localBinder;

    public MediaPlayer mediaPlayer;
    public String title;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        localBinder = new LocalBinder();
        return localBinder;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
        }
        Bundle bundle = intent.getExtras();
        int resource = bundle.getInt("resource");
        title = bundle.getString("title");

        mediaPlayer = MediaPlayer.create(this,resource);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }
}

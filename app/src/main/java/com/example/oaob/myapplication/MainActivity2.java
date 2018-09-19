package com.example.oaob.myapplication;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity2 extends AppCompatActivity
{
    TextView locate_time;
    TextView end_time;
    TextView title_name;
    Button play_button;
    Button return_button;
    SeekBar time_bar;
    SeekBar volume_bar;
    int totaltime;
    ServiceConnection mSc;
    MyService myService;
    Timer timer = new Timer(true);


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        locate_time = (TextView)findViewById(R.id.locate_time);
        end_time = (TextView)findViewById(R.id.end_time);
        play_button = (Button)findViewById(R.id.play_button);
        time_bar = (SeekBar)findViewById(R.id.time_bar);
        volume_bar = (SeekBar)findViewById(R.id.volume_bar);
        return_button = (Button)findViewById(R.id.button2);
        title_name = (TextView)findViewById(R.id.textView);

        mSc = new ServiceConnection()
        {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                final MyService ss = ((MyService.LocalBinder) service).getService();
                myService = ss;
                if (!ss.mediaPlayer.isPlaying())
                {
                    play_button.setText("Play");
                }
                else
                {
                    play_button.setText("Stop");
                }
                totaltime = ss.mediaPlayer.getDuration();
                time_bar.setMax(totaltime);
                time_bar.setProgress(ss.mediaPlayer.getCurrentPosition());
                title_name.setText(ss.title);
                locate_time.setText(createTimeString(ss.mediaPlayer.getCurrentPosition()));
                end_time.setText(createTimeString(totaltime - ss.mediaPlayer.getCurrentPosition()));

                play_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ss.mediaPlayer.isPlaying()) {
                            ss.mediaPlayer.pause();
                            play_button.setText("Play");
                        } else {
                            ss.mediaPlayer.start();
                            play_button.setText("Stop");
                        }
                        System.out.println("play_button_clicked");
                    }
                });

                time_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int locate, boolean b) {
                        if (b) {
                            ss.mediaPlayer.seekTo(locate);
                            time_bar.setProgress(locate);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                volume_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int locate, boolean b) {
                        float volumeNum = locate / 100f;
                        ss.mediaPlayer.setVolume(volumeNum, volumeNum);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName)
            {
               unbindService(mSc);
            }
        };

        return_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Intent serviceIntent = new Intent(this, MyService.class);
        this.bindService(serviceIntent, mSc, Context.BIND_AUTO_CREATE);

        @SuppressLint("HandlerLeak") final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                int msgId = msg.what;
                locate_time.setText(createTimeString(msgId));
                end_time.setText(createTimeString(totaltime - msgId));
            }
        };

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = myService.mediaPlayer.getCurrentPosition();
                handler.sendMessage(message);
            }
        }, 1000, 1000/* 表示1000毫秒之後，每隔1000毫秒執行一次 */);
    }

    public String createTimeString(int time)
    {
        String s = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;
        s += min + ":";
        s = sec < 10 ? s + "0" + sec : s + sec;
        return s;
    }


    @Override
    public void onDestroy()
    {
        timer.cancel();
        super.onDestroy();
    }
}

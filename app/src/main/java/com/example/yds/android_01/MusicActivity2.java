package com.example.yds.android_01;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.media.midi.MidiDevice;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.ClosedSelectorException;

import static java.lang.Thread.sleep;

/**
 * Created by YDS on 2017/10/3.
 */

public class MusicActivity2 extends AppCompatActivity {
    public Button bstart;
    public Button bpause;
    public Button bcontiue;
    Intent intetmusicse;
    public ProgressBar probar;
    public TextView textpro;
    public MyHandler handler;
   // public Mythread thread;
    public ServiceMusic muService;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            muService = ((ServiceMusic.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music3);
        bstart = (Button) findViewById(R.id.start);
        bpause = (Button) findViewById(R.id.pause);
        bcontiue = (Button) findViewById(R.id.contiue);
        probar = (ProgressBar) findViewById(R.id.proBar);
        textpro = (TextView) findViewById(R.id.textView);
        bpause.setEnabled(false);
        bcontiue.setEnabled(false);



        intetmusicse = new Intent(this, ServiceMusic.class);//声明此Intent是用来启动服务的
        //startService(intetmusicse);

        bindService(intetmusicse, mConnection, BIND_AUTO_CREATE);

        if (muService == null)
            Toast.makeText(this, "muservice===null null", Toast.LENGTH_LONG).show();
        handler = new MyHandler();


        bstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                muService.meplayer.start();
                int j = muService.meplayer.getDuration();
                int i = muService.meplayer.getCurrentPosition();
                textpro.setText(String.valueOf(100 * (float) i / j) + "%");
                bstart.setEnabled(false);
                bpause.setEnabled(true);
            }
        });
        bpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muService.meplayer.pause();

                int j = muService.meplayer.getDuration();
                int i = muService.meplayer.getCurrentPosition();
                textpro.setText(String.valueOf((int) 100 * (float) i / j) + "%");
                bcontiue.setEnabled(true);
                bpause.setEnabled(false);
            }
        });
        bcontiue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muService.meplayer.start();
                int j = muService.meplayer.getDuration();
                textpro.setText(String.valueOf(j));
                bpause.setEnabled(true);
                bcontiue.setEnabled(false);
            }
        });

    }

    public class MyHandler extends Handler {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle prog = msg.getData();
            Toast.makeText(getApplicationContext(), "jie sou jie sou", Toast.LENGTH_LONG).show();
            int progress = prog.getInt("jindu");
            probar.setProgress(progress);
            textpro.setText(progress + "%");
        }
    }


    public class Mythread extends Thread {
        ServiceMusic smu;

        public Mythread(ServiceMusic sm) {
            smu = sm;
        }

        public void run() {
            float i =0;
            smu.meplayer.getCurrentPosition();////////////////////////////???????????报错提示这行有问题。？？？？
            int j = smu.meplayer.getDuration();
            for(;i<j; )
            {
                i = smu.meplayer.getCurrentPosition();
                j = smu.meplayer.getDuration();
                Message msg = new Message();
                Bundle b = new Bundle();
                float k=100*( i/ j);
                b.putInt("jindu", (int)k );
                msg.setData(b);
                handler.sendMessage(msg);
                Toast.makeText(getApplicationContext(),"fa song fa song",Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}


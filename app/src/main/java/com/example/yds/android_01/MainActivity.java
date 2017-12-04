package com.example.yds.android_01;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.BatchUpdateException;

import last.Main_student;

/*
   项目指导：已经实现控制音乐的播放，暂停，恢复，但是多线程中 引用 mdiaplayer 报错，说返回的是空的对象，多线程无法运行。
 */

public class MainActivity extends AppCompatActivity {

    Button but_2;
    Button but_3;
    Button but_4;
    Button but_5;
    Button but_6;
    Button but_7;
    Button but_end;
    Intent intet,intet2;


    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but_2=(Button)findViewById(R.id.b2);
        context=this.getApplicationContext();
        button2 b2=new button2(but_2,getWindow());//getwindow () 获取窗口 ，传给外部类，实现跳转界面。
        but_2.setOnClickListener(b2);

        but_3=(Button)findViewById(R.id.button3);
        but_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intet=new Intent(getcontext(),MusicActivity2.class);//指定要跳转Activity的目标
                startActivity(intet);

            }
        });

        but_4=(Button)findViewById(R.id.button4);
        but_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intet2=new Intent(getcontext(),receiverActivity4.class);//指定要跳转Activity的目标
                startActivity(intet2);
            }
        });

        but_5=(Button)findViewById(R.id.button5);
        but_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intet2=new Intent(getcontext(),touchpicture5.class);
                startActivity(intet2);
            }
        });

        but_6=(Button)findViewById(R.id.button6);
        but_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intet2=new Intent(getcontext(),sensor_6.class);
                startActivity(intet2);
            }
        });

        but_7=(Button)findViewById(R.id.button7);
        but_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intet2 = new Intent(getcontext(), Map_7.class);
                    startActivity(intet2);
                }
            });
        but_end=(Button)findViewById(R.id.button8);
        but_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intet2=new Intent(getcontext(), Main_student.class);
                startActivity(intet2);
            }
        });

    }

     static public Context getcontext()
    {
        return context;
    }
}

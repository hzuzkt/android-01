package com.example.yds.android_01;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by YDS on 2017/10/4.
 */

public class ServiceMusic extends Service {
    public  MediaPlayer meplayer;
    @Nullable
    private final IBinder mubinder=new LocalBinder();
    public class LocalBinder extends Binder
    {
        ServiceMusic getService(){return ServiceMusic.this; }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mubinder;
    }

    public void onCreate()
    {
        super.onCreate();
        if(meplayer==null)
        {
            meplayer=MediaPlayer.create(this,R.raw.huanyingbaohe);//使用指定的的音乐初始化播放器
            meplayer.setLooping(true);//循环播放

        }
    }

    public void onStart(Intent intent,int startId)
    {
        super.onStart(intent,startId);

    }

    public  void onDestroy()
    {
        super.onDestroy();
    }
}

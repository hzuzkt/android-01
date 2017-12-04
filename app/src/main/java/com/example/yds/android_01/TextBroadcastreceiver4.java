package com.example.yds.android_01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

/**
 * Created by YDS on 2017/10/14.
 */

public class TextBroadcastreceiver4 extends BroadcastReceiver {
    public static  IChangeTV iChangeTV=null;
    String textsub;

    @Override
    public void onReceive(Context context, Intent intent) {
            textsub = intent.getStringExtra("text");
            double []loca=intent.getDoubleArrayExtra("location"); //从Intent中获取Location类型的数据
            iChangeTV.change(textsub,loca);
            Toast.makeText(context,"收到坐标",Toast.LENGTH_SHORT).show();
    }

    interface  IChangeTV
    {
        void change(String textsub, double [] loca);
    }

    public void OnBroadcast(IChangeTV iChange)
    {
        this.iChangeTV=iChange;
    }
}

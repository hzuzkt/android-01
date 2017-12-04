package com.example.yds.android_01;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by YDS on 2017/10/14.
 */

public class receiverActivity4 extends AppCompatActivity {
    static TextView tvshow;
    Intent intentser;
    Service ServiceSetBroad;
    TextBroadcastreceiver4 TBR4;
//    private ServiceConnection mConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            ServiceSetBroad = ((ServiceSetBroadcast.LocalBinder) service).getService();
//            Toast.makeText(getApplicationContext(),"connection successful!!",Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            Toast.makeText(getApplicationContext(),"connection failed!!",Toast.LENGTH_LONG).show();
//
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_3_recevier);
        tvshow=(TextView)findViewById(R.id.textView);
        intentser=new Intent(this,ServiceSetBroadcast.class);//启动服务

        startService(intentser);
       // bindService(intentser, mConnection,BIND_AUTO_CREATE);  //绑定服务，绑定启动

//        if(ServiceSetBroad==null)
//            Toast.makeText(this,"null--null",Toast.LENGTH_LONG).show();

        tvshow.setText("hhhhhh");
        TBR4=new TextBroadcastreceiver4();
        try {
            TBR4.OnBroadcast(new TextBroadcastreceiver4.IChangeTV() {
                @Override
                public void change(String textsub, double []local) {
                    tvshow.setText(textsub);
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(this,"kkkkkkkkkkk",Toast.LENGTH_LONG).show();
        }
    }
}

package com.example.yds.android_01;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;

import java.util.List;


/**
 * Created by YDS on 2017/10/14.
 */

public class ServiceSetBroadcast extends Service {

    String text = "收到广播";
    Intent intentstr;
    LocationManager locationManager;
    double[] loca = new double[]{0, 0};
    LocationListener mlistener;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void onCreate() {
        super.onCreate();
        intentstr = new Intent("b_4Bsendroadcaset");//Action 可以随意取 ，不一定是要使用系统定义好的。
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = LocationManager.NETWORK_PROVIDER;

        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "ERROR:No Permission!", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(getApplicationContext(),"使用:"+provider+"定位",Toast.LENGTH_SHORT).show();
        ///////////////////////////////////////////Permission
        mlistener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(getApplicationContext(),provider+":开启",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getApplicationContext(),provider+":关闭",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLocationChanged(Location location) {
                double Longitude = location.getLongitude();
                double Latitude = location.getLatitude();

                //由于坐标格式不同因此需要转换
                LatLng OraLatLng = new LatLng(Latitude,Longitude);
                CoordinateConverter converter  = new CoordinateConverter();
                converter.from(CoordinateConverter.CoordType.GPS);
                // sourceLatLng待转换坐标
                converter.coord(OraLatLng);
                LatLng desLatLng = converter.convert();
                loca[0]=desLatLng.latitude;
                loca[1]=desLatLng.longitude;
                intentstr.putExtra("location",loca);

                sendBroadcast(intentstr);

            }
        };
        locationManager.requestLocationUpdates(provider, 3000, 1,mlistener);
    }

    public int onStartCommand(Intent intent, int flags, int startid) {
        super.onStartCommand(intent, flags, startid);
        //每隔三秒 ,更新一次

        new Thread()////////////////////////////////////////////////////
        {
            public void run() {
                super.run();

                for (int i = 0; i >= 0; i++) {
                    try {

                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    intentstr.putExtra("text",text.substring(i%4,i%4+1));
                }
            }
       }.start();

        return START_REDELIVER_INTENT;
    }
    public void onDestroy()
    {
        super.onDestroy();
        locationManager.removeUpdates(mlistener);

    }



}

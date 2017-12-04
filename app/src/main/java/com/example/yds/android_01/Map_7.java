package com.example.yds.android_01;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.camera2.params.BlackLevelPattern;
import android.icu.text.DecimalFormat;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;

import java.util.Map;

/**
 * Created by YDS on 2017/11/1.
 */

public class Map_7 extends AppCompatActivity {


    MapView mMapView = null;
    private float zoomLevel = 8;//表示当前地图缩放级别
    Intent InSemap;
    TextBroadcastreceiver4 getboadmap;
    private BaiduMap mBaiduMap;
    int ifFrist = 0;
    static int i=0;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.map_7);

        mMapView = (MapView) findViewById(R.id.bmapView);
        //获取百度地图
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 设置可改变地图位置
        mBaiduMap.setMyLocationEnabled(true);
        //开启交通图
        //mBaiduMap.setTrafficEnabled(true);

        //-----开启服务
        InSemap = new Intent(this, ServiceSetBroadcast.class); //创建Intent来作为服务启动的中间量
        startService(InSemap);                                       //启动服务

        getboadmap = new TextBroadcastreceiver4();
        getboadmap.OnBroadcast(new TextBroadcastreceiver4.IChangeTV() {
            @Override
            public void change(String textsub,double[] loca) {
                navigateTo(loca);
            }
        });

    }

    // 按照经纬度确定地图位置
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void navigateTo(double [] local) {
        mBaiduMap.clear();
        if (ifFrist<1) {
            LatLng ll = new LatLng(local[0],
                    local[1]);
            //定义地图状态
            MapStatus mMapStatus = new MapStatus
                    .Builder()
                    .target(ll)
                    .zoom(8)
                    .build();
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            mBaiduMap.setMapStatus(mMapStatusUpdate);
            ifFrist += 1;
        }
        // 显示个人位置图标
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(local[0]);
        builder.longitude(local[1]);
        MyLocationData data = builder.build();
        mBaiduMap.setMyLocationData(data);
        showmaker(local);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected  void showmaker(double[] local)
    {
        ///////////////////
//        MarkerOptions markerOptions = new MarkerOptions();
//        //这里很简单就加了一个TextView，根据需求可以加载复杂的View
//
//        LatLng point = new LatLng(local[0],local[1]);
//        TextView textView = new TextView(getApplicationContext());
//        textView.setTextColor(android.graphics.Color.RED);
//
//        textView.setText("("+local[0]+";"+local[1]+")");
//             //通过View获取BitmapDescriptor对象
//        BitmapDescriptor markerIcon = BitmapDescriptorFactory
//                .fromView(textView);
//        markerOptions.position(point)
//                .icon(markerIcon).title("123").perspective(true);
//        //添加到地图上
//        mBaiduMap.addOverlay(markerOptions);
        View view = LayoutInflater.from(Map_7.this).inflate(R.layout.laypicture, null);

        TextView tv_num_price=(TextView) view.findViewById(R.id.tv_num_price);

        tv_num_price.setText("经度:"+local[0]+"维度:"+local[1]);
        BitmapDescriptor free_view = BitmapDescriptorFactory.fromView(view);
        MarkerOptions ooA = null;
        LatLng lat=new LatLng(local[0],local[1]);
        ooA=new MarkerOptions().position(lat).icon(free_view).zIndex(9).draggable(false);
        mBaiduMap.addOverlay(ooA);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止定位
        mBaiduMap.setMyLocationEnabled(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(InSemap);
        mMapView.onDestroy();
    }
}
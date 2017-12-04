package com.example.yds.android_01;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;



public class sensor_6 extends AppCompatActivity implements SensorEventListener {

    public TextView tv_gravity,tv_light;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_6);

        tv_gravity=(TextView)findViewById(R.id.textGravity);
        tv_light=(TextView)findViewById(R.id.textLight);

        mSensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);//获取传感器管理对象
    }

    protected  void onStart()//为传感器
    {
        super.onStart();
        //为重力传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),SensorManager.SENSOR_DELAY_GAME);
        //为光线传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    protected void onStop() {
        super.onStop();
        // 取消监听
        mSensorManager.unregisterListener(this);
    }


    @Override    //当传感器的值改变的时候回调该方法
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;
        // 获取传感器类型
        int type = sensorEvent.sensor.getType();
        String str_show="";
        switch (type)
        {
            case Sensor.TYPE_GRAVITY:
                float x=values[0],y=values[1],z=values[2];
                //str_show="X="+x+"\nY="+y+"\nZ="+z;
                if(Math.abs(x)>8) {
                    if (x > 8)
                        str_show += "\n右侧朝上";
                    else
                        str_show += "\n左测朝上";
                }
                else if(Math.abs(y)>8) {
                    if (y > 8)
                        str_show += "\n竖直向上";
                    else
                        str_show += "\n底部向上";
                }
                else if(Math.abs(z)>8)
                {
                    if(z>8)
                        str_show+="\n正面向上";
                    else
                        str_show+="\n背面向上";
                }
                tv_gravity.setText(str_show);
                break;
            case Sensor.TYPE_LIGHT:
                tv_light.setText("光线值："+values[0]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

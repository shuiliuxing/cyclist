package com.huabing.cyclist.map;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by 30781 on 2017/4/5.
 */

public class MyOrientationListener implements SensorEventListener {
    private SensorManager mSensorManager;
    private Context mContext;
    private Sensor mSensor;
    private float lastX;

    public MyOrientationListener(Context context)
    {
        this.mContext=context;
    }

    //开始监听
    public void start()
    {
        mSensorManager=(SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager!=null)
        {   //获得方向传感器
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        if(mSensor!=null)
        {
            mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_UI);

        }
    }

    //结束监听
    public void stop()
    {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0,int arg1)
    {

    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if(event.sensor.getType()==Sensor.TYPE_ORIENTATION)
        {
            float x=event.values[SensorManager.DATA_X];
            //float y=event.values[SensorManager.DATA_Y];
            //float z=event.values[SensorManager.DATA_Z];
            if(Math.abs(x-lastX)>1.0)
            {
                if(mOnOrientationListener!=null)
                {
                    mOnOrientationListener.onOrientationChanged(x);

                }

            }
            lastX=x;

        }

    }


    private OnOrientationListener mOnOrientationListener;
    public void setOnOrientationListener(OnOrientationListener mOnOrientationListener)
    {

    }

    public interface OnOrientationListener
    {
        void onOrientationChanged(float x);
    }
}

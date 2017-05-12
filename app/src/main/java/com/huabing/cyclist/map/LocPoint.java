package com.huabing.cyclist.map;

import java.io.Serializable;

/**
 * Created by 30781 on 2017/4/7.
 */

public class LocPoint implements Serializable {
    private double latitude;
    private double longtitude;

    public void setLatitude(double latitude)
    {
        this.latitude=latitude;
    }
    public void setLongtitude(double longtitude)
    {
        this.longtitude=longtitude;
    }

    public double getLatitude()
    {
        return latitude;
    }
    public double getLongtitude()
    {
        return longtitude;
    }
}

package com.huabing.cyclist.database;

import org.litepal.crud.DataSupport;

/**
 * Created by 30781 on 2017/3/26.
 */

public class County extends DataSupport {
    private int id;
    private String countyName;
    private String weatherId;
    private int cityId;

    public void setId(int id)
    {
        this.id=id;
    }
    public int getId()
    {
        return id;
    }
    public void setCountyName(String countyName)
    {
        this.countyName=countyName;
    }
    public String getCountyName()
    {
        return countyName;
    }
    public void setWeatherId(String weatherId)
    {
        this.weatherId=weatherId;
    }
    public String getWeatherId()
    {
        return weatherId;
    }
    public void setCityId(int cityId)
    {
        this.cityId=cityId;
    }
    public int getCityId()
    {
        return cityId;
    }

}

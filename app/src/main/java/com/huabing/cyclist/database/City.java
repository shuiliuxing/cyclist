package com.huabing.cyclist.database;

import org.litepal.crud.DataSupport;

/**
 * Created by 30781 on 2017/3/26.
 */

public class City extends DataSupport {
    private int id;
    private String cityName;
    private int cityId;
    private int provinceId;

    public void setId(int id)
    {
        this.id=id;
    }
    public int getId()
    {
        return id;
    }
    public void setCityName(String cityName)
    {
        this.cityName=cityName;
    }
    public String getCityName()
    {
        return cityName;
    }
    public void setCityId(int cityId)
    {
        this.cityId=cityId;
    }
    public int getCityId()
    {
        return cityId;
    }
    public void setProvinceId(int provinceId)
    {
        this.provinceId=provinceId;
    }
    public int getProvinceId()
    {
        return provinceId;
    }
}

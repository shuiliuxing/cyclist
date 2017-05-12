package com.huabing.cyclist.database;

import org.litepal.crud.DataSupport;

/**
 * Created by 30781 on 2017/3/25.
 */

public class Province extends DataSupport {
    private int id;
    private String provinceName;
    private int provinceId;

    public void setId(int id)
    {
        this.id=id;
    }
    public int getId()
    {
        return id;
    }
    public void setProvinceName(String provinceName)
    {
        this.provinceName=provinceName;
    }
    public String getProvinceName()
    {
        return provinceName;
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

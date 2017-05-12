package com.huabing.cyclist.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.huabing.cyclist.database.Province;
import com.huabing.cyclist.database.City;
import com.huabing.cyclist.database.County;
import com.huabing.cyclist.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 30781 on 2017/3/26.
 */

public class Utility {

    //解析省级数据，保存所有省
    public static boolean handleProvinceResponse(String response)
    {
        if(!TextUtils.isEmpty(response))
        {
            try
            {
                JSONArray provinceArray=new JSONArray(response);
                for(int i=0;i< provinceArray.length();i++)
                {
                    JSONObject provinceObject= provinceArray.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceId(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    //解析市级数据，保存所有市
    public static boolean handleCityResponse(String response,int provinceId)
    {
        if(!TextUtils.isEmpty(response))
        {
            try
            {
                JSONArray cityArray=new JSONArray(response);
                for(int i=0;i<cityArray.length();i++)
                {
                    JSONObject cityObject=cityArray.getJSONObject(i);
                    City city=new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityId(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    //解析县级数据,保存所有县
    public static boolean handleCountyResponse(String response,int cityId)
    {
        if(!TextUtils.isEmpty(response))
        {
            try
            {
                JSONArray countyArray=new JSONArray(response);
                for(int i=0;i<countyArray.length();i++)
                {
                    JSONObject countyObject=countyArray.getJSONObject(i);
                    County county=new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    //解析天气数据，返回Weather对象
    public static Weather handleWeatherResponse(String response)
    {
        try
        {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather5");
            String content=jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(content,Weather.class);
           /* Gson gson=new Gson();
            Weather weather=gson.fromJson(content,Weather.class);
            return weather;*/
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}

package com.huabing.cyclist.gson;

import com.huabing.cyclist.gson.weathergson.Aqi;
import com.huabing.cyclist.gson.weathergson.Basic;
import com.huabing.cyclist.gson.weathergson.DailyForecast;
import com.huabing.cyclist.gson.weathergson.HourlyForecast;
import com.huabing.cyclist.gson.weathergson.Now;
import com.huabing.cyclist.gson.weathergson.Suggestion;

import java.util.List;

/**
 * Created by 30781 on 2017/3/27.
 */

public class Weather {
    public Aqi aqi;
    public Basic basic;
    public List<DailyForecast> daily_forecast;
    public List<HourlyForecast> hourly_forecast;
    public Now now;
    public String status;
    public Suggestion suggestion;
}

package com.huabing.cyclist.gson.weathergson;

/**
 * Created by 30781 on 2017/3/27.
 */

public class Basic {
    public String city;
    public String cnty;
    public String id;
    public String lat;
    public String lon;

    public Update update;
    public class Update
    {
        public String loc;
        public String utc;
    }
}

package com.huabing.cyclist.gson.weathergson;

/**
 * Created by 30781 on 2017/3/27.
 */

public class Now {
    public Cond cond;
    public class Cond
    {
        public String code;
        public String txt;
    }
    public String fl;
    public String hum;
    public String pcpn;
    public String pres;
    public String tmp;
    public String vis;
    public Wind wind;
    public class Wind
    {
        public String deg;
        public String dir;
        public String sc;
        public String spd;
    }
}

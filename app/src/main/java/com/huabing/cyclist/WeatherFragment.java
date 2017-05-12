package com.huabing.cyclist;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.huabing.cyclist.gson.weathergson.DailyForecast;
import com.huabing.cyclist.gson.Weather;
import com.huabing.cyclist.util.HttpUtil;
import com.huabing.cyclist.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 30781 on 2017/3/26.
 */

public class WeatherFragment extends Fragment{
    private ImageView ivBackground;
    private SwipeRefreshLayout srfRefresh;
    private TextView tvOpen;
    private TextView tvCounty;
    private TextView tvDegree;
    private TextView tvInfo;
    private TextView tvAqi;
    private TextView tvPm;
    private TextView tvComfort;
    private TextView tvWear;
    private TextView tvSport;
    private TextView tvWash;
    private LinearLayout llForecast;

    private LocalReceiver receiver;
    private LocalBroadcastManager manager;
    private IntentFilter filter;

    public LocationClient client;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        //初始化LocationClient
        client=new LocationClient(getActivity());
        client.registerLocationListener(new MyLocationListener());
        View view=inflater.inflate(R.layout.frag_weather,container,false);
        //下拉刷新
        srfRefresh=(SwipeRefreshLayout)view.findViewById(R.id.srl_refresh);
        srfRefresh.setColorSchemeResources(R.color.colorAccent);
        srfRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFragment();
            }
        });
        tvOpen=(TextView)view.findViewById(R.id.tv_open);
        tvOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ChooseActivity.class);
                startActivity(intent);
            }
        });
        //天气控件
        tvCounty=(TextView)view.findViewById(R.id.tv_county);
        tvDegree=(TextView)view.findViewById(R.id.tv_degree);
        tvInfo=(TextView)view.findViewById(R.id.tv_info);
        tvAqi=(TextView)view.findViewById(R.id.tv_aqi);
        tvPm=(TextView)view.findViewById(R.id.tv_pm);
        tvComfort=(TextView)view.findViewById(R.id.tv_comfort);
        tvWear=(TextView)view.findViewById(R.id.tv_wear);
        tvSport=(TextView)view.findViewById(R.id.tv_sport);
        tvWash=(TextView)view.findViewById(R.id.tv_wash);
        llForecast=(LinearLayout)view.findViewById(R.id.ll_forecast);
        //注册本地广播
        manager=LocalBroadcastManager.getInstance(getActivity());
        filter=new IntentFilter();
        filter.addAction("com.huabing.LOCAL_BROADCAST");
        receiver=new LocalReceiver();
        manager.registerReceiver(receiver,filter);
        //必应背景图片
        ivBackground=(ImageView)view.findViewById(R.id.iv_background);
        //ivBackground.setImageResource(R.drawable.main_backthree);
        Glide.with(this).load(R.drawable.main_backtwo).into(ivBackground);
        ConnectivityManager connectManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectManager.getActiveNetworkInfo();
        //Glide.with(getActivity()).load(R.drawable.main_background).into(ivBackground);
        if(networkInfo!=null && networkInfo.isAvailable())
        {
            //String picAddress="http://cn.bing.com/az/hprichbg/rb/BellasArtes_ZH-CN9573521567_1920x1080.jpg";
            //Glide.with(getActivity()).load(picAddress).into(ivBackground);
            //刚打开时加载当前地区天气数据
            loadCountyData();
        }
        else
            Toast.makeText(getActivity(),"当前没有网络！",Toast.LENGTH_SHORT).show();

        return view;
    }

    public static WeatherFragment newInstance(String title)
    {
        //Bundle args=new Bundle();
        //args.putString("weathertitle",title);
        WeatherFragment fragment=new WeatherFragment();
        //fragment.setArguments(args);
        return fragment;
    }

    /*与Activity交互时
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle=getArguments();
        Toolbar toolbar=(Toolbar)getActivity().findViewById(R.id.tb_toolbar);
        toolbar.setTitle(bundle.getString("weathertitle"));
    }*/


    //初始化天气
    private void loadCountyData()
    {
        srfRefresh.setRefreshing(true);
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String weatherId=preferences.getString("weather_id",null);
        //若有上一次的地区天气ID
        if(weatherId!=null)
        {
            String address="https://free-api.heweather.com/v5/weather?city="+weatherId+"&key=4287b74d5e45499aad0976b91aca3b1d";
            HttpUtil.sendOkHttpRequest(address, new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException
                {
                    final String weatherJson=response.body().string();
                    final Weather weather=Utility.handleWeatherResponse(weatherJson);
                    //用SharedPreferences存储天气json数据
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                    editor.putString("weather_json", weatherJson);
                    editor.putString("weather_id",weatherId);
                    editor.apply();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshFragment();
                        }
                    });
                }
                @Override
                public void onFailure(Call call, IOException e)
                {
                    //Toast.makeText(getActivity(),"获取天气数据失败！",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            //权限管理,添加所有权限
            List<String> permissionList=new ArrayList<>();
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
            {
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED)
            {
                permissionList.add(Manifest.permission.READ_PHONE_STATE);
            }
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
            {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if(!permissionList.isEmpty())
            {
                //将权限List转为String[]
                String[] permissions=permissionList.toArray(new String[permissionList.size()]);
                ActivityCompat.requestPermissions(getActivity(),permissions,1);
            }
            else
            {
                //权限全部获得,开始定位
                LocationClientOption option=new LocationClientOption();
                option.setIsNeedAddress(true);
                client.setLocOption(option);
                client.start();
            }
        }
        srfRefresh.setRefreshing(false);
    }


    //广播类
    class LocalReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            refreshFragment();
        }
    }
    //地图位置监听类
    public class MyLocationListener implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation location)
        {
            //获取当前城市
            String cityId=location.getCity();
            String address="https://free-api.heweather.com/v5/weather?city="+cityId+"&key=4287b74d5e45499aad0976b91aca3b1d";
            HttpUtil.sendOkHttpRequest(address, new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //获取到当前城市的json数据
                    final String weatherJson=response.body().string();
                    //用Weather对象来是否真的获取到数据
                    final Weather weather=Utility.handleWeatherResponse(weatherJson);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(weather!=null)
                            {
                                //保存第一次的天气数据
                                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                                editor.putString("weather_json", weatherJson);
                                editor.putString("weather_id",weather.basic.id);
                                editor.apply();
                                showAll(weather);
                            }
                            else
                                Toast.makeText(getActivity(),"获取天气数据失败！",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                @Override
                public void onFailure(Call call, IOException e) {

                }
            });
        }
        @Override
        public void onConnectHotSpotMessage(String s, int i)
        {
        }
    }

    //所有控件显示天气数据
    private void showAll(Weather weather)
    {
        //天气数据
        String county=weather.basic.city;//地区
        String updateTime=weather.basic.update.loc;//更新时间
        String degree=weather.now.tmp+"℃";//温度
        String info=weather.now.cond.txt;//阴晴
        if(weather.aqi!=null)
        {
            String aqi = weather.aqi.city.aqi;//AQI指数
            String pm = weather.aqi.city.pm25;//PM2.5
            tvAqi.setText(aqi);
            tvPm.setText(pm);
        }
        String comfort=weather.suggestion.comf.txt;//舒适度
        String wear=weather.suggestion.drsg.txt;//穿衣推荐
        String sport=weather.suggestion.sport.txt;//运动建议
        String wash=weather.suggestion.cw.txt;//洗车指数*/
        //数据显示
        tvCounty.setText(county);
        tvDegree.setText(degree);
        tvInfo.setText(info);
        tvComfort.setText("舒适度："+comfort);
        tvWear.setText("穿衣推荐："+wear);
        tvSport.setText("运动建议："+sport);
        tvWash.setText("洗车指数："+wash);
        //未来几天
        llForecast.removeAllViews();
        for(DailyForecast forecast:weather.daily_forecast)
        {
            View view=LayoutInflater.from(getActivity()).inflate(R.layout.forecast_item,llForecast,false);
            TextView tvDate=(TextView)view.findViewById(R.id.tv_date);
            TextView tvTxt=(TextView)view.findViewById(R.id.tv_txt);
            TextView tvMax=(TextView)view.findViewById(R.id.tv_max);
            TextView tvMin=(TextView)view.findViewById(R.id.tv_min);
            tvDate.setText(forecast.date);
            tvTxt.setText(forecast.cond.txt_d);
            tvMax.setText(forecast.tmp.max);
            tvMin.setText(forecast.tmp.min);
            llForecast.addView(view);
        }

    }

    //重新刷新数据
    private void refreshFragment()
    {
        srfRefresh.setRefreshing(true);
        //从SharedPreference获取天气json数据，解析出weather对象
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String weatherJson=pref.getString("weather_json",null);
        if(weatherJson!=null) {
            Weather weather = Utility.handleWeatherResponse(weatherJson);
            showAll(weather);
        }
        srfRefresh.setRefreshing(false);
    }

    //权限授权
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults)
    {
        switch(requestCode)
        {
            case 1:
                if(grantResults.length>0)
                {
                    //如果有一个权限没同意，则退出Activity
                    for(int result:grantResults)
                    {
                        if(result!=PackageManager.PERMISSION_GRANTED)
                        {
                            Toast.makeText(getActivity(),"必须同意所有权限才能使用本程序！",Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                            return;
                        }
                    }
                    //权限全部获得,开始定位
                    LocationClientOption option=new LocationClientOption();
                    option.setIsNeedAddress(true);
                    client.setLocOption(option);
                    client.start();
                }
                else
                {
                    Toast.makeText(getActivity(),"发生未知错误",Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                break;
            default:
                break;
        }
    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();
        manager.unregisterReceiver(receiver);
        client.stop();
    }


}

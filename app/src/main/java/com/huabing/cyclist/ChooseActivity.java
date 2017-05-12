package com.huabing.cyclist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huabing.cyclist.adapter.CityAdapter;
import com.huabing.cyclist.database.City;
import com.huabing.cyclist.database.County;
import com.huabing.cyclist.database.Province;
import com.huabing.cyclist.gson.Weather;
import com.huabing.cyclist.util.HttpUtil;
import com.huabing.cyclist.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseActivity extends Activity {

    //控件
    private ProgressDialog pgrDialog;
    private Button btnBack;
    private TextView tvChoose;
    private ListView lvChooose;
    private List<String> dataList;
    private ArrayAdapter<String> adapter;

    //省市县数据集
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;

    //选中的省或市
    private Province selectProvince;
    private City selectCity;

    //级别
    public static final int LEVEL_PROVINCE=0;
    public static final int LEVEL_CITY=1;
    public static final int LEVEL_COUNTY=2;
    //选中的级别
    private int currentLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        btnBack=(Button)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel==LEVEL_COUNTY)
                    queryCities();
                else if(currentLevel==LEVEL_CITY)
                    queryProvinces();
            }
        });
        tvChoose=(TextView)findViewById(R.id.tv_choose);
        lvChooose=(ListView)findViewById(R.id.lv_choose);

        dataList=new ArrayList<>();
        adapter=new CityAdapter(ChooseActivity.this,R.layout.choose_item,dataList);
        lvChooose.setAdapter(adapter);
        lvChooose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel==LEVEL_PROVINCE) {
                    selectProvince = provinceList.get(position);
                    queryCities();
                }
                else if(currentLevel==LEVEL_CITY)
                {
                    selectCity=cityList.get(position);
                    queryCounties();
                }
                else if(currentLevel==LEVEL_COUNTY)
                {
                    final String weatherId=countyList.get(position).getWeatherId();
                    String address="https://free-api.heweather.com/v5/weather?city="+weatherId+"&key=4287b74d5e45499aad0976b91aca3b1d";
                    //Toast.makeText(ChooseActivity.this,"id为！"+weatherId,Toast.LENGTH_SHORT).show();
                    HttpUtil.sendOkHttpRequest(address, new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException
                        {
                            final String weatherJson=response.body().string();
                            //Log.i("数据:",weatherJson);
                            //用Weather对象来是否真的获取到数据
                            final Weather weather=Utility.handleWeatherResponse(weatherJson);
                            //用SharedPreferences存储天气json数据
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run() {
                                    if(weather!=null)
                                    {
                                        //Toast.makeText(ChooseActivity.this,"获取天气成功！",Toast.LENGTH_SHORT).show();
                                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ChooseActivity.this).edit();
                                        editor.putString("weather_json", weatherJson);
                                        editor.putString("weather_id",weatherId);
                                        editor.apply();
                                        //向WeatherFragment发送刷新数据广播
                                        LocalBroadcastManager manager=LocalBroadcastManager.getInstance(ChooseActivity.this);
                                        Intent intent=new Intent("com.huabing.LOCAL_BROADCAST");
                                        manager.sendBroadcast(intent);
                                        //Toast.makeText(ChooseActivity.this,"广播已发送！",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    else
                                        Toast.makeText(ChooseActivity.this,"获取天气数据失败1！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onFailure(Call call, IOException e)
                        {
                            Toast.makeText(ChooseActivity.this,"获取天气数据失败2！",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        //ListView显示初始数据
        queryProvinces();
    }

    //从数据库查询所有省
    private void queryProvinces()
    {
        tvChoose.setText("中国");
        btnBack.setVisibility(View.GONE);

        provinceList=DataSupport.findAll(Province.class);
        if(provinceList.size()>0)
        {
            //先清空
            dataList.clear();
            //添加所有省份数据并刷新
            for(Province province:provinceList)
                dataList.add(province.getProvinceName());
            adapter.notifyDataSetChanged();
            //默认选中第一行
            lvChooose.setSelection(0);
            //当前选中级别
            currentLevel=LEVEL_PROVINCE;
        }
        else
        {
            //从服务器查询省数据
            String address="http://guolin.tech/api/china";
            queryFromServer(address,"province");
        }
    }

    //从数据库查询所有市
    private void queryCities()
    {
        tvChoose.setText(selectProvince.getProvinceName());
        btnBack.setVisibility(View.VISIBLE);

        cityList=DataSupport.where("provinceId=?",String.valueOf(selectProvince.getProvinceId())).find(City.class);
        if(cityList.size()>0)
        {
            dataList.clear();
            for(City city:cityList)
                dataList.add(city.getCityName());
            adapter.notifyDataSetChanged();
            lvChooose.setSelection(0);
            currentLevel=LEVEL_CITY;
        }
        else
        {
            int provinceId=selectProvince.getProvinceId();
            String address="http://guolin.tech/api/china/"+provinceId;
            //Toast.makeText(ChooseActivity.this,address,Toast.LENGTH_SHORT).show();
            queryFromServer(address,"city");
        }
    }

    //从数据库查询所有县
    private void queryCounties()
    {
        tvChoose.setText(selectCity.getCityName());
        btnBack.setVisibility(View.VISIBLE);

        countyList=DataSupport.where("cityId=?",String.valueOf(selectCity.getCityId())).find(County.class);
        if(countyList.size()>0)
        {
            dataList.clear();
            for(County county:countyList)
                dataList.add(county.getCountyName());
            adapter.notifyDataSetChanged();
            lvChooose.setSelection(0);
            currentLevel=LEVEL_COUNTY;
        }
        else
        {
            int provinceId=selectProvince.getProvinceId();
            int cityId=selectCity.getCityId();
            String address="http://guolin.tech/api/china/"+provinceId+"/"+cityId;
            queryFromServer(address,"county");
        }
    }


    //根据闯入地址和类型从服务器上查询省市县数据
    private void queryFromServer(String address,final String type)
    {
        //进度框
        if(pgrDialog==null)
        {
            pgrDialog=new ProgressDialog(ChooseActivity.this);
            pgrDialog.setMessage("正在加载...");
            pgrDialog.setCanceledOnTouchOutside(false);
        }
        pgrDialog.show();

        //查询数据
        HttpUtil.sendOkHttpRequest(address,new Callback()
        {
            //成功
            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                //返回的省或市或县json数据
                String jsonData=response.body().string();
                //Log.e("数据为",jsonData);
                boolean result=false;
                //由类型解析并保存数据到数据库
                if(type.equals("province"))
                    result= Utility.handleProvinceResponse(jsonData);
                else if(type.equals("city"))
                    result=Utility.handleCityResponse(jsonData,selectProvince.getProvinceId());
                else if(type.equals("county"))
                    result=Utility.handleCountyResponse(jsonData,selectCity.getCityId());
                //请求并保存数据成功，转回主线程
                if(result)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //关闭进度框
                            if(pgrDialog!=null)
                                pgrDialog.dismiss();
                            //ListView重新从数据库读取数据
                            if(type.equals("province"))
                                queryProvinces();
                            else if(type.equals("city"))
                                queryCities();
                            else if(type.equals("county"))
                                queryCounties();
                        }
                    });
                }

            }

            //失败
            @Override
            public void onFailure(Call call,IOException e)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(pgrDialog!=null)
                            pgrDialog.dismiss();
                        Toast.makeText(ChooseActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}

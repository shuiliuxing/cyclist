package com.huabing.cyclist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.huabing.cyclist.map.LocPoint;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener{
    //周边搜索控件
    private ImageView ivCate;
    private ImageView ivGrog;
    private ImageView ivBank;
    private ImageView ivBus;
    private ImageView ivScenery;
    private ImageView ivGas;
    private ImageView ivCollect;
    private ImageView ivMore;
    //位置搜索控件
    private Toolbar selectToolbar;
    private EditText etStart;
    private EditText etEnd;
    private Button btnSearch;

    private SuggestionSearch suggestionSearch;
    private PopupWindow popupWindow;
    private ListView lvSearch;
    private List<String> listInput;
    private ArrayAdapter adapter;
    //起点
    private String startAddress;
    private GeoCoder mSearchStart=null;
    //终点
    private String endAddress;
    private GeoCoder  mSearchEnd=null;
    private List<String> dataList;
    //起终点坐标
    private double startLat;
    private double startLong;
    private double endLat;
    private double endLong;
    private int count;
    //搜索类型
    private int searchType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //标题栏
        selectToolbar=(Toolbar)findViewById(R.id.tb_select_toolbar);
        selectToolbar.setTitle("路线选择");
        setSupportActionBar(selectToolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //周边搜索控件
        ivCate=(ImageView)findViewById(R.id.iv_cate);
        ivCate.setOnClickListener(this);
        ivGrog=(ImageView)findViewById(R.id.iv_grog);
        ivGrog.setOnClickListener(this);
        ivBank=(ImageView)findViewById(R.id.iv_bank);
        ivBank.setOnClickListener(this);
        ivBus=(ImageView)findViewById(R.id.iv_bus);
        ivBus.setOnClickListener(this);
        ivScenery=(ImageView)findViewById(R.id.iv_scenery);
        ivScenery.setOnClickListener(this);
        ivGas=(ImageView)findViewById(R.id.iv_gas);
        ivGas.setOnClickListener(this);
        ivCollect=(ImageView)findViewById(R.id.iv_collect);
        ivCollect.setOnClickListener(this);
        ivMore=(ImageView)findViewById(R.id.iv_more);
        ivMore.setOnClickListener(this);
        //位置输入
        etStart=(EditText)findViewById(R.id.et_start);
        etEnd=(EditText)findViewById(R.id.et_end);
        //默认起始位置
        startAddress=getIntent().getStringExtra("address");
        etStart.setText(startAddress);
        etStart.setSelection(etStart.getText().length());//光标移动到最后一行

        //GeoCoder获取地址坐标
        mSearchStart=GeoCoder.newInstance();
        mSearchStart.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                //结果为空
                if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR)
                {
                    Toast.makeText(SelectActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
                    return;
                }
                startLat=geoCodeResult.getLocation().latitude;
                startLong=geoCodeResult.getLocation().longitude;
                ++count;
                if(count==2)
                    openNextActivity("",2);
            }
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {}
        });
        mSearchEnd=GeoCoder.newInstance();
        mSearchEnd.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                //结果为空
                if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR)
                {
                    Toast.makeText(SelectActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
                    return;
                }
                endLat=geoCodeResult.getLocation().latitude;
                endLong=geoCodeResult.getLocation().longitude;
                ++count;
                if(count==2)
                    openNextActivity("",2);
            }
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {}
        });

        //模糊匹配列表
        dataList=new ArrayList<>();
        //搜索路线
        btnSearch=(Button)findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);

        //搜索的PopupWindow
        View view=getLayoutInflater().inflate(R.layout.select_popwindow,null);
        lvSearch=(ListView)view.findViewById(R.id.lv_search);
        listInput=new ArrayList<>();
        adapter=new ArrayAdapter(SelectActivity.this,android.R.layout.simple_list_item_1,listInput);
        lvSearch.setAdapter(adapter);
        popupWindow=new PopupWindow(view,800,400);//指定PopupWindow的宽度和高度
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        popupWindow.setOutsideTouchable(true);

        //弹出位置模糊匹配
        suggestionSearch=SuggestionSearch.newInstance();
        suggestionSearch.setOnGetSuggestionResultListener(suggestionListener);

        //位置输入的点击事件(起点、终点)
        etStart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                //清空列表
                listInput.clear();
                //清除PopupWindow
                popupWindow.dismiss();
                //模糊搜索内容
                String startAddress=etStart.getText().toString();
                suggestionSearch.requestSuggestion(new SuggestionSearchOption().keyword(startAddress).city("广州"));
            }
        });
        etEnd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                //清空列表
                listInput.clear();
                //清除PopupWindow
                popupWindow.dismiss();
                //模糊搜索内容
                String startAddress=etEnd.getText().toString();
                suggestionSearch.requestSuggestion(new SuggestionSearchOption().keyword(startAddress).city("广州"));
            }
        });
        //PopupWindow中ListView的点击事件
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String Addr=listInput.get(position).toString();
                if(etStart.isFocused())
                {
                    etStart.setText(Addr);
                    etStart.setSelection(Addr.length());//光标移动到最后一行
                }
                else
                {
                    etEnd.setText(Addr);
                    etEnd.setSelection(Addr.length());//光标移动到最后一行
                }
                //释放PopupWindow
                popupWindow.dismiss();
            }
        });

    }
    //打开InfoActivity
    private void openNextActivity(String strSearch,int type)
    {
        //打开InfoActivity搜索
        Intent intent=new Intent(SelectActivity.this,InfoActivity.class);;
        //传入坐标
        intent.putExtra("startlat",startLat);
        intent.putExtra("startlong",startLong);
        intent.putExtra("endlat",endLat);
        intent.putExtra("endlong",endLong);
        //传入搜索类型
        intent.putExtra("searchtext",strSearch);
        intent.putExtra("searchtype",type);
        startActivity(intent);
    }


    //位置输入的匹配事件
    OnGetSuggestionResultListener suggestionListener=new OnGetSuggestionResultListener()
    {
        @Override
        public void onGetSuggestionResult(SuggestionResult res)
        {
            //未找到相关结果
            if(res==null || res.getAllSuggestions()==null)
                return;
            //找到
            else {
                List<SuggestionResult.SuggestionInfo> suggInfos=res.getAllSuggestions();
                for(int i=0;i<suggInfos.size();i++)
                    listInput.add(suggInfos.get(i).city+suggInfos.get(i).district+suggInfos.get(i).key);
                //更新PopupWindow的状态
                popupWindow.update();
                //在EditText中以下拉的方式显示(可设置显示位置)
                if(etStart.isFocused())
                    popupWindow.showAsDropDown(etStart,0,20);
                else
                    popupWindow.showAsDropDown(etEnd,0,20);
            }
        }
    };


    //标题栏菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;        }
        return true;
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_cate:
                openNextActivity("美食",1);
                break;
            case R.id.iv_grog:
                openNextActivity("酒店",1);
                break;
            case R.id.iv_bank:
                openNextActivity("银行",1);
                break;
            case R.id.iv_bus:
                openNextActivity("公交",1);
                break;
            case R.id.iv_scenery:
                openNextActivity("景点",1);
                break;
            case R.id.iv_gas:
                openNextActivity("加油站",1);
                break;
            case R.id.iv_collect:
                //openNextActivity("美食",1);
                break;
            case R.id.iv_more:
                //openNextActivity("更多",1);
                break;
            case R.id.btn_search:
                //起终点坐标搜索
                startAddress=etStart.getText().toString();
                endAddress=etEnd.getText().toString();
                mSearchStart.geocode(new GeoCodeOption().city("广东").address(startAddress));
                mSearchEnd.geocode(new GeoCodeOption().city("广东").address(endAddress));
                break;
            default:
                break;
        }
    }

    //权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                    mSearchStart.geocode(new GeoCodeOption().city("广东").address(startAddress));
                    mSearchEnd.geocode(new GeoCodeOption().city("广东").address(endAddress));
                }
                else
                    Toast.makeText(this,"你禁止了权限",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop()
    {
        count=0;
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        mSearchStart.destroy();
        mSearchEnd.destroy();
        super.onDestroy();
    }

}

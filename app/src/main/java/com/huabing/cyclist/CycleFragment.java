package com.huabing.cyclist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.huabing.cyclist.map.MyOrientationListener;
/**
 * Created by 30781 on 2017/3/26.
 */

public class CycleFragment extends Fragment implements View.OnClickListener {
    private View view;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocClient;
    private LocationMode locMode;
    private BitmapDescriptor mCurrentMarker;
    private MyOrientationListener orientationListener;
    private float mCurrentX;

    private ImageView ivMode;
    private PopupWindow popupWindow;
    private ImageView ivNormalMode;
    private ImageView ivSatelliteMode;
    private ImageView ivTrafficMode;
    private FloatingActionButton fabRoute;
    private boolean isFirstLocate;
    private String address;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //初始化地图
        SDKInitializer.initialize(getActivity().getApplicationContext());
        view=inflater.inflate(R.layout.frag_cycle,container,false);
        //权限检查，通过则初始化地图
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else {
            initMapView();
        }

        //地图模式
        ivMode=(ImageView)view.findViewById(R.id.iv_mode);
        ivMode.setOnClickListener(this);
        //路线选择
        fabRoute=(FloatingActionButton)view.findViewById(R.id.fab_route);
        fabRoute.setOnClickListener(this);
        return view;
    }

    //静态工厂方法
    public static CycleFragment newInstance(String title)
    {
        CycleFragment fragment=new CycleFragment();
        return fragment;
    }


    @Override
    public void onStart()
    {
        super.onStart();
        //开启定位的允许
        mBaiduMap.setMyLocationEnabled(true);
        //开启定位和方向传感器
        if(!mLocClient.isStarted())
        {
            mLocClient.start();//开始定位
            orientationListener.start();//开启方向传感器
        }
    }


    //初始化地图
    private void initMapView()
    {
        //地图控件
        mMapView=(MapView)view.findViewById(R.id.map_view);
        mBaiduMap=mMapView.getMap();
        //是否首次定位
        isFirstLocate=true;
        //开启方向感应器
        orientationListener=new MyOrientationListener(getActivity());
        orientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX=x;
            }
        });
        //定位LocationClient监听
        mLocClient=new LocationClient(getActivity().getApplicationContext());
        mLocClient.registerLocationListener(new MyLocationListener());
        //每隔1秒实时更新位置
        LocationClientOption option=new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setOpenGps(true);//打开GPS
        option.setScanSpan(1000);//隔2秒更新
        option.setIsNeedAddress(true);//需要提供位置
        mLocClient.setLocOption(option);
    }

    //位置监听
    public class MyLocationListener implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation location)
        {
            if(location==null || mMapView==null)
                return;
            //实时更新位置
            MyLocationData locData=new MyLocationData.Builder()
                                    .direction(mCurrentX)  //方向
                                    .accuracy(location.getRadius())
                                    .latitude(location.getLatitude())//经度
                                    .longitude(location.getLongitude())//纬度
                                    .build();
            mBaiduMap.setMyLocationData(locData);
            //如果是第一次定位
            if(isFirstLocate)
            {
                //当前位置坐标
                LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());
                MapStatus.Builder build = new MapStatus.Builder();
                build.target(ll).zoom(11.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(build.build()));
                isFirstLocate=false;
            }
            //记录现在的详细位置
            address=location.getAddrStr();
        }
        @Override
        public void onConnectHotSpotMessage(String s, int i)
        {
        }
    }

    //设置地图模式
    private void setMapMode()
    {
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.map_mode, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),(Bitmap)null));

        ivNormalMode=(ImageView)popupView.findViewById(R.id.iv_normalmode);
        ivNormalMode.setOnClickListener(this);
        ivSatelliteMode=(ImageView)popupView.findViewById(R.id.iv_satellitemode);
        ivSatelliteMode.setOnClickListener(this);
        ivTrafficMode=(ImageView)popupView.findViewById(R.id.iv_trafficmode);
        ivTrafficMode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            //地图模式选择
            case R.id.iv_mode:
                setMapMode();
                popupWindow.showAsDropDown(v);
                break;
            //普通地图模式
            case R.id.iv_normalmode:
                ivNormalMode.setImageResource(R.drawable.map_normal_select);
                ivSatelliteMode.setImageResource(R.drawable.map_satellite);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            //卫星地图模式
            case R.id.iv_satellitemode:
                ivSatelliteMode.setImageResource(R.drawable.map_satellite_select);
                ivNormalMode.setImageResource(R.drawable.map_noraml);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            //交通地图模式
            case R.id.iv_trafficmode:
                if (mBaiduMap.isTrafficEnabled()) {
                    mBaiduMap.setTrafficEnabled(false);
                    ivTrafficMode.setImageResource(R.drawable.map_traffic);
                }
                else {
                    ivTrafficMode.setImageResource(R.drawable.map_traffic_select);
                    mBaiduMap.setTrafficEnabled(true);
                }
                break;
            //路线选择浮动按钮
            case R.id.fab_route:
                Intent intent=new Intent(getActivity(),SelectActivity.class);
                intent.putExtra("address",address);
                startActivity(intent);
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
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    initMapView();
                else
                    Toast.makeText(getActivity(),"你禁止了权限",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
    @Override
    public void onResume()
    {
        mMapView.onResume();
        super.onResume();
    }
    @Override
    public void onPause()
    {
        mMapView.onPause();
        super.onPause();
    }
    @Override
    public void onDestroy()
    {
        mLocClient.stop();//退出时销毁定位
        mBaiduMap.setMyLocationEnabled(false);//关闭定位图层
        orientationListener.stop();//停止方向传感器
        mMapView.onDestroy();
        mMapView=null;
        super.onDestroy();
    }
}

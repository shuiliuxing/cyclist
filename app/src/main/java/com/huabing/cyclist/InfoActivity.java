package com.huabing.cyclist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mapapi.overlayutil.BikingRouteOverlay;
import mapapi.overlayutil.DrivingRouteOverlay;
import mapapi.overlayutil.PoiOverlay;

import static com.baidu.navisdk.adapter.PackageUtil.getSdcardDir;


public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar infoToolbar;
    //隐藏控件
    private LinearLayout llHide;
    private float density;
    private int hideHeight;
    //路线信息控件
    private TextView tvDistance;
    private TextView tvTime;
    private TextView tvCalorie;
    private TextView tvAltitude;
    private ImageView ivSound;
    private TextView tvSplit;
    //地图对象
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    //是否第一次定位
    private boolean isFirstLocate;
    //起终点坐标
    private double startlat;
    private double startlong;
    private double endlat;
    private double endlong;
    //POI搜索
    private PoiSearch mPoiSearch;
    //路线规划
    private RoutePlanSearch mSearch;
    //语音导航
    public static List<Activity> activityList = new LinkedList<Activity>();
    private static final String APP_FOLDER_NAME = "BNSDKSimpleDemo";
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    private String mSDCardPath = null;  //SD卡目录
    private String authinfo = null;
    //内部TTS播报状态回传handler
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                   // showToastMsg("Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    //showToastMsg("Handler : TTS play end");
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_info);
        //标题栏
        infoToolbar = (Toolbar) findViewById(R.id.tb_info_toolbar);
        infoToolbar.setTitle("路线信息");
        setSupportActionBar(infoToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //隐藏LinearLayout
        llHide = (LinearLayout) findViewById(R.id.ll_hide);
        density = getResources().getDisplayMetrics().density;//获取屏幕密度
        hideHeight = (int) (density * 120 + 0.5);
        //路线信息
        tvDistance = (TextView) findViewById(R.id.tv_distance);
        tvTime=(TextView)findViewById(R.id.tv_time);
        tvCalorie=(TextView)findViewById(R.id.tv_calorie);
        tvAltitude=(TextView)findViewById(R.id.tv_altitude);
        tvSplit = (TextView) findViewById(R.id.tv_split);//分割线
        //语音导航
        ivSound = (ImageView) findViewById(R.id.iv_sound);
        ivSound.setOnClickListener(this);
        //地图控件
        mMapView = (MapView) findViewById(R.id.map_view);
        mBaiduMap = mMapView.getMap();
        //是否首次定位
        isFirstLocate = true;
        //获取起点和终点
        Intent intent = getIntent();
        int type=intent.getIntExtra("searchtype",0);
        if(type==1)
        {
            String searchText=intent.getStringExtra("searchtext");
            llHide.setVisibility(View.GONE);
            ivSound.setVisibility(View.GONE);
            mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
            //PoiNearbySearchOption nearbySearchOption=new PoiNearbySearchOption()
             //                                               .keyword(searchText)
               //                                             .sortType();
        }
        else if(type==2)
        {
            initSearch(intent);
        }
    }

    //初始化位置搜索
    private void initSearch(Intent iten)
    {
        startlat = iten.getDoubleExtra("startlat", 0);
        startlong = iten.getDoubleExtra("startlong", 0);
        endlat = iten.getDoubleExtra("endlat", 0);
        endlong = iten.getDoubleExtra("endlong", 0);
        //路线规划
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(listener);
        //DrivingRoutePlanOption option=new DrivingRoutePlanOption();
        BikingRoutePlanOption option = new BikingRoutePlanOption();
        LatLng startLatLng = new LatLng(startlat, startlong);
        LatLng endLatLng = new LatLng(endlat, endlong);
        PlanNode startNode = PlanNode.withLocation(startLatLng);
        PlanNode endNode = PlanNode.withLocation(endLatLng);
        option.from(startNode);
        option.to(endNode);
        mSearch.bikingSearch(option);//骑行
        //初始化导航
        if (initDirs()) {
            initNavi();
        }
    }
    //POI搜索监听
    OnGetPoiSearchResultListener poiListener=new OnGetPoiSearchResultListener()
    {
        @Override
        public void onGetPoiResult(PoiResult poiResult)
        {
            //没找到
            if(poiResult==null || poiResult.error==SearchResult.ERRORNO.RESULT_NOT_FOUND)
            {
                return;
            }
            //找到
            if(poiResult.error==SearchResult.ERRORNO.NO_ERROR)
            {
                mBaiduMap.clear();
                MyPoiOverlay poiOverlay=new MyPoiOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(poiOverlay);
                poiOverlay.setData(poiResult);
                poiOverlay.addToMap();
                poiOverlay.zoomToSpan();
            }
        }
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult)
        {
            //获取Place详情页检索结果
        }
        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult)
        {

        }
    };
    //POI覆盖物点击事件
    private class MyPoiOverlay extends PoiOverlay
    {
        public MyPoiOverlay(BaiduMap baiduMap)
        {
            super(baiduMap);
        }
        @Override
        public boolean onPoiClick(int index)
        {
            super.onPoiClick(index);
            PoiInfo poi=getPoiResult().getAllPoi().get(index);
            mPoiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(poi.uid));
            return true;
        }
    }

    //内部TTS播报状态回调接口
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {
        @Override
        public void playEnd() {
            //showToastMsg("TTSPlayStateListener : TTS play end");
        }

        @Override
        public void playStart() {
            //showToastMsg("TTSPlayStateListener : TTS play start");
        }
    };

    private void initSetting() {
        // 设置是否双屏显示
        BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        // 设置导航播报模式
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        // 是否开启路况
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    //初始化导航
    private void initNavi() {
        BNOuterTTSPlayerCallback ttsCallback = null;
        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
                    @Override
                    public void onAuthResult(int status, String msg) {
                /*if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                InfoActivity.this.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run() {
                        Toast.makeText(InfoActivity.this, authinfo, Toast.LENGTH_LONG).show();
                    }
                });*/
                    }

                    public void initSuccess() {
                        //Toast.makeText(InfoActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                        initSetting();
                    }

                    public void initStart() {
                        //Toast.makeText(InfoActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
                    }

                    public void initFailed() {
                        //Toast.makeText(InfoActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
                    }
                },
                null, ttsHandler, ttsPlayStateListener);
    }

    //显示数据
    public void showToastMsg(final String msg) {
        InfoActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(InfoActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //路线规划
    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {
        }

        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult result) {
            //步行
        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult result) {
            //公交
        }

        //驾车
        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                //驾车路线集合
                List<DrivingRouteLine> lines = drivingRouteResult.getRouteLines();
                DrivingRouteLine routeLine = lines.get(0);//第一条路线
                //创建路线的覆盖物
                DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
                overlay.setData(routeLine);//把数据设置到覆盖物上
                overlay.addToMap();//把覆盖物添加到地图上
                overlay.zoomToSpan();//缩放到合适的距离
                LatLng ll = new LatLng(startlat, startlong);
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(update);
            }
        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
            if (bikingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                //驾车路线集合
                List<BikingRouteLine> lines = bikingRouteResult.getRouteLines();
                BikingRouteLine routeLine = lines.get(0);//第一条路线
                //创建路线的覆盖物
                BikingRouteOverlay overlay = new MyBikingRouteOverlay(mBaiduMap);
                overlay.setData(routeLine);//把数据设置到覆盖物上
                overlay.addToMap();//把覆盖物添加到地图上
                overlay.zoomToSpan();//缩放到合适的距离
                LatLng ll = new LatLng(startlat, startlong);
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(update);
                showBikeDistance(routeLine);
            }
        }
    };

    //骑行路线覆盖物
    private class MyBikingRouteOverlay extends BikingRouteOverlay {
        public MyBikingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        //起点覆盖物
        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.info_start);
        }

        //终点覆盖物
        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.info_end);
        }
    }

    //骑行信息显示
    private void showBikeDistance(BikingRouteLine routeLine) {
        DecimalFormat df=new DecimalFormat("##.##");
        //骑行距离
        double distance = routeLine.getDistance();
        tvDistance.setText(""+df.format(distance/1000));
        tvTime.setText(""+df.format(distance/1000/15));
        tvCalorie.setText(""+df.format(distance/15000*26));
        tvAltitude.setText(""+300);
    }

    //驾车路线覆盖物
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {
        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        //起点覆盖物
        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.info_start);
        }

        //终点覆盖物
        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.info_end);
        }
    }

    //语音播报
    private void routeplanToNavi() {
        CoordinateType coType = CoordinateType.GCJ02;
        BNRoutePlanNode sNode = null;
        BNRoutePlanNode eNode = null;
        sNode = new BNRoutePlanNode(startlong, startlat, "我的位置", null, coType);
        eNode = new BNRoutePlanNode(endlong, endlat, "目标地点", null, coType);
        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
        }
    }

    //语音播报监听
    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {
        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            //设置途径点以及resetEndNode会回调该接口
            for (Activity ac : activityList) {
                if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {
                    return;
                }
            }
            //打开导航Activity
            Intent intent = new Intent(InfoActivity.this, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        @Override
        public void onRoutePlanFailed() {
            Toast.makeText(InfoActivity.this, "算路失败", Toast.LENGTH_SHORT).show();
        }
    }

    //动画
    private void animateOpen(View v) {
        v.setVisibility(View.VISIBLE);
        tvSplit.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(v, 0, hideHeight);
        animator.start();
    }

    private void animateClose(final View view) {
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                tvSplit.setVisibility(View.GONE);
            }

        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);

            }
        });
        return animator;
    }

    //标题栏菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
        return true;
    }

    //标题栏菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_refresh:
                if (llHide.getVisibility() == View.GONE) {
                    animateOpen(llHide);
                    item.setIcon(R.drawable.info_fresh);
                } else {
                    animateClose(llHide);
                    item.setIcon(R.drawable.info_fresh_oppo);
                }
                break;

        }
        return true;
    }

    //所有点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_sound:
                routeplanToNavi();
                //Toast.makeText(this, "导航", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mSearch.destroy();
        super.onDestroy();
    }


}

package com.huabing.cyclist;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class NewsDetailActivity extends AppCompatActivity {

    private Toolbar tbToolbar;
    private LinearLayout llWebview;
    private WebView wvDetail=null;
    private WebSettings wvSetting;
    ProgressDialog dialog;
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar=(Toolbar)findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        llWebview=(LinearLayout)findViewById(R.id.llWebview);

        dialog=new ProgressDialog(NewsDetailActivity.this);
        dialog.setTitle("新闻详情");
        dialog.setMessage("请稍候...");
        dialog.setCancelable(true);
        //路径
        String url=getIntent().getStringExtra("next");
        //注入js过滤广告
        str="JavaScript:function setTop(){document.getElementById('mm-0').style.display='none';" +
                "document.getElementById('indexHeader').style.display='none';" +
                "document.getElementsByClassName('art_main boxs')[0].style.marginTop='0.5px';" +
                "document.getElementsByClassName('adsbygoogle')[0].style.display='none';" +
                "document.getElementById('_slideTxtBox').style.display='none';" +
                "document.querySelector('img[width=\"10px\"]').style.display='none';" +
                "document.getElementsByTagName('h3')[0].style.display='none';" +
                "document.querySelector('p[style=\"text-align: center\"]').style.display='none';" +
                "document.querySelector('img[height=\"110px\"]').style.display='none';" +
                "document.getElementById('commt').style.display='none';" +
                "document.getElementById('wheel_avatar').style.display='none';" +
                "var ride=document.getElementsByClassName('about_link clearfix')[0];if(ride!=null) ride.style.display='none';" +
                "document.getElementById('_slideTxtBox').style.display='none';window.control.showWebview();}setTop()";
        wvDetail=new WebView(this);
        wvDetail.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        wvDetail.setVisibility(View.INVISIBLE);
        llWebview.addView(wvDetail);

        wvSetting=wvDetail.getSettings();
        wvSetting.setJavaScriptEnabled(true);
        wvSetting.setDomStorageEnabled(true);
        wvSetting.setAppCacheEnabled(true);
        wvSetting.setAllowFileAccess(true);
        wvSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        wvSetting.setAppCacheMaxSize(1024 * 1024 * 9);// 设置缓冲大小
        wvSetting.setLoadWithOverviewMode(true);
        wvSetting.setUseWideViewPort(true);
        wvSetting.setBlockNetworkImage(true);    //先阻塞加载图片
        wvDetail.addJavascriptInterface(new JsInteraction(), "control");
        wvDetail.loadUrl(url);
        wvDetail.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                dialog.show();
                super.onPageStarted(view,url,favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                view.loadUrl(str);
            }
        });
    }

    //js返回调用
    public class JsInteraction {
        @JavascriptInterface
        public void showWebview() {   //提供给js调用的方法
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    wvSetting.setBlockNetworkImage(false);
                    dialog.dismiss();
                    wvDetail.setVisibility(View.VISIBLE);
                    //判断webview是否加载了，图片资源
                    if (!wvSetting.getLoadsImagesAutomatically()) {
                        //设置wenView加载图片资源
                        wvSetting.setLoadsImagesAutomatically(true);
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        wvDetail.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(wvDetail!=null){
            wvDetail.removeAllViews();
            wvDetail.destroy();
            wvDetail=null;
            llWebview.removeAllViews();
            llWebview=null;
        }
    }

}

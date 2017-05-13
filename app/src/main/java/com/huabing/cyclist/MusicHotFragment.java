package com.huabing.cyclist;


import android.database.Observable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.huabing.cyclist.adapter.EndLessOnScrollListener;
import com.huabing.cyclist.adapter.Music;
import com.huabing.cyclist.adapter.MusicAdapter;
import com.huabing.cyclist.gson.MusicBean;
import com.huabing.cyclist.util.HttpUtil;
import com.huabing.cyclist.view.GradientShaderTextView.GradientShaderTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MusicHotFragment extends Fragment {
    private MaterialRefreshLayout mrlRefresh;
    private RecyclerView rvMusivHot;
    private List<Music> musicAll;
    private MusicAdapter adapter;
    private int listNum;
    private boolean isFirstLoad;

    public static MusicHotFragment newInstance(String name) {
        MusicHotFragment fragment = new MusicHotFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_music_hot, container, false);
        //RecycleView初始化
        musicAll=new ArrayList<>();
        rvMusivHot = (RecyclerView) view.findViewById(R.id.rv_music_hot);
        final LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        rvMusivHot.setLayoutManager(manager);
        adapter=new MusicAdapter(musicAll);
        rvMusivHot.setAdapter(adapter);
        //刷新控件
        mrlRefresh=(MaterialRefreshLayout)view.findViewById(R.id.mrl_refresh);
        mrlRefresh.setLoadMore(true);
        mrlRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                if(!isFirstLoad) {
                    UpdateData();
                    isFirstLoad=true;
                }
                else
                {
                    mrlRefresh.finishRefresh();
                }
            }
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout)
            {
                UpdateMoreData();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        //初始时先自动加载20条歌曲数据
        mrlRefresh.autoRefresh();
    }

    //下拉刷新数据
    private void UpdateData()
    {
        String str="http://music.163.com/api/playlist/detail?id=3778678&updateTime=-1";
        HttpUtil.sendOkHttpRequest(str, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //返回的json数据
                String result=response.body().string();
                //将json数据解析为一个MusicBean类
                Gson gson=new Gson();
                MusicBean musicBean=gson.fromJson(result,MusicBean.class);
                //json数据中歌曲的数量
                int total=musicBean.getResult().getTracks().size();
                //当前列表的数量
                int startNum=musicAll.size();
                int endNum=startNum+20;
                //每次刷新20条
                if(endNum<total)
                {
                    for (int i=startNum; i<endNum; i++) {
                        int num = i + 1;
                        int id = musicBean.getResult().getTracks().get(i).getId();
                        String name = musicBean.getResult().getTracks().get(i).getName();
                        String author = musicBean.getResult().getTracks().get(i).getArtists().get(0).getName();
                        String pic = musicBean.getResult().getTracks().get(i).getAlbum().getPicurl();
                        String url = musicBean.getResult().getTracks().get(i).getMp3url();
                        Music music = new Music(num, id, name, author, pic, url);
                        musicAll.add(music);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mrlRefresh.finishRefresh();
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }



    //上拉加载更多
    private void UpdateMoreData()
    {
        String str="http://music.163.com/api/playlist/detail?id=3778678&updateTime=-1";
        HttpUtil.sendOkHttpRequest(str, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //返回的json数据
                String result=response.body().string();
                //将json数据解析为一个MusicBean类
                Gson gson=new Gson();
                MusicBean musicBean=gson.fromJson(result,MusicBean.class);
                //json数据中歌曲的数量
                int total=musicBean.getResult().getTracks().size();
                //当前列表的数量
                int startNum=musicAll.size();
                int endNum=startNum+20;
                //每次刷新20条
                if(endNum<total)
                {
                    for (int i=startNum; i<endNum; i++) {
                        int num = i + 1;
                        int id = musicBean.getResult().getTracks().get(i).getId();
                        String name = musicBean.getResult().getTracks().get(i).getName();
                        String author = musicBean.getResult().getTracks().get(i).getArtists().get(0).getName();
                        String pic = musicBean.getResult().getTracks().get(i).getAlbum().getPicurl();
                        String url = musicBean.getResult().getTracks().get(i).getMp3url();
                        Music music = new Music(num, id, name, author, pic, url);
                        musicAll.add(music);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mrlRefresh.finishRefreshLoadMore();
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(),"刷新成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }
}

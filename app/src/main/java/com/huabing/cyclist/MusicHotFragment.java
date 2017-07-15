package com.huabing.cyclist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.huabing.cyclist.adapter.MusicHotAdapter;
import com.huabing.cyclist.database.MusicHot;
import com.huabing.cyclist.gson.musichotgson.MusicHotBean;
import com.huabing.cyclist.util.HttpUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MusicHotFragment extends Fragment{
    //加载控件
    ProgressBar pbLoading;
    //刷新控件
    private MaterialRefreshLayout mrlRefresh;
    //热门列表
    private RecyclerView rvMusivHot;
    private List<MusicHot> musicHotList;
    private MusicHotAdapter adapter;

    public static MusicHotFragment newInstance(String name) {
        MusicHotFragment fragment = new MusicHotFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_music_hot, container, false);
        //加载控件初始化
        pbLoading=(ProgressBar)view.findViewById(R.id.pb_loading);
        //RecycleView初始化
        musicHotList=new ArrayList<>();
        rvMusivHot = (RecyclerView) view.findViewById(R.id.rv_music_hot);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        rvMusivHot.setLayoutManager(manager);
        adapter=new MusicHotAdapter(musicHotList);
        rvMusivHot.setAdapter(adapter);
        //刷新控件
        mrlRefresh=(MaterialRefreshLayout)view.findViewById(R.id.mrl_refresh);
        mrlRefresh.setLoadMore(true);
        mrlRefresh.setMaterialRefreshListener(new MaterialRefreshListener()
        {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout)
            {
                mrlRefresh.finishRefresh();
            }
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout)
            {
                mrlRefresh.finishRefreshLoadMore();
            }
        });

        //判断数据库是否有数据
        List<MusicHot> tempList=DataSupport.findAll(MusicHot.class);
        int length=tempList.size();
        if(length>0)   //有数据
        {
            pbLoading.setVisibility(View.GONE);  //隐藏加载控件
            mrlRefresh.setVisibility(View.VISIBLE);  //显示列表
            for(int i=0;i<length;i++)
                musicHotList.add(tempList.get(i));
            adapter.notifyDataSetChanged();
        }
        else //没有数据，从网上加载
        {
            getMusicHotData(); //获取音乐热歌数据
        }
        return view;
    }

    //获取热门音乐数据
    private void getMusicHotData()
    {
        //热歌榜
        final String address="http://music.163.com/api/playlist/detail?id=3778678&updateTime=-1";
        HttpUtil.sendOkHttpRequest(address, new Callback()
        {
            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                //返回的json数据
                String result=response.body().string();
                //将json数据解析为一个MusicBean类
                Gson gson=new Gson();
                MusicHotBean musicHotBean=gson.fromJson(result,MusicHotBean.class);
                //解析完成后，热歌数据存入数据库
                int length=musicHotBean.getResult().getTracks().size();
                for(int i=0;i<length;i++)
                {
                    MusicHot musicHot=new MusicHot();
                    musicHot.setRank(i+1);
                    long songId=musicHotBean.getResult().getTracks().get(i).getId();
                    musicHot.setSongId(songId);
                    musicHot.setName(musicHotBean.getResult().getTracks().get(i).getName());
                    musicHot.setAuthor(musicHotBean.getResult().getTracks().get(i).getArtists().get(0).getName());
                    musicHot.setPicUrl(musicHotBean.getResult().getTracks().get(i).getAlbum().getPicUrl());
                    musicHot.setSongLink("http://music.163.com/song/media/outer/url?id="+songId+".mp3");
                    musicHot.save();
                }
                //从数据库提取数据，更新热歌列表
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<MusicHot> tempList= DataSupport.findAll(MusicHot.class);
                        int tempLength=tempList.size();
                        //数据库有数据
                        if(tempLength>0)
                        {
                            pbLoading.setVisibility(View.GONE);  //隐藏加载控件
                            mrlRefresh.setVisibility(View.VISIBLE);  //显示列表
                            for(int i=0;i<tempLength;i++)
                                musicHotList.add(tempList.get(i));
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

            }
            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }



    //上拉加载更多
    /*private void UpdateMoreData()
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
    }*/
}

package com.huabing.cyclist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huabing.cyclist.adapter.MusicMenuAdapter;
import com.huabing.cyclist.bean.MusicMenu;
import com.huabing.cyclist.gson.musicmenugson.MusicMenuBean;
import com.huabing.cyclist.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MusicMenuFragment extends Fragment {
    private TextView tvLoad;
    private List<MusicMenu> musicMenuList;
    private MusicMenuAdapter adapter;
    private RecyclerView rvMusicMenu;

    public static MusicMenuFragment newInstance(String name) {
        MusicMenuFragment fragment = new MusicMenuFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music_menu, container, false);
        musicMenuList=new ArrayList<>();
        tvLoad=(TextView)view.findViewById(R.id.tv_load);
        //歌单RecycleView列表
        rvMusicMenu=(RecyclerView)view.findViewById(R.id.rv_music_menu);
        GridLayoutManager manager=new GridLayoutManager(container.getContext(),2);
        rvMusicMenu.setLayoutManager(manager);
        adapter=new MusicMenuAdapter(musicMenuList);
        rvMusicMenu.setAdapter(adapter);
        //加载数据
        String address="http://123.207.38.178/wangyimusic/travelmenu.json";
        getMusicMenuData(address);
        return view;
    }


    //刷新歌曲数据
    private void getMusicMenuData(String address)
    {
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonData=response.body().string();
                if(jsonData!=null)
                {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvLoad.setVisibility(View.GONE);
                            Gson gson=new Gson();
                            MusicMenuBean musicMenuBean =gson.fromJson(jsonData,MusicMenuBean.class);
                            int length=musicMenuBean.getMenu().size();
                            if(length>0)
                            {
                                tvLoad.setVisibility(View.GONE);//隐藏加载控件
                                rvMusicMenu.setVisibility(View.VISIBLE); //显示RecyclerView
                                //刷新数据
                                for(int i=0;i<length;i++)
                                {
                                    MusicMenu musicMenu=new MusicMenu();
                                    musicMenu.setId(musicMenuBean.getMenu().get(i).getId());
                                    musicMenu.setName(musicMenuBean.getMenu().get(i).getName());
                                    musicMenu.setAuthor(musicMenuBean.getMenu().get(i).getAuthor());
                                    musicMenu.setCollect(musicMenuBean.getMenu().get(i).getCollect());
                                    musicMenu.setHear(musicMenuBean.getMenu().get(i).getHear());
                                    musicMenu.setPicUrl(musicMenuBean.getMenu().get(i).getPicUrl());
                                    musicMenuList.add(musicMenu);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        });
    }

}

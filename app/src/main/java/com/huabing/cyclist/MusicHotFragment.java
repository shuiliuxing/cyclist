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
import android.widget.Toast;

import com.google.gson.Gson;
import com.huabing.cyclist.adapter.Music;
import com.huabing.cyclist.adapter.MusicAdapter;
import com.huabing.cyclist.adapter.RecyclerScrollListener;
import com.huabing.cyclist.gson.MusicBean;
import com.huabing.cyclist.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MusicHotFragment extends Fragment {
    private SwipeRefreshLayout srlRefresh;
    private RecyclerView rvMusivHot;
    private List<Music> musicAll;
    private MusicAdapter adapter;

    public static MusicHotFragment newInstance(String name) {
        MusicHotFragment fragment = new MusicHotFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_music_hot, container, false);
        musicAll=new ArrayList<>();
        srlRefresh=(SwipeRefreshLayout)view.findViewById(R.id.srl_refresh);
        srlRefresh.setRefreshing(true);

        rvMusivHot=(RecyclerView)view.findViewById(R.id.rv_music_hot);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        rvMusivHot.setLayoutManager(manager);
        adapter=new MusicAdapter(musicAll);
        rvMusivHot.setAdapter(adapter);
        rvMusivHot.addOnScrollListener(new RecyclerScrollListener(manager) {
            @Override
           public void onLoadMore(int currentPage)
            {
               // simulateLoadMoreData();
                Music music=new Music(11,1435443,"歌名","作者","图片路径","连接");
                musicAll.add(music);
                adapter.notifyDataSetChanged();

                Toast.makeText(getActivity(), "Load Finished!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        String str="http://music.163.com/api/playlist/detail?id=3778678&updateTime=-1";
        HttpUtil.sendOkHttpRequest(str, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                Gson gson=new Gson();
                MusicBean musicBean=gson.fromJson(result,MusicBean.class);
                //int length=musicList.getResult().getTracks().size();
                for(int i=0;i<10;i++)
                {
                    int num=i+1;
                    int id=musicBean.getResult().getTracks().get(i).getId();
                    String name=musicBean.getResult().getTracks().get(i).getName();
                    String author=musicBean.getResult().getTracks().get(i).getArtists().get(0).getName();
                    String pic=musicBean.getResult().getTracks().get(i).getAlbum().getPicurl();
                    String url=musicBean.getResult().getTracks().get(i).getMp3url();
                    Music music=new Music(num,id,name,author,pic,url);
                    musicAll.add(music);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        srlRefresh.setRefreshing(false);
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }
}

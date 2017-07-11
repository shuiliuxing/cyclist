package com.huabing.cyclist;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huabing.cyclist.adapter.MusicAdapter;
import com.huabing.cyclist.bean.Music;
import com.huabing.cyclist.database.MusicHot;
import com.huabing.cyclist.gson.musichotgson.MusicHotBean;
import com.huabing.cyclist.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MusicActivity extends AppCompatActivity {

    private SwipeRefreshLayout srlRefresh;
    private RecyclerView rvMusivHot;
    private List<MusicHot> musicList;
    private MusicAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        musicList=new ArrayList<>();
        srlRefresh=(SwipeRefreshLayout)findViewById(R.id.srl_music_menu);
        srlRefresh.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlRefresh.setRefreshing(false);
                Toast.makeText(MusicActivity.this,"已刷新！",Toast.LENGTH_SHORT).show();
            }
        });

        rvMusivHot=(RecyclerView)findViewById(R.id.rv_music_menu);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rvMusivHot.setLayoutManager(manager);
        adapter=new MusicAdapter(musicList);
        rvMusivHot.setAdapter(adapter);
        //获取旅行歌单ID
        long id=getIntent().getLongExtra("songId",0);
        //获取旅行歌单json并解析
        String str="http://music.163.com/api/playlist/detail?id="+707575900+"&updateTime=-1";
        HttpUtil.sendOkHttpRequest(str, new Callback()
        {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if(result!=null)
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            MusicHotBean musicHotBean = gson.fromJson(result, MusicHotBean.class);
                            int length = musicHotBean.getResult().getTracks().size();
                            for (int i = 0; i < length; i++)
                            {
                                MusicHot music = new MusicHot();
                                music.setRank(i+1);
                                long songId=musicHotBean.getResult().getTracks().get(i).getId();
                                music.setSongId(songId);
                                music.setName(musicHotBean.getResult().getTracks().get(i).getName());
                                music.setAuthor(musicHotBean.getResult().getTracks().get(i).getArtists().get(0).getName());
                                music.setPicUrl(musicHotBean.getResult().getTracks().get(i).getAlbum().getPicUrl());
                                String songLink="http://music.163.com/song/media/outer/url?id=" + songId + ".mp3";
                                music.setSongLink(songLink);
                                musicList.add(music);
                            }
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
}

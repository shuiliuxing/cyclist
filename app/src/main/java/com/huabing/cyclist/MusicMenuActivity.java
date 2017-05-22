package com.huabing.cyclist;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huabing.cyclist.adapter.Music;
import com.huabing.cyclist.adapter.MusicAdapter;
import com.huabing.cyclist.gson.MusicBean;
import com.huabing.cyclist.gson.MusicMenuBean;
import com.huabing.cyclist.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MusicMenuActivity extends AppCompatActivity {

    private SwipeRefreshLayout srlRefresh;
    private RecyclerView rvMusivHot;
    private List<Music> musicAll;
    private MusicAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_menu);

        musicAll=new ArrayList<>();
        srlRefresh=(SwipeRefreshLayout)findViewById(R.id.srl_music_menu);
        srlRefresh.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        rvMusivHot=(RecyclerView)findViewById(R.id.rv_music_menu);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rvMusivHot.setLayoutManager(manager);
        adapter=new MusicAdapter(musicAll);
        rvMusivHot.setAdapter(adapter);

        int id=getIntent().getIntExtra("id",0);
        String str="http://music.163.com/api/playlist/detail?id="+id+"&updateTime=-1";
        HttpUtil.sendOkHttpRequest(str, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson=new Gson();
                MusicMenuBean musicMenuBean =gson.fromJson(result,MusicMenuBean.class);
                //int length=musicList.getResult().getTracks().size();
                for (int i = 0; i < 10; i++) {
                    int num = i + 1;
                    int id = musicMenuBean.getResult().getTracks().get(i).getId();
                    String name = musicMenuBean.getResult().getTracks().get(i).getName();
                    String author = musicMenuBean.getResult().getTracks().get(i).getArtists().get(0).getName();
                    String pic = musicMenuBean.getResult().getTracks().get(i).getAlbum().getPicurl();
                    //String url = musicMenuBean.getResult().getTracks().get(i).getMp3url();
                    String url = "http://link.hhtjim.com/163/"+id+".mp3";
                    Music music = new Music(num, id, name, author, pic, url);
                    musicAll.add(music);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }
}

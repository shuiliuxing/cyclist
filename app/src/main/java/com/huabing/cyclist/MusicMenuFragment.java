package com.huabing.cyclist;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huabing.cyclist.adapter.MusicMenu;
import com.huabing.cyclist.adapter.MusicMenuAdapter;
import com.huabing.cyclist.gson.MusicBean;
import com.huabing.cyclist.gson.MusicMenuBean;
import com.huabing.cyclist.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MusicMenuFragment extends Fragment {
    private TextView tvLoad;
    private List<MusicMenu> musicMenuList;
    private MusicMenuAdapter adapter;
    private String[] address;
    private int num;

    public static MusicMenuFragment newInstance(String name) {
        MusicMenuFragment fragment = new MusicMenuFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_music_menu, container, false);
        musicMenuList=new ArrayList<>();
        tvLoad=(TextView)view.findViewById(R.id.tv_load);
        //旅行歌单地址
        address=new String[]{"http://music.163.com/api/playlist/detail?id=551801688&updateTime=-1",
                "http://music.163.com/api/playlist/detail?id=83315865&updateTime=-1",
                "http://music.163.com/api/playlist/detail?id=637424692&updateTime=-1",
                "http://music.163.com/api/playlist/detail?id=89908894&updateTime=-1",
                "http://music.163.com/api/playlist/detail?id=519332769&updateTime=-1",
                "http://music.163.com/api/playlist/detail?id=55128269&updateTime=-1"};
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.rv_music_menu);
        GridLayoutManager manager=new GridLayoutManager(container.getContext(),2);
        recyclerView.setLayoutManager(manager);
        adapter=new MusicMenuAdapter(musicMenuList);
        recyclerView.setAdapter(adapter);

        num=0;
        musicMenuList.clear();
        //开启异步加载
        ParseTask parseTask=new ParseTask();
        parseTask.execute(address[0]);
        return view;
    }


    //异步加载解析旅行歌单
    class ParseTask extends AsyncTask<String,Integer,Boolean>
    {
        @Override
        protected Boolean doInBackground(String... params)
        {
            try
            {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                                .url(params[0])
                                .build();
                Response response=client.newCall(request).execute();
                String result=response.body().string();
                Gson gson=new Gson();
                MusicMenuBean musicMenuBean =gson.fromJson(result,MusicMenuBean.class);
                int id=musicMenuBean.getResult().getId();
                String coverImage= musicMenuBean.getResult().getCoverimgurl();
                int comment= musicMenuBean.getResult().getPlaycount();
                String name= musicMenuBean.getResult().getName();
                MusicMenu musicMenu=new MusicMenu(id,coverImage,comment,name);
                musicMenuList.add(musicMenu);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            if(result)
            {
                num++;
                if(num>=0&&num<6)
                {
                    ParseTask parseTask=new ParseTask();
                    parseTask.execute(address[num]);
                }
                else if(num==6)
                {
                    tvLoad.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    num=-1;
                }
            }
        }
    }
    @Override
    public void onPause()
    {
        num=-1;
        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        num=-1;
        musicMenuList.clear();
        super.onDestroy();
    }

}

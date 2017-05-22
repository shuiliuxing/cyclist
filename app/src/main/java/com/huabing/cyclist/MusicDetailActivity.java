package com.huabing.cyclist;

import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.huabing.cyclist.adapter.Music;
import com.huabing.cyclist.gson.MusicLrc;
import com.huabing.cyclist.view.lrcview.LrcView;
import com.huabing.cyclist.util.HttpUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MusicDetailActivity extends AppCompatActivity implements View.OnClickListener,MediaPlayer.OnCompletionListener{
    private Music music;
    private LrcView lvLrc;
    private ImageView ivMusicPre;
    private ImageView ivMusicPlay;
    private ImageView ivMusicNext;
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private Handler handler=new Handler();
    private String strLrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.activity_open,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
        //获取传入的Music对象
        music=(Music)getIntent().getSerializableExtra("music_data");
        //标题栏Toolbar
        Toolbar toolbar=(Toolbar)findViewById(R.id.tb_music_toobar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //加载音乐歌手背景
        CollapsingToolbarLayout ctlCollapsing=(CollapsingToolbarLayout)findViewById(R.id.ctl_collapsing);
        ImageView ivMusicAuthor=(ImageView)findViewById(R.id.iv_music_author);
        ctlCollapsing.setTitle(music.getName());
        Glide.with(this).load(music.getPic()).into(ivMusicAuthor);

        lvLrc=(LrcView)findViewById(R.id.lv_lrc);
        //播放按钮
        ivMusicPre=(ImageView)findViewById(R.id.iv_music_pre);
        ivMusicPre.setOnClickListener(this);
        ivMusicPlay=(ImageView)findViewById(R.id.iv_music_play);
        ivMusicPlay.setOnClickListener(this);
        ivMusicNext=(ImageView)findViewById(R.id.iv_music_next);
        ivMusicNext.setOnClickListener(this);
        //MediaPlayer的结束事件
        mediaPlayer.setOnCompletionListener(this);
        //初始化MediaPlayer
        try
        {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(music.getUrl());
            mediaPlayer.prepare();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //获取歌词并用LrvView加载
        String address="http://music.163.com/api/song/lyric?os=pc&id="+music.getId()+"&lv=-1&kv=-1&tv=-1";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData=strLrc=response.body().string();
                Gson gson=new Gson();
                MusicLrc musicLrc=gson.fromJson(jsonData,MusicLrc.class);
                strLrc=musicLrc.getLrc().getLyric().toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(strLrc!=null) {
                            lvLrc.loadLrc(strLrc);
                        }
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }

    //随播放进度更新歌词
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(mediaPlayer.isPlaying())
            {
                long time=mediaPlayer.getCurrentPosition();
                lvLrc.updateTime(time);
            }
            handler.postDelayed(this,100);
        }
    };

    //MediaPlayer播放结束
    @Override
    public void onCompletion(MediaPlayer mp)
    {
        //停止歌词
        lvLrc.onDrag(0);
        //停止播放
        ivMusicPlay.setImageResource(R.drawable.music_pause);
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_music_pre:
                break;
            case R.id.iv_music_play:
                if(!mediaPlayer.isPlaying())
                {
                    ivMusicPlay.setImageResource(R.drawable.music_play);
                    mediaPlayer.start();
                    handler.post(runnable);
                }
                else
                {
                    ivMusicPlay.setImageResource(R.drawable.music_pause);
                    mediaPlayer.pause();
                    handler.removeCallbacks(runnable);
                }
                break;
            case R.id.iv_music_next:
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish()
    {
        super.finish();
        this.overridePendingTransition(R.anim.activity_close,0);
    }

    @Override
    protected void onDestroy()
    {
        handler.removeCallbacks(runnable);
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer=null;
        super.onDestroy();
    }
}

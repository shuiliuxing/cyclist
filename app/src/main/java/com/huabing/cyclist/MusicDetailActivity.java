package com.huabing.cyclist;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.huabing.cyclist.database.MusicHot;
import com.huabing.cyclist.gson.musiclrcgson.MusicLrcBean;
import com.huabing.cyclist.view.lrcview.LrcView;
import com.huabing.cyclist.util.HttpUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MusicDetailActivity extends AppCompatActivity implements View.OnClickListener,MediaPlayer.OnCompletionListener{
    private CollapsingToolbarLayout ctlCollapsing;
    private ImageView ivMusicAuthor;
    private MusicHot musicHot;
    private LrcView lvLrc;
    private ImageView ivMusicPre;
    private ImageView ivMusicPlay;
    private ImageView ivMusicNext;
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private Handler handler=new Handler();
    private String strLrc;
    private int currentRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.activity_open,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
        //获取传入的Music对象
        musicHot=(MusicHot)getIntent().getSerializableExtra("music_data");
        //当前歌曲序号
        currentRank=musicHot.getRank();
        //标题栏Toolbar
        Toolbar toolbar=(Toolbar)findViewById(R.id.tb_music_toobar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //加载音乐歌手背景
        ctlCollapsing=(CollapsingToolbarLayout)findViewById(R.id.ctl_collapsing);
        ivMusicAuthor=(ImageView)findViewById(R.id.iv_music_author);
        ctlCollapsing.setTitle(musicHot.getName());
        Glide.with(this).load(musicHot.getPicUrl()).into(ivMusicAuthor);

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
        String songLink=musicHot.getSongLink();
        String lrcAddress="http://music.163.com/api/song/lyric?os=pc&id="+musicHot.getSongId()+"&lv=-1&kv=-1&tv=-1";
        resetMediaPlayer(songLink,lrcAddress);
    }

    //初始化播放器
    private void resetMediaPlayer(String songLink,String lrcAddress)
    {
        ivMusicPlay.setImageResource(R.drawable.music_pause);
        try
        {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(songLink);
            mediaPlayer.prepare();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //获取歌词并用LrvView加载
        HttpUtil.sendOkHttpRequest(lrcAddress, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData=strLrc=response.body().string();
                Gson gson=new Gson();
                MusicLrcBean musicLrcBean=gson.fromJson(jsonData,MusicLrcBean.class);
                strLrc=musicLrcBean.getLrc().getLyric().toString();
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
                if( currentRank<=1)  //没有前一首
                {
                    Toast.makeText(this,"当前已经是第一首！",Toast.LENGTH_SHORT).show();
                }
                else  //有前一首
                {
                    currentRank--;
                    List<MusicHot> preMusicHots= DataSupport.where("rank=?",String.valueOf(currentRank)).find(MusicHot.class);
                    if(preMusicHots.size()>0)  //找到前一首
                    {
                        //更新歌曲名、背景
                        ctlCollapsing.setTitle(preMusicHots.get(0).getName());
                        Glide.with(this).load(preMusicHots.get(0).getPicUrl()).into(ivMusicAuthor);
                        //更新歌曲链接、更新歌词
                        String songLink=preMusicHots.get(0).getSongLink();
                        String lrcAddress="http://music.163.com/api/song/lyric?os=pc&id="+preMusicHots.get(0).getSongId()+"&lv=-1&kv=-1&tv=-1";
                        resetMediaPlayer(songLink,lrcAddress);
                    }
                }
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
                if( currentRank>=150)  //没有前一首
                {
                    Toast.makeText(this,"当前已经是最后一首！",Toast.LENGTH_SHORT).show();
                }
                else  //有前一首
                {
                    currentRank++;
                    List<MusicHot> preMusicHots= DataSupport.where("rank=?",String.valueOf(currentRank)).find(MusicHot.class);
                    if(preMusicHots.size()>0)  //找到前一首
                    {
                        //更新歌曲名、背景
                        ctlCollapsing.setTitle(preMusicHots.get(0).getName());
                        Glide.with(this).load(preMusicHots.get(0).getPicUrl()).into(ivMusicAuthor);
                        //更新歌曲链接、更新歌词
                        String songLink=preMusicHots.get(0).getSongLink();
                        String lrcAddress="http://music.163.com/api/song/lyric?os=pc&id="+preMusicHots.get(0).getSongId()+"&lv=-1&kv=-1&tv=-1";
                        resetMediaPlayer(songLink,lrcAddress);
                    }
                }
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

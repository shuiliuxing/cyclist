package com.huabing.cyclist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huabing.cyclist.MusicActivity;
import com.huabing.cyclist.MusicDetailActivity;
import com.huabing.cyclist.R;
import com.huabing.cyclist.bean.Music;
import com.huabing.cyclist.database.MusicHot;

import java.util.List;

/**
 * Created by 30781 on 2017/7/10.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder>{
    private Context mContext;
    private List<MusicHot> musicList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View viewMusic;
        TextView tvRank;
        ImageView ivPicUrl;
        TextView tvName;
        TextView tvMusicAuthor;
        public ViewHolder(View view)
        {
            super(view);
            viewMusic=view;
            tvRank=(TextView)view.findViewById(R.id.tv_rank);
            ivPicUrl=(ImageView)view.findViewById(R.id.iv_picurl);
            tvName=(TextView)view.findViewById(R.id.tv_name);
            tvMusicAuthor=(TextView)view.findViewById(R.id.tv_author);
        }
    }

    public MusicAdapter(List<MusicHot> musicHotList)
    {
        this.musicList=musicHotList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        mContext=parent.getContext();
        View view= LayoutInflater.from(mContext).inflate(R.layout.adapter_music_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.viewMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                MusicHot music=musicList.get(position);
                Intent intent=new Intent(mContext, MusicDetailActivity.class);
                intent.putExtra("music_data",music);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position)
    {
        MusicHot music=musicList.get(position);
        holder.tvRank.setText(""+music.getRank());
        holder.tvName.setText(music.getName());
        holder.tvMusicAuthor.setText(music.getAuthor());
        Glide.with(mContext).load(music.getPicUrl()).into(holder.ivPicUrl);
    }

    @Override
    public int getItemCount()
    {
        return musicList.size();
    }
}

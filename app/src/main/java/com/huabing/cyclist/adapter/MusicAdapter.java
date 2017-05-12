package com.huabing.cyclist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huabing.cyclist.MusicDetailActivity;
import com.huabing.cyclist.R;

import java.util.List;

/**
 * Created by 30781 on 2017/5/7.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder>{
    private List<Music> musicList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View viewMusic;
        TextView tvMusicNum;
        TextView tvMusicName;
        TextView tvMusicAuthor;
        public ViewHolder(View view)
        {
            super(view);
            viewMusic=view;
            tvMusicNum=(TextView)view.findViewById(R.id.tv_music_num);
            tvMusicName=(TextView)view.findViewById(R.id.tv_music_name);
            tvMusicAuthor=(TextView)view.findViewById(R.id.tv_music_author);
        }
    }

    public MusicAdapter(List<Music> musicList)
    {
        this.musicList=musicList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.viewMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Music music=musicList.get(position);
                Context context=v.getContext();
                Intent intent=new Intent(context, MusicDetailActivity.class);
                intent.putExtra("music_data",music);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position)
    {
        Music music=musicList.get(position);
        holder.tvMusicNum.setText(""+music.getNum());
        holder.tvMusicName.setText(music.getName());
        holder.tvMusicAuthor.setText(music.getAuthor());
    }

    @Override
    public int getItemCount()
    {
        return musicList.size();
    }
}

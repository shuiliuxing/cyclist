package com.huabing.cyclist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huabing.cyclist.MusicDetailActivity;
import com.huabing.cyclist.MusicMenuActivity;
import com.huabing.cyclist.R;
import com.huabing.cyclist.gson.MusicMenuBean;

import java.util.List;

/**
 * Created by 30781 on 2017/5/9.
 */

public class MusicMenuAdapter extends RecyclerView.Adapter<MusicMenuAdapter.ViewHolder>{
    private List<MusicMenu> musicList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView ivMenuBackground;
        TextView tvMenuComment;
        TextView tvMenuName;
        public ViewHolder(View view)
        {
            super(view);
            cardView=(CardView)view;
            ivMenuBackground=(ImageView)view.findViewById(R.id.iv_menu_background);
            tvMenuComment=(TextView)view.findViewById(R.id.tv_menu_comment);
            tvMenuName=(TextView)view.findViewById(R.id.tv_menu_name);
        }
    }

    public MusicMenuAdapter(List<MusicMenu> musicList)
    {
        this.musicList=musicList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.music_menu_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                MusicMenu musicMenu=musicList.get(position);
                Context context=v.getContext();
                Intent intent=new Intent(context, MusicMenuActivity.class);
                intent.putExtra("id",musicMenu.getId());
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position)
    {
        MusicMenu musicMenu=musicList.get(position);
        Glide.with(context).load(musicMenu.getCoverImage()).into(holder.ivMenuBackground);
        holder.tvMenuComment.setText(""+musicMenu.getComment());
        holder.tvMenuName.setText(musicMenu.getName());
    }

    @Override
    public int getItemCount()
    {
        return musicList.size();
    }
}
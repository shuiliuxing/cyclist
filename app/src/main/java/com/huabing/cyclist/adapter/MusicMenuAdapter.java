package com.huabing.cyclist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huabing.cyclist.MusicActivity;
import com.huabing.cyclist.R;
import com.huabing.cyclist.bean.MusicMenu;

import java.util.List;

/**
 * Created by 30781 on 2017/5/9.
 */

public class MusicMenuAdapter extends RecyclerView.Adapter<MusicMenuAdapter.ViewHolder>{
    private List<MusicMenu> musicMenuList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView ivPicUrl;
        TextView tvHear;
        TextView tvName;
        public ViewHolder(View view)
        {
            super(view);
            cardView=(CardView)view;
            ivPicUrl=(ImageView)view.findViewById(R.id.iv_picurl);
            tvHear=(TextView)view.findViewById(R.id.tv_hear);
            tvName=(TextView)view.findViewById(R.id.tv_name);
        }
    }

    public MusicMenuAdapter(List<MusicMenu> musicMenuList)
    {
        this.musicMenuList=musicMenuList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        mContext=parent.getContext();
        View view= LayoutInflater.from(mContext).inflate(R.layout.adapter_music_menu_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position=holder.getAdapterPosition();
                MusicMenu musicMenu=musicMenuList.get(position);
                Intent intent=new Intent(mContext, MusicActivity.class);
                intent.putExtra("songId",musicMenu.getId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position)
    {
        MusicMenu musicMenu=musicMenuList.get(position);
        holder.tvHear.setText(""+musicMenu.getHear());
        holder.tvName.setText(musicMenu.getName());
        Glide.with(mContext).load(musicMenu.getPicUrl()).into(holder.ivPicUrl);
    }

    @Override
    public int getItemCount()
    {
        return musicMenuList.size();
    }
}
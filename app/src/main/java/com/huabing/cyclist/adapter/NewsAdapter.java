package com.huabing.cyclist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huabing.cyclist.NewsDetailActivity;
import com.huabing.cyclist.R;

import java.util.List;

/**
 * Created by 30781 on 2017/5/6.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private List<News> newsList;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout llNews;
        TextView tvTitle;
        ImageView ivImage;
        TextView tvTime;
        public ViewHolder(View view)
        {
            super(view);
            llNews=(LinearLayout)view;
            tvTitle=(TextView)view.findViewById(R.id.tv_title);
            ivImage=(ImageView)view.findViewById(R.id.iv_image);
            tvTime=(TextView)view.findViewById(R.id.tv_time);
        }
    }

    public NewsAdapter(List<News> list)
    {
        newsList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        mContext=parent.getContext();
        View view= LayoutInflater.from(mContext).inflate(R.layout.news_item,parent,false);

        final ViewHolder viewHolder=new ViewHolder(view);
        //点击事件
        viewHolder.llNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int position=viewHolder.getAdapterPosition();
                News news=newsList.get(position);
                Intent intent=new Intent(mContext,NewsDetailActivity.class);
                intent.putExtra("next",news.getNext());
                mContext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position)
    {
        News news=newsList.get(position);
        holder.tvTitle.setText(news.getTitle());
        Glide.with(mContext).load(news.getImage()).into(holder.ivImage);
        //holder.ivImage.setImageResource(R.mipmap.ic_launcher);
        holder.tvTime.setText(news.getTime());
    }

    @Override
    public int getItemCount()
    {
        return newsList.size();
    }
}

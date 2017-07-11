package com.huabing.cyclist.bean;

import java.io.Serializable;

/**
 * Created by 30781 on 2017/7/10.
 */

public class Music implements Serializable {
    private int rank;
    private long songId;
    private String name;
    private String author;
    private String picUrl;
    private String songLink;

    public Music(int rank,long songId,String name,String author,String picUrl,String songLink)
    {
        this.rank=rank;
        this.songId=songId;
        this.name=name;
        this.author=author;
        this.picUrl=picUrl;
        this.songLink=songLink;
    }

    public void setRank(int rank){this.rank=rank;}
    public int getRank(){return rank;}
    public void setSongId(long songId)
    {
        this.songId=songId;
    }
    public long getSongId()
    {
        return songId;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public String getName()
    {
        return name;
    }
    public void setAuthor(String author)
    {
        this.author=author;
    }
    public String getAuthor()
    {
        return author;
    }
    public void setPicUrl(String picUrl)
    {
        this.picUrl=picUrl;
    }
    public String getPicUrl()
    {
        return picUrl;
    }
    public void setSongLink(String songLink)
    {
        this.songLink=songLink;
    }
    public String getSongLink()
    {
        return songLink;
    }
}

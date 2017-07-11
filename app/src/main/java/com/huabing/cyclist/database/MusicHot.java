package com.huabing.cyclist.database;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by 30781 on 2017/7/9.
 */

public class MusicHot extends DataSupport implements Serializable {
    private int rank;
    private long songId;
    private String name;
    private String author;
    private String picUrl;
    private String songLink;

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

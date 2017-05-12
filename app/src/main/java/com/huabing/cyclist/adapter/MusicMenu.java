package com.huabing.cyclist.adapter;

import java.io.Serializable;

/**
 * Created by 30781 on 2017/5/9.
 */

public class MusicMenu implements Serializable{
    int id;
    private String coverImage;
    private int comment;
    private String name;

    public MusicMenu(int id,String coverImage,int comment,String name)
    {
        this.id=id;
        this.coverImage=coverImage;
        this.comment=comment;
        this.name=name;
    }
    public void setId(int id){this.id=id;}
    public int getId(){return id;}
    public void setCoverImage(String coverImage)
    {
        this.coverImage=coverImage;
    }
    public String getCoverImage()
    {
        return coverImage;
    }
    public void setComment(int comment)
    {
        this.comment=comment;
    }
    public int getComment()
    {
        return comment;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public String getName()
    {
        return name;
    }
}

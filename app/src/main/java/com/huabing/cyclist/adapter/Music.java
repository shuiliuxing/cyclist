package com.huabing.cyclist.adapter;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by 30781 on 2017/5/7.
 */

public class Music extends DataSupport implements Serializable {
    private int num;
    private int id;
    private String name;
    private String author;
    private String pic;
    private String url;

    public Music(int num,int id,String name,String author,String pic,String url)
    {
        this.num=num;
        this.id=id;
        this.name=name;
        this.author=author;
        this.pic=pic;
        this.url=url;
    }
    public void setNum(int num){this.num=num;}
    public int getNum(){return  num;}
    public void setId(int id)
    {
        this.id=id;
    }
    public int getId()
    {
        return id;
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
    public void setPic(String pic)
    {
        this.pic=pic;
    }
    public String getPic()
    {
        return pic;
    }
    public void setUrl(String url)
    {
        this.url=url;
    }
    public String getUrl()
    {
        return url;
    }

}

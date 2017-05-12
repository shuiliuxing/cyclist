package com.huabing.cyclist.adapter;

/**
 * Created by 30781 on 2017/5/6.
 */

public class News {
    private String next;
    private String title;
    private String image;
    private String time;

    public News(String next, String title, String image, String time)
    {
        this.next=next;
        this.title=title;
        this.image=image;
        this.time=time;
    }

    public void setNext(String next)
    {
        this.next=next;
    }
    public void setTitle(String title)
    {
        this.title=title;
    }
    public void setImage(String image)
    {
        this.image=image;
    }
    public void setTime(String time)
    {
        this.time=time;
    }

    public String getNext()
    {
        return next;
    }
    public String getTitle()
    {
        return title;
    }
    public String getImage()
    {
        return image;
    }
    public String getTime()
    {
        return time;
    }
}

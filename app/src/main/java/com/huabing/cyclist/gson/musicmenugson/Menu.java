package com.huabing.cyclist.gson.musicmenugson;

/**
 * Created by 30781 on 2017/7/10.
 */

public class Menu {
    private long id;
    private String name;
    private String author;
    private String collect;
    private String hear;
    private String picUrl;
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() {
        return author;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }
    public String getCollect() {
        return collect;
    }

    public void setHear(String hear) {
        this.hear = hear;
    }
    public String getHear() {
        return hear;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    public String getPicUrl() {
        return picUrl;
    }
}

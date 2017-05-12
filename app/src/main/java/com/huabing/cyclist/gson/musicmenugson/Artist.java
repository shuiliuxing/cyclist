package com.huabing.cyclist.gson.musicmenugson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 30781 on 2017/5/10.
 */

public class Artist {
    private String name;
    private int id;
    @SerializedName("picId")
    private int picid;
    @SerializedName("img1v1Id")
    private int img1v1id;
    @SerializedName("briefDesc")
    private String briefdesc;
    @SerializedName("picUrl")
    private String picurl;
    @SerializedName("img1v1Url")
    private String img1v1url;
    @SerializedName("albumSize")
    private int albumsize;
    private List<String> alias;
    private String trans;
    @SerializedName("musicSize")
    private int musicsize;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setPicid(int picid) {
        this.picid = picid;
    }
    public int getPicid() {
        return picid;
    }

    public void setImg1v1id(int img1v1id) {
        this.img1v1id = img1v1id;
    }
    public int getImg1v1id() {
        return img1v1id;
    }

    public void setBriefdesc(String briefdesc) {
        this.briefdesc = briefdesc;
    }
    public String getBriefdesc() {
        return briefdesc;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }
    public String getPicurl() {
        return picurl;
    }

    public void setImg1v1url(String img1v1url) {
        this.img1v1url = img1v1url;
    }
    public String getImg1v1url() {
        return img1v1url;
    }

    public void setAlbumsize(int albumsize) {
        this.albumsize = albumsize;
    }
    public int getAlbumsize() {
        return albumsize;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }
    public List<String> getAlias() {
        return alias;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }
    public String getTrans() {
        return trans;
    }

    public void setMusicsize(int musicsize) {
        this.musicsize = musicsize;
    }
    public int getMusicsize() {
        return musicsize;
    }
}

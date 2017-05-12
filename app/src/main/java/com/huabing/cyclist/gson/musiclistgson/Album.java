package com.huabing.cyclist.gson.musiclistgson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 30781 on 2017/5/7.
 */

public class Album {
    private String name;
    private int id;
    private String type;
    private int size;
    @SerializedName("picId")
    private long picid;
    @SerializedName("blurPicUrl")
    private String blurpicurl;
    @SerializedName("companyId")
    private int companyid;
    private long pic;
    @SerializedName("picUrl")
    private String picurl;
    @SerializedName("publishTime")
    private long publishtime;
    private String description;
    private String tags;
    private String company;
    @SerializedName("briefDesc")
    private String briefdesc;
    private Artist artist;
    private List<String> songs;
    private List<String> alias;
    private int status;
    @SerializedName("copyrightId")
    private int copyrightid;
    @SerializedName("commentThreadId")
    private String commentthreadid;
    private List<Artists> artists;
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

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }

    public void setPicid(long picid) {
        this.picid = picid;
    }
    public long getPicid() {
        return picid;
    }

    public void setBlurpicurl(String blurpicurl) {
        this.blurpicurl = blurpicurl;
    }
    public String getBlurpicurl() {
        return blurpicurl;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }
    public int getCompanyid() {
        return companyid;
    }

    public void setPic(long pic) {
        this.pic = pic;
    }
    public long getPic() {
        return pic;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }
    public String getPicurl() {
        return picurl;
    }

    public void setPublishtime(long publishtime) {
        this.publishtime = publishtime;
    }
    public long getPublishtime() {
        return publishtime;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getTags() {
        return tags;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    public String getCompany() {
        return company;
    }

    public void setBriefdesc(String briefdesc) {
        this.briefdesc = briefdesc;
    }
    public String getBriefdesc() {
        return briefdesc;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
    public Artist getArtist() {
        return artist;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }
    public List<String> getSongs() {
        return songs;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }
    public List<String> getAlias() {
        return alias;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setCopyrightid(int copyrightid) {
        this.copyrightid = copyrightid;
    }
    public int getCopyrightid() {
        return copyrightid;
    }

    public void setCommentthreadid(String commentthreadid) {
        this.commentthreadid = commentthreadid;
    }
    public String getCommentthreadid() {
        return commentthreadid;
    }

    public void setArtists(List<Artists> artists) {
        this.artists = artists;
    }
    public List<Artists> getArtists() {
        return artists;
    }
}

package com.huabing.cyclist.gson.musicmenugson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 30781 on 2017/5/10.
 */

public class Hmusic {
    private String name;
    private int id;
    private int size;
    private String extension;
    private int sr;
    @SerializedName("dfsId")
    private long dfsid;
    private int bitrate;
    @SerializedName("playTime")
    private int playtime;
    @SerializedName("volumeDelta")
    private double volumedelta;
    @SerializedName("dfsId_str")
    private String dfsidStr;
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

    public void setSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    public String getExtension() {
        return extension;
    }

    public void setSr(int sr) {
        this.sr = sr;
    }
    public int getSr() {
        return sr;
    }

    public void setDfsid(long dfsid) {
        this.dfsid = dfsid;
    }
    public long getDfsid() {
        return dfsid;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }
    public int getBitrate() {
        return bitrate;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }
    public int getPlaytime() {
        return playtime;
    }

    public void setVolumedelta(double volumedelta) {
        this.volumedelta = volumedelta;
    }
    public double getVolumedelta() {
        return volumedelta;
    }

    public void setDfsidStr(String dfsidStr) {
        this.dfsidStr = dfsidStr;
    }
    public String getDfsidStr() {
        return dfsidStr;
    }
}

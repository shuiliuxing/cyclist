package com.huabing.cyclist.gson.musichotgson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 30781 on 2017/5/7.
 */

public class MMusic {
    private String name;
    private int id;
    private int size;
    private String extension;
    private int sr;
    private int dfsId;
    private int bitrate;
    private int playTime;
    private double volumeDelta;
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

    public void setDfsId(int dfsId) {
        this.dfsId = dfsId;
    }
    public int getDfsId() {
        return dfsId;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }
    public int getBitrate() {
        return bitrate;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }
    public int getPlayTime() {
        return playTime;
    }

    public void setVolumeDelta(double volumeDelta) {
        this.volumeDelta = volumeDelta;
    }
    public double getVolumeDelta() {
        return volumeDelta;
    }
}

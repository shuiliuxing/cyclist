package com.huabing.cyclist.gson.musiclrcgson;

/**
 * Created by 30781 on 2017/5/9.
 */

public class Lrc {
    private int version;
    private String lyric;
    public void setVersion(int version) {
        this.version = version;
    }
    public int getVersion() {
        return version;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }
    public String getLyric() {
        return lyric;
    }
}

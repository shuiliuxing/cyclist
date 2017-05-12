package com.huabing.cyclist.gson;

import com.huabing.cyclist.gson.musiclrcgson.Klyric;
import com.huabing.cyclist.gson.musiclrcgson.Lrc;
import com.huabing.cyclist.gson.musiclrcgson.Tlyric;

/**
 * Created by 30781 on 2017/5/9.
 */

public class MusicLrc {
    private boolean sgc;
    private boolean sfy;
    private boolean qfy;
    private Lrc lrc;
    private Klyric klyric;
    private Tlyric tlyric;
    private int code;
    public void setSgc(boolean sgc) {
        this.sgc = sgc;
    }
    public boolean getSgc() {
        return sgc;
    }

    public void setSfy(boolean sfy) {
        this.sfy = sfy;
    }
    public boolean getSfy() {
        return sfy;
    }

    public void setQfy(boolean qfy) {
        this.qfy = qfy;
    }
    public boolean getQfy() {
        return qfy;
    }

    public void setLrc(Lrc lrc) {
        this.lrc = lrc;
    }
    public Lrc getLrc() {
        return lrc;
    }

    public void setKlyric(Klyric klyric) {
        this.klyric = klyric;
    }
    public Klyric getKlyric() {
        return klyric;
    }

    public void setTlyric(Tlyric tlyric) {
        this.tlyric = tlyric;
    }
    public Tlyric getTlyric() {
        return tlyric;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}

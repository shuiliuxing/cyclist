package com.huabing.cyclist.gson.musiclistgson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 30781 on 2017/5/7.
 */

public class Tracks {
    private String name;
    private int id;
    private int position;
    private List<String> alias;
    private int status;
    private int fee;
    @SerializedName("copyrightId")
    private int copyrightid;
    private String disc;
    private int no;
    private List<Artists> artists;
    private Album album;
    private boolean starred;
    private int popularity;
    private int score;
    @SerializedName("starredNum")
    private int starrednum;
    private int duration;
    @SerializedName("playedNum")
    private int playednum;
    @SerializedName("dayPlays")
    private int dayplays;
    @SerializedName("hearTime")
    private int heartime;
    private String ringtone;
    private String crbt;
    private String audition;
    @SerializedName("copyFrom")
    private String copyfrom;
    @SerializedName("commentThreadId")
    private String commentthreadid;
    @SerializedName("rtUrl")
    private String rturl;
    private int ftype;
    @SerializedName("rtUrls")
    private List<String> rturls;
    @SerializedName("mp3Url")
    private String mp3url;
    private int rtype;
    private String rurl;
    private int mvid;
    @SerializedName("bMusic")
    private Bmusic bmusic;
    @SerializedName("hMusic")
    private Hmusic hmusic;
    @SerializedName("mMusic")
    private Mmusic mmusic;
    @SerializedName("lMusic")
    private Lmusic lmusic;
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

    public void setPosition(int position) {
        this.position = position;
    }
    public int getPosition() {
        return position;
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

    public void setFee(int fee) {
        this.fee = fee;
    }
    public int getFee() {
        return fee;
    }

    public void setCopyrightid(int copyrightid) {
        this.copyrightid = copyrightid;
    }
    public int getCopyrightid() {
        return copyrightid;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }
    public String getDisc() {
        return disc;
    }

    public void setNo(int no) {
        this.no = no;
    }
    public int getNo() {
        return no;
    }

    public void setArtists(List<Artists> artists) {
        this.artists = artists;
    }
    public List<Artists> getArtists() {
        return artists;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
    public Album getAlbum() {
        return album;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }
    public boolean getStarred() {
        return starred;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
    public int getPopularity() {
        return popularity;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }

    public void setStarrednum(int starrednum) {
        this.starrednum = starrednum;
    }
    public int getStarrednum() {
        return starrednum;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getDuration() {
        return duration;
    }

    public void setPlayednum(int playednum) {
        this.playednum = playednum;
    }
    public int getPlayednum() {
        return playednum;
    }

    public void setDayplays(int dayplays) {
        this.dayplays = dayplays;
    }
    public int getDayplays() {
        return dayplays;
    }

    public void setHeartime(int heartime) {
        this.heartime = heartime;
    }
    public int getHeartime() {
        return heartime;
    }

    public void setRingtone(String ringtone) {
        this.ringtone = ringtone;
    }
    public String getRingtone() {
        return ringtone;
    }

    public void setCrbt(String crbt) {
        this.crbt = crbt;
    }
    public String getCrbt() {
        return crbt;
    }

    public void setAudition(String audition) {
        this.audition = audition;
    }
    public String getAudition() {
        return audition;
    }

    public void setCopyfrom(String copyfrom) {
        this.copyfrom = copyfrom;
    }
    public String getCopyfrom() {
        return copyfrom;
    }

    public void setCommentthreadid(String commentthreadid) {
        this.commentthreadid = commentthreadid;
    }
    public String getCommentthreadid() {
        return commentthreadid;
    }

    public void setRturl(String rturl) {
        this.rturl = rturl;
    }
    public String getRturl() {
        return rturl;
    }

    public void setFtype(int ftype) {
        this.ftype = ftype;
    }
    public int getFtype() {
        return ftype;
    }

    public void setRturls(List<String> rturls) {
        this.rturls = rturls;
    }
    public List<String> getRturls() {
        return rturls;
    }

    public void setMp3url(String mp3url) {
        this.mp3url = mp3url;
    }
    public String getMp3url() {
        return mp3url;
    }

    public void setRtype(int rtype) {
        this.rtype = rtype;
    }
    public int getRtype() {
        return rtype;
    }

    public void setRurl(String rurl) {
        this.rurl = rurl;
    }
    public String getRurl() {
        return rurl;
    }

    public void setMvid(int mvid) {
        this.mvid = mvid;
    }
    public int getMvid() {
        return mvid;
    }

    public void setBmusic(Bmusic bmusic) {
        this.bmusic = bmusic;
    }
    public Bmusic getBmusic() {
        return bmusic;
    }

    public void setHmusic(Hmusic hmusic) {
        this.hmusic = hmusic;
    }
    public Hmusic getHmusic() {
        return hmusic;
    }

    public void setMmusic(Mmusic mmusic) {
        this.mmusic = mmusic;
    }
    public Mmusic getMmusic() {
        return mmusic;
    }

    public void setLmusic(Lmusic lmusic) {
        this.lmusic = lmusic;
    }
    public Lmusic getLmusic() {
        return lmusic;
    }
}

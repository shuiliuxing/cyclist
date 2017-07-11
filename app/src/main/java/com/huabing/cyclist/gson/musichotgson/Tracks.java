package com.huabing.cyclist.gson.musichotgson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 30781 on 2017/5/7.
 */

public class Tracks {
    private String name;
    private long id;
    private int position;
    private List<String> alias;
    private int status;
    private int fee;
    private int copyrightId;
    private String disc;
    private int no;
    private List<Artists> artists;
    private Album album;
    private boolean starred;
    private int popularity;
    private int score;
    private int starredNum;
    private int duration;
    private int playedNum;
    private int dayPlays;
    private int hearTime;
    private String ringtone;
    private String crbt;
    private String audition;
    private String copyFrom;
    private String commentThreadId;
    private String rtUrl;
    private int ftype;
    private List<String> rtUrls;
    private int copyright;
    private int mvid;
    private BMusic bMusic;
    private String mp3Url;
    private int rtype;
    private String rurl;
    private HMusic hMusic;
    private MMusic mMusic;
    private LMusic lMusic;
    private int lastRank;


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
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

    public void setCopyrightId(int copyrightId) {
        this.copyrightId = copyrightId;
    }
    public int getCopyrightId() {
        return copyrightId;
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

    public void setStarredNum(int starredNum) {
        this.starredNum = starredNum;
    }
    public int getStarredNum() {
        return starredNum;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getDuration() {
        return duration;
    }

    public void setPlayedNum(int playedNum) {
        this.playedNum = playedNum;
    }
    public int getPlayedNum() {
        return playedNum;
    }

    public void setDayPlays(int dayPlays) {
        this.dayPlays = dayPlays;
    }
    public int getDayPlays() {
        return dayPlays;
    }

    public void setHearTime(int hearTime) {
        this.hearTime = hearTime;
    }
    public int getHearTime() {
        return hearTime;
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

    public void setCopyFrom(String copyFrom) {
        this.copyFrom = copyFrom;
    }
    public String getCopyFrom() {
        return copyFrom;
    }

    public void setCommentThreadId(String commentThreadId) {
        this.commentThreadId = commentThreadId;
    }
    public String getCommentThreadId() {
        return commentThreadId;
    }

    public void setRtUrl(String rtUrl) {
        this.rtUrl = rtUrl;
    }
    public String getRtUrl() {
        return rtUrl;
    }

    public void setFtype(int ftype) {
        this.ftype = ftype;
    }
    public int getFtype() {
        return ftype;
    }

    public void setRtUrls(List<String> rtUrls) {
        this.rtUrls = rtUrls;
    }
    public List<String> getRtUrls() {
        return rtUrls;
    }

    public void setCopyright(int copyright) {
        this.copyright = copyright;
    }
    public int getCopyright() {
        return copyright;
    }

    public void setMvid(int mvid) {
        this.mvid = mvid;
    }
    public int getMvid() {
        return mvid;
    }

    public void setBMusic(BMusic bMusic) {
        this.bMusic = bMusic;
    }
    public BMusic getBMusic() {
        return bMusic;
    }

    public void setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
    }
    public String getMp3Url() {
        return mp3Url;
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

    public void setHMusic(HMusic hMusic) {
        this.hMusic = hMusic;
    }
    public HMusic getHMusic() {
        return hMusic;
    }

    public void setMMusic(MMusic mMusic) {
        this.mMusic = mMusic;
    }
    public MMusic getMMusic() {
        return mMusic;
    }

    public void setLMusic(LMusic lMusic) {
        this.lMusic = lMusic;
    }
    public LMusic getLMusic() {
        return lMusic;
    }

    public void setLastRank(int lastRank) {
        this.lastRank = lastRank;
    }
    public int getLastRank() {
        return lastRank;
    }
}

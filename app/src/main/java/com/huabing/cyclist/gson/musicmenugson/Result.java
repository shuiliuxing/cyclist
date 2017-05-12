package com.huabing.cyclist.gson.musicmenugson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 30781 on 2017/5/10.
 */

public class Result {
    private List<String> subscribers;
    private boolean subscribed;
    private Creator creator;
    private String artists;
    private List<Tracks> tracks;
    private List<String> tags;
    private boolean ordered;
    @SerializedName("trackUpdateTime")
    private long trackupdatetime;
    @SerializedName("highQuality")
    private boolean highquality;
    @SerializedName("specialType")
    private int specialtype;
    private boolean anonimous;
    @SerializedName("userId")
    private int userid;
    private int privacy;
    @SerializedName("newImported")
    private boolean newimported;
    @SerializedName("cloudTrackCount")
    private int cloudtrackcount;
    @SerializedName("subscribedCount")
    private int subscribedcount;
    @SerializedName("trackCount")
    private int trackcount;
    @SerializedName("createTime")
    private long createtime;
    @SerializedName("coverImgId")
    private long coverimgid;
    @SerializedName("playCount")
    private int playcount;
    @SerializedName("totalDuration")
    private int totalduration;
    @SerializedName("coverImgUrl")
    private String coverimgurl;
    @SerializedName("adType")
    private int adtype;
    @SerializedName("trackNumberUpdateTime")
    private long tracknumberupdatetime;
    private String description;
    private int status;
    @SerializedName("updateTime")
    private long updatetime;
    @SerializedName("commentThreadId")
    private String commentthreadid;
    private String name;
    private int id;
    @SerializedName("shareCount")
    private int sharecount;
    @SerializedName("commentCount")
    private int commentcount;
    public void setSubscribers(List<String> subscribers) {
        this.subscribers = subscribers;
    }
    public List<String> getSubscribers() {
        return subscribers;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
    public boolean getSubscribed() {
        return subscribed;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }
    public Creator getCreator() {
        return creator;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }
    public String getArtists() {
        return artists;
    }

    public void setTracks(List<Tracks> tracks) {
        this.tracks = tracks;
    }
    public List<Tracks> getTracks() {
        return tracks;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public List<String> getTags() {
        return tags;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }
    public boolean getOrdered() {
        return ordered;
    }

    public void setTrackupdatetime(long trackupdatetime) {
        this.trackupdatetime = trackupdatetime;
    }
    public long getTrackupdatetime() {
        return trackupdatetime;
    }

    public void setHighquality(boolean highquality) {
        this.highquality = highquality;
    }
    public boolean getHighquality() {
        return highquality;
    }

    public void setSpecialtype(int specialtype) {
        this.specialtype = specialtype;
    }
    public int getSpecialtype() {
        return specialtype;
    }

    public void setAnonimous(boolean anonimous) {
        this.anonimous = anonimous;
    }
    public boolean getAnonimous() {
        return anonimous;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
    public int getUserid() {
        return userid;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }
    public int getPrivacy() {
        return privacy;
    }

    public void setNewimported(boolean newimported) {
        this.newimported = newimported;
    }
    public boolean getNewimported() {
        return newimported;
    }

    public void setCloudtrackcount(int cloudtrackcount) {
        this.cloudtrackcount = cloudtrackcount;
    }
    public int getCloudtrackcount() {
        return cloudtrackcount;
    }

    public void setSubscribedcount(int subscribedcount) {
        this.subscribedcount = subscribedcount;
    }
    public int getSubscribedcount() {
        return subscribedcount;
    }

    public void setTrackcount(int trackcount) {
        this.trackcount = trackcount;
    }
    public int getTrackcount() {
        return trackcount;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
    public long getCreatetime() {
        return createtime;
    }

    public void setCoverimgid(long coverimgid) {
        this.coverimgid = coverimgid;
    }
    public long getCoverimgid() {
        return coverimgid;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }
    public int getPlaycount() {
        return playcount;
    }

    public void setTotalduration(int totalduration) {
        this.totalduration = totalduration;
    }
    public int getTotalduration() {
        return totalduration;
    }

    public void setCoverimgurl(String coverimgurl) {
        this.coverimgurl = coverimgurl;
    }
    public String getCoverimgurl() {
        return coverimgurl;
    }

    public void setAdtype(int adtype) {
        this.adtype = adtype;
    }
    public int getAdtype() {
        return adtype;
    }

    public void setTracknumberupdatetime(long tracknumberupdatetime) {
        this.tracknumberupdatetime = tracknumberupdatetime;
    }
    public long getTracknumberupdatetime() {
        return tracknumberupdatetime;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }
    public long getUpdatetime() {
        return updatetime;
    }

    public void setCommentthreadid(String commentthreadid) {
        this.commentthreadid = commentthreadid;
    }
    public String getCommentthreadid() {
        return commentthreadid;
    }

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

    public void setSharecount(int sharecount) {
        this.sharecount = sharecount;
    }
    public int getSharecount() {
        return sharecount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }
    public int getCommentcount() {
        return commentcount;
    }
}

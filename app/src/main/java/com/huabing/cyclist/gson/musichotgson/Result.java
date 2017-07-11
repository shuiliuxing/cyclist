package com.huabing.cyclist.gson.musichotgson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 30781 on 2017/5/7.
 */

public class Result {
    private List<String> subscribers;
    private boolean subscribed;
    private Creator creator;
    private String artists;
    private List<Tracks> tracks;
    private long trackNumberUpdateTime;
    private int adType;
    private int subscribedCount;
    private int cloudTrackCount;
    private int status;
    private int specialType;
    private boolean anonimous;
    private long trackUpdateTime;
    private long coverImgId;
    private long createTime;
    private int privacy;
    private boolean newImported;
    private boolean highQuality;
    private long updateTime;
    private String commentThreadId;
    private int trackCount;
    private String coverImgUrl;
    private int userId;
    private int playCount;
    private int totalDuration;
    private boolean ordered;
    private List<String> tags;
    private String description;
    private String name;
    private int id;
    private int shareCount;
    private String coverImgId_str;
    private String ToplistType;
    private int commentCount;


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

    public void setTrackNumberUpdateTime(long trackNumberUpdateTime) {
        this.trackNumberUpdateTime = trackNumberUpdateTime;
    }
    public long getTrackNumberUpdateTime() {
        return trackNumberUpdateTime;
    }

    public void setAdType(int adType) {
        this.adType = adType;
    }
    public int getAdType() {
        return adType;
    }

    public void setSubscribedCount(int subscribedCount) {
        this.subscribedCount = subscribedCount;
    }
    public int getSubscribedCount() {
        return subscribedCount;
    }

    public void setCloudTrackCount(int cloudTrackCount) {
        this.cloudTrackCount = cloudTrackCount;
    }
    public int getCloudTrackCount() {
        return cloudTrackCount;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setSpecialType(int specialType) {
        this.specialType = specialType;
    }
    public int getSpecialType() {
        return specialType;
    }

    public void setAnonimous(boolean anonimous) {
        this.anonimous = anonimous;
    }
    public boolean getAnonimous() {
        return anonimous;
    }

    public void setTrackUpdateTime(long trackUpdateTime) {
        this.trackUpdateTime = trackUpdateTime;
    }
    public long getTrackUpdateTime() {
        return trackUpdateTime;
    }

    public void setCoverImgId(long coverImgId) {
        this.coverImgId = coverImgId;
    }
    public long getCoverImgId() {
        return coverImgId;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    public long getCreateTime() {
        return createTime;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }
    public int getPrivacy() {
        return privacy;
    }

    public void setNewImported(boolean newImported) {
        this.newImported = newImported;
    }
    public boolean getNewImported() {
        return newImported;
    }

    public void setHighQuality(boolean highQuality) {
        this.highQuality = highQuality;
    }
    public boolean getHighQuality() {
        return highQuality;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
    public long getUpdateTime() {
        return updateTime;
    }

    public void setCommentThreadId(String commentThreadId) {
        this.commentThreadId = commentThreadId;
    }
    public String getCommentThreadId() {
        return commentThreadId;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }
    public int getTrackCount() {
        return trackCount;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }
    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }
    public int getPlayCount() {
        return playCount;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }
    public int getTotalDuration() {
        return totalDuration;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }
    public boolean getOrdered() {
        return ordered;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public List<String> getTags() {
        return tags;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
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

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }
    public int getShareCount() {
        return shareCount;
    }

    public void setCoverImgId_str(String coverImgId_str) {
        this.coverImgId_str = coverImgId_str;
    }
    public String getCoverImgId_str() {
        return coverImgId_str;
    }

    public void setToplistType(String ToplistType) {
        this.ToplistType = ToplistType;
    }
    public String getToplistType() {
        return ToplistType;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
    public int getCommentCount() {
        return commentCount;
    }
}

package com.huabing.cyclist.gson.musiclistgson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 30781 on 2017/5/7.
 */

public class Creator {
    @SerializedName("defaultAvatar")
    private boolean defaultavatar;
    private int province;
    @SerializedName("authStatus")
    private int authstatus;
    private boolean followed;
    @SerializedName("avatarUrl")
    private String avatarurl;
    @SerializedName("accountStatus")
    private int accountstatus;
    private int gender;
    private int city;
    private long birthday;
    @SerializedName("userId")
    private int userid;
    @SerializedName("userType")
    private int usertype;
    private String nickname;
    private String signature;
    private String description;
    @SerializedName("detailDescription")
    private String detaildescription;
    @SerializedName("avatarImgId")
    private long avatarimgid;
    @SerializedName("backgroundImgId")
    private long backgroundimgid;
    @SerializedName("backgroundUrl")
    private String backgroundurl;
    private int authority;
    private boolean mutual;
    @SerializedName("expertTags")
    private String experttags;
    @SerializedName("djStatus")
    private int djstatus;
    @SerializedName("vipType")
    private int viptype;
    @SerializedName("remarkName")
    private String remarkname;
    @SerializedName("backgroundImgIdStr")
    private String backgroundimgidstr;
    @SerializedName("avatarImgIdStr")
    private String avatarimgidstr;
    public void setDefaultavatar(boolean defaultavatar) {
        this.defaultavatar = defaultavatar;
    }
    public boolean getDefaultavatar() {
        return defaultavatar;
    }

    public void setProvince(int province) {
        this.province = province;
    }
    public int getProvince() {
        return province;
    }

    public void setAuthstatus(int authstatus) {
        this.authstatus = authstatus;
    }
    public int getAuthstatus() {
        return authstatus;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }
    public boolean getFollowed() {
        return followed;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }
    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAccountstatus(int accountstatus) {
        this.accountstatus = accountstatus;
    }
    public int getAccountstatus() {
        return accountstatus;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
    public int getGender() {
        return gender;
    }

    public void setCity(int city) {
        this.city = city;
    }
    public int getCity() {
        return city;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }
    public long getBirthday() {
        return birthday;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
    public int getUserid() {
        return userid;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }
    public int getUsertype() {
        return usertype;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    public String getSignature() {
        return signature;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setDetaildescription(String detaildescription) {
        this.detaildescription = detaildescription;
    }
    public String getDetaildescription() {
        return detaildescription;
    }

    public void setAvatarimgid(long avatarimgid) {
        this.avatarimgid = avatarimgid;
    }
    public long getAvatarimgid() {
        return avatarimgid;
    }

    public void setBackgroundimgid(long backgroundimgid) {
        this.backgroundimgid = backgroundimgid;
    }
    public long getBackgroundimgid() {
        return backgroundimgid;
    }

    public void setBackgroundurl(String backgroundurl) {
        this.backgroundurl = backgroundurl;
    }
    public String getBackgroundurl() {
        return backgroundurl;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }
    public int getAuthority() {
        return authority;
    }

    public void setMutual(boolean mutual) {
        this.mutual = mutual;
    }
    public boolean getMutual() {
        return mutual;
    }

    public void setExperttags(String experttags) {
        this.experttags = experttags;
    }
    public String getExperttags() {
        return experttags;
    }

    public void setDjstatus(int djstatus) {
        this.djstatus = djstatus;
    }
    public int getDjstatus() {
        return djstatus;
    }

    public void setViptype(int viptype) {
        this.viptype = viptype;
    }
    public int getViptype() {
        return viptype;
    }

    public void setRemarkname(String remarkname) {
        this.remarkname = remarkname;
    }
    public String getRemarkname() {
        return remarkname;
    }

    public void setBackgroundimgidstr(String backgroundimgidstr) {
        this.backgroundimgidstr = backgroundimgidstr;
    }
    public String getBackgroundimgidstr() {
        return backgroundimgidstr;
    }

    public void setAvatarimgidstr(String avatarimgidstr) {
        this.avatarimgidstr = avatarimgidstr;
    }
    public String getAvatarimgidstr() {
        return avatarimgidstr;
    }
}

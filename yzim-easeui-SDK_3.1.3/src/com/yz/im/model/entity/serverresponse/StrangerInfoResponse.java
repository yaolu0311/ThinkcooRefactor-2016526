package com.yz.im.model.entity.serverresponse;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cys on 2016/7/28
 */
public class StrangerInfoResponse implements Parcelable {

    /**
     * school : null
     * userId : 20018
     * imager : http://www.thinkcoo.com/yingzi-mobile/upload/personal/headportrait/20160513145451936.JPG
     * sex : 女
     * blacklist : 0
     * beBlack : 0
     * shield : 0
     * queryNum : 0
     * isVerify : 0
     * stick : 0
     * disturb : 0
     * name : 冯云娣
     */

    private String school;
    private String userId;
    private String imager;
    private String sex;
    private String blacklist;
    private String beBlack;
    private String shield;
    private String queryNum;
    private String isVerify;
    private String stick;
    private String disturb;
    private String name;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImager() {
        return imager;
    }

    public void setImager(String imager) {
        this.imager = imager;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }

    public String getBeBlack() {
        return beBlack;
    }

    public void setBeBlack(String beBlack) {
        this.beBlack = beBlack;
    }

    public String getShield() {
        return shield;
    }

    public void setShield(String shield) {
        this.shield = shield;
    }

    public String getQueryNum() {
        return queryNum;
    }

    public void setQueryNum(String queryNum) {
        this.queryNum = queryNum;
    }

    public String getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(String isVerify) {
        this.isVerify = isVerify;
    }

    public String getStick() {
        return stick;
    }

    public void setStick(String stick) {
        this.stick = stick;
    }

    public String getDisturb() {
        return disturb;
    }

    public void setDisturb(String disturb) {
        this.disturb = disturb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.school);
        dest.writeString(this.userId);
        dest.writeString(this.imager);
        dest.writeString(this.sex);
        dest.writeString(this.blacklist);
        dest.writeString(this.beBlack);
        dest.writeString(this.shield);
        dest.writeString(this.queryNum);
        dest.writeString(this.isVerify);
        dest.writeString(this.stick);
        dest.writeString(this.disturb);
        dest.writeString(this.name);
    }

    public StrangerInfoResponse() {
    }

    protected StrangerInfoResponse(Parcel in) {
        this.school = in.readString();
        this.userId = in.readString();
        this.imager = in.readString();
        this.sex = in.readString();
        this.blacklist = in.readString();
        this.beBlack = in.readString();
        this.shield = in.readString();
        this.queryNum = in.readString();
        this.isVerify = in.readString();
        this.stick = in.readString();
        this.disturb = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<StrangerInfoResponse> CREATOR = new Parcelable.Creator<StrangerInfoResponse>() {
        @Override
        public StrangerInfoResponse createFromParcel(Parcel source) {
            return new StrangerInfoResponse(source);
        }

        @Override
        public StrangerInfoResponse[] newArray(int size) {
            return new StrangerInfoResponse[size];
        }
    };
}

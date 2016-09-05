package com.yz.im.model.entity.serverresponse;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cys on 2016/8/1
 */
public class FindUserResponse implements Parcelable {

    /**
     * userId : 10017
     * school : null
     * imager : http://117.34.109.231/yingzi-mobile/upload/personal/headportrait/20160527094415637.jpg
     * sex : 男
     * shield : 0
     * isVerify : 0
     * isFriends : 1
     * userName : 18700182159
     * name : 崔永胜
     */

    private String userId;
    private String school;
    private String imager;
    private String sex;
    private String shield;
    private String isVerify;
    private String isFriends;
    private String userName;
    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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

    public String getShield() {
        return shield;
    }

    public void setShield(String shield) {
        this.shield = shield;
    }

    public String getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(String isVerify) {
        this.isVerify = isVerify;
    }

    public String getIsFriends() {
        return isFriends;
    }

    public void setIsFriends(String isFriends) {
        this.isFriends = isFriends;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        dest.writeString(this.userId);
        dest.writeString(this.school);
        dest.writeString(this.imager);
        dest.writeString(this.sex);
        dest.writeString(this.shield);
        dest.writeString(this.isVerify);
        dest.writeString(this.isFriends);
        dest.writeString(this.userName);
        dest.writeString(this.name);
    }

    public FindUserResponse() {
    }

    protected FindUserResponse(Parcel in) {
        this.userId = in.readString();
        this.school = in.readString();
        this.imager = in.readString();
        this.sex = in.readString();
        this.shield = in.readString();
        this.isVerify = in.readString();
        this.isFriends = in.readString();
        this.userName = in.readString();
        this.name = in.readString();
    }

    public static final Creator<FindUserResponse> CREATOR = new Creator<FindUserResponse>() {
        @Override
        public FindUserResponse createFromParcel(Parcel source) {
            return new FindUserResponse(source);
        }

        @Override
        public FindUserResponse[] newArray(int size) {
            return new FindUserResponse[size];
        }
    };
}

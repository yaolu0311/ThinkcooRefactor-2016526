package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/28.
 */
public class ScheduleCreateUser implements Parcelable {


    public static ScheduleCreateUser createByUser(User user){
        return new ScheduleCreateUser(user.getUserId(),user.getName());
    }

    private String userId;  // accountId
    private String userName;

    public ScheduleCreateUser(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public ScheduleCreateUser() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleCreateUser that = (ScheduleCreateUser) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return userName != null ? userName.equals(that.userName) : that.userName == null;

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.userName);
    }

    protected ScheduleCreateUser(Parcel in) {
        this.userId = in.readString();
        this.userName = in.readString();
    }

    public static final Parcelable.Creator<ScheduleCreateUser> CREATOR = new Parcelable.Creator<ScheduleCreateUser>() {
        @Override
        public ScheduleCreateUser createFromParcel(Parcel source) {
            return new ScheduleCreateUser(source);
        }

        @Override
        public ScheduleCreateUser[] newArray(int size) {
            return new ScheduleCreateUser[size];
        }
    };
}

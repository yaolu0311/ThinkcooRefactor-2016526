package com.yz.im.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cys on 2016/8/19
 */
public class RockEntity implements Parcelable {

    private String uuid;
    private String msg;
    private String type;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.msg);
        dest.writeString(this.type);
    }

    public RockEntity() {
    }

    protected RockEntity(Parcel in) {
        this.uuid = in.readString();
        this.msg = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<RockEntity> CREATOR = new Parcelable.Creator<RockEntity>() {
        @Override
        public RockEntity createFromParcel(Parcel source) {
            return new RockEntity(source);
        }

        @Override
        public RockEntity[] newArray(int size) {
            return new RockEntity[size];
        }
    };
}

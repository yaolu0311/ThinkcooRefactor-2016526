package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkcoo.mobile.model.entity.nullentities.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/5/19.
 */
@Singleton
public class License implements Nullable, Parcelable {

    String content;
    String url;
    boolean isAgree = true;

    public License() {
    }

    @Inject
    public License(String url, String content) {
        this.url = url;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isAgree() {
        return isAgree;
    }

    public void setAgree(boolean agree) {
        isAgree = agree;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeString(this.url);
        dest.writeByte(this.isAgree ? (byte) 1 : (byte) 0);
    }

    protected License(Parcel in) {
        this.content = in.readString();
        this.url = in.readString();
        this.isAgree = in.readByte() != 0;
    }

    public static final Parcelable.Creator<License> CREATOR = new Parcelable.Creator<License>() {
        @Override
        public License createFromParcel(Parcel source) {
            return new License(source);
        }

        @Override
        public License[] newArray(int size) {
            return new License[size];
        }
    };
}

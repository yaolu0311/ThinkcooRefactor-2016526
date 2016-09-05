package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkcoo.mobile.model.entity.serverresponse.HomeImageBean;
import com.yzkj.android.orm.annotation.Column;
import com.yzkj.android.orm.annotation.Table;

/**
 * Created by Robert.yao on 2016/7/18.
 */
@Table(name = "banner")
public class Banner implements Parcelable{

    public static Banner create(HomeImageBean homeImageBean){
        Banner banner = new Banner();
        banner.setH5Url(homeImageBean.getLinkUrl());
        banner.setImageUrl(homeImageBean.getPicPath());
        banner.setId(homeImageBean.getAdCode());
        return banner;
    }

    @Column(name = "id" , isId = true)
    private String id;
    @Column(name = "h5Url")
    private String h5Url;
    @Column(name = "imageUrl")
    private String imageUrl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.h5Url);
        dest.writeString(this.imageUrl);
    }

    public Banner() {
    }

    protected Banner(Parcel in) {
        this.id = in.readString();
        this.h5Url = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel source) {
            return new Banner(source);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };
}

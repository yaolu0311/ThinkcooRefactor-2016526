package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert.yao on 2016/8/10.
 */
public class MyGoodsDetail implements Parcelable{

    List<GoodsPhoto> goodsPhotoList;
    private String quality;
    private String introduce;
    private SchoolLocation location;
    private String filePaths;

    public List<GoodsPhoto> getGoodsPhotoList() {
        return goodsPhotoList;
    }

    public void setGoodsPhotoList(List<GoodsPhoto> goodsPhotoList) {
        this.goodsPhotoList = goodsPhotoList;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public SchoolLocation getLocation() {
        return location;
    }

    public void setLocation(SchoolLocation location) {
        this.location = location;
    }

    public String getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(String filePaths) {
        this.filePaths = filePaths;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.goodsPhotoList);
        dest.writeString(this.quality);
        dest.writeString(this.introduce);
        dest.writeParcelable(this.location, flags);
        dest.writeString(this.filePaths);
    }

    public MyGoodsDetail() {
        goodsPhotoList = new ArrayList<>();
    }

    protected MyGoodsDetail(Parcel in) {
        this.goodsPhotoList = in.createTypedArrayList(GoodsPhoto.CREATOR);
        this.quality = in.readString();
        this.introduce = in.readString();
        this.location = in.readParcelable(SchoolLocation.class.getClassLoader());
        this.filePaths = in.readString();
    }

    public static final Creator<MyGoodsDetail> CREATOR = new Creator<MyGoodsDetail>() {
        @Override
        public MyGoodsDetail createFromParcel(Parcel source) {
            return new MyGoodsDetail(source);
        }

        @Override
        public MyGoodsDetail[] newArray(int size) {
            return new MyGoodsDetail[size];
        }
    };
}

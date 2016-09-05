package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by Robert.yao on 2016/8/10.
 */
public class GoodsPhoto implements ThinkCooPhoto ,Parcelable{

    public static final String PLUS_SIGN_PHOTO_ID = "-1";

    public static GoodsPhoto createLocal(String filePath){
        GoodsPhoto goodsPhoto = new GoodsPhoto();
        goodsPhoto.setFromServer(false);
        goodsPhoto.setFilePath(filePath);
        return goodsPhoto;
    }

    public static GoodsPhoto createServer(String id,String url){
        GoodsPhoto goodsPhoto = new GoodsPhoto();
        goodsPhoto.setFromServer(true);
        goodsPhoto.setPhotoId(id);
        goodsPhoto.setPhotoUrl(url);
        return goodsPhoto;
    }

    public static GoodsPhoto createPlusSign() {
        GoodsPhoto goodsPhoto = new GoodsPhoto();
        goodsPhoto.setFromServer(false);
        goodsPhoto.setPhotoId(PLUS_SIGN_PHOTO_ID);
        return goodsPhoto;
    }

    private GoodsPhoto() {}

    private String photoId;
    private boolean fromServer;
    private String filePath;
    private String photoUrl;

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public boolean isFromServer() {
        return fromServer;
    }

    public void setFromServer(boolean fromServer) {
        this.fromServer = fromServer;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    @Override
    public String getWorkPhotoPath(){
        return TextUtils.isEmpty(filePath) ? photoUrl : filePath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.photoId);
        dest.writeByte(this.fromServer ? (byte) 1 : (byte) 0);
        dest.writeString(this.filePath);
        dest.writeString(this.photoUrl);
    }

    protected GoodsPhoto(Parcel in) {
        this.photoId = in.readString();
        this.fromServer = in.readByte() != 0;
        this.filePath = in.readString();
        this.photoUrl = in.readString();
    }

    public static final Creator<GoodsPhoto> CREATOR = new Creator<GoodsPhoto>() {
        @Override
        public GoodsPhoto createFromParcel(Parcel source) {
            return new GoodsPhoto(source);
        }

        @Override
        public GoodsPhoto[] newArray(int size) {
            return new GoodsPhoto[size];
        }
    };
}

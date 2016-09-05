package com.yz.im.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cys on 2016/7/27
 * 排序实体类
 */
public class BaseSortEntity implements Parcelable, Comparable{


    private boolean isCheck;
    private boolean canToggle = true;
    private String nameLetters;
    private String firstLetter;
    private String showName;

    public BaseSortEntity() {
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getNameLetters() {
        return nameLetters;
    }

    public void setNameLetters(String nameLetters) {
        this.nameLetters = nameLetters;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public boolean isCanToggle() {
        return canToggle;
    }

    public void setCanToggle(boolean canToggle) {
        this.canToggle = canToggle;
    }

    @Override
    public int compareTo(Object another) {

        if (another == null) {
            return -1;
        }
        if (!(another instanceof BaseSortEntity)) {
            return -1;
        }

        BaseSortEntity entity = (BaseSortEntity) another;

        if (entity.getFirstLetter().equals("#")) {
            return -1;
        } else if (this.getFirstLetter().equals("#")) {
            return 1;
        } else {
            return this.getNameLetters().compareTo(entity.getNameLetters());
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
        dest.writeString(this.nameLetters);
        dest.writeString(this.firstLetter);
        dest.writeString(this.showName);
    }

    protected BaseSortEntity(Parcel in) {
        this.isCheck = in.readByte() != 0;
        this.nameLetters = in.readString();
        this.firstLetter = in.readString();
        this.showName = in.readString();
    }

}

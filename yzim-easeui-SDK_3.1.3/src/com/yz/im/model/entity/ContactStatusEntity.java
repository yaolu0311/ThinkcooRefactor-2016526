package com.yz.im.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cys on 2016/7/27
 */
public class ContactStatusEntity implements Parcelable {

    public static final int LEFT_CHECK_BOX = 0X0003;
    public static final int RIGHT_CHECK_BOX = 0X0002;
    public static final int NONE_CHECK_BOX = 0X0001;

    private boolean isSort = true;  //是否排序
    private boolean isShowSearchView = true;
    private boolean isGroupMember;
    private boolean isDelete;
    private int status_checkbox = NONE_CHECK_BOX;

    public ContactStatusEntity() {}

    public boolean isSort() {
        return isSort;
    }

    public void setSort(boolean sort) {
        isSort = sort;
    }

    public boolean isShowSearchView() {
        return isShowSearchView;
    }

    public void setShowSearchView(boolean showSearchView) {
        isShowSearchView = showSearchView;
    }

    public boolean isGroupMember() {
        return isGroupMember;
    }

    public void setGroupMember(boolean groupMember) {
        isGroupMember = groupMember;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public int getStatus_checkbox() {
        return status_checkbox;
    }

    public void setStatus_checkbox(int status_checkbox) {
        this.status_checkbox = status_checkbox;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isGroupMember ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isDelete ? (byte) 1 : (byte) 0);
        dest.writeInt(this.status_checkbox);
    }

    protected ContactStatusEntity(Parcel in) {
        this.isGroupMember = in.readByte() != 0;
        this.isDelete = in.readByte() != 0;
        this.status_checkbox = in.readInt();
    }

    public static final Creator<ContactStatusEntity> CREATOR = new Creator<ContactStatusEntity>() {
        @Override
        public ContactStatusEntity createFromParcel(Parcel source) {
            return new ContactStatusEntity(source);
        }

        @Override
        public ContactStatusEntity[] newArray(int size) {
            return new ContactStatusEntity[size];
        }
    };
}

package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/16.
 */
public class IndustryItemEntity extends DataDictionary implements Parcelable{

    private boolean isChecked;

    public IndustryItemEntity(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected IndustryItemEntity(Parcel in) {
        super(in);
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<IndustryItemEntity> CREATOR = new Creator<IndustryItemEntity>() {
        @Override
        public IndustryItemEntity createFromParcel(Parcel source) {
            return new IndustryItemEntity(source);
        }

        @Override
        public IndustryItemEntity[] newArray(int size) {
            return new IndustryItemEntity[size];
        }
    };
}

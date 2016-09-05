package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkcoo.mobile.model.entity.nullentities.Nullable;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  10:45
 */
public class Area extends Address implements Nullable ,Parcelable{

    public Area(String name, int code) {
        super(name,code);
    }

    public Area() {
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
        super.writeToParcel(dest, flags);
    }

    protected Area(Parcel in) {
        super(in);
    }

    public static final Creator<Area> CREATOR = new Creator<Area>() {
        @Override
        public Area createFromParcel(Parcel source) {
            return new Area(source);
        }

        @Override
        public Area[] newArray(int size) {
            return new Area[size];
        }
    };
}

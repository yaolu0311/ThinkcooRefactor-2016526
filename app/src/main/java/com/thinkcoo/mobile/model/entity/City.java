package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkcoo.mobile.model.entity.nullentities.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  10:42
 */
public class City extends Address implements Nullable, Parcelable {

    public City(String name , int code) {
        super(name,code);
    }

    public City() {
    }

    private  List<Area> areaList = new ArrayList<>();

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
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
        dest.writeTypedList(this.areaList);
    }

    protected City(Parcel in) {
        this.areaList = in.createTypedArrayList(Area.CREATOR);
    }

    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}

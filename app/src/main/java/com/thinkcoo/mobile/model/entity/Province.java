package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.administrator.publicmodule.util.PinyinHelper;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  9:49
 */
public class Province extends Address implements Nullable, Parcelable ,Comparable<Province> {

    private List<City> mCityList = new ArrayList<>();

    public Province() {
    }

    public Province(String name, int code) {
        super(name,code);
    }

    public List<City> getCityList() {
        return mCityList;
    }

    public void setCityList(List<City> cityList) {
        mCityList = cityList;
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
        dest.writeTypedList(this.mCityList);
    }

    protected Province(Parcel in) {
        this.mCityList = in.createTypedArrayList(City.CREATOR);
    }

    public static final Parcelable.Creator<Province> CREATOR = new Parcelable.Creator<Province>() {
        @Override
        public Province createFromParcel(Parcel source) {
            return new Province(source);
        }

        @Override
        public Province[] newArray(int size) {
            return new Province[size];
        }
    };

    @Override
    public int compareTo(Province another) {
        return PinyinHelper.getInstance().getFirstUpCaseLetter(this.getName()).compareTo(PinyinHelper.getInstance().getFirstUpCaseLetter(another.getName()));
    }
}

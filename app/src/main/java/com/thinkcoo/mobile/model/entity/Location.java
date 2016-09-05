package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.location.BDLocation;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;

/**
 * Created by Robert.yao on 2016/7/6.
 */
public class Location implements Parcelable {

    public static final double DEFAULT_LON = 0;
    public static final double DEFAULT_LAT = 0;

    public static final String DEFAULT_LON_STRING = "0.00";
    public static final String DEFAULT_LAT_STRING = "0.00";

    public static Location NULL_LOCATION = new Location("","",DEFAULT_LON,DEFAULT_LAT,"");

    private String city;
    private String cityCode;
    private double lat;
    private double lon;
    private String address;

    public Location() {
    }

    public Location(String city, String cityCode, double lat, double lon, String address) {
        this.city = city;
        this.cityCode = cityCode;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
    }

    public static Location from(BDLocation bdLocation) {

        Location location = new Location();
        location.setCity(bdLocation.getCity());
        location.setCityCode(bdLocation.getCityCode());
        location.setLat(bdLocation.getLatitude());
        location.setLon(bdLocation.getLongitude());
        location.setAddress(bdLocation.getAddrStr());

        return location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLon() {
        return lon;
    }
    public String getLonString(){
        return String.valueOf(lon);
    }
    public String getLatString(){
        return String.valueOf(lat);
    }
    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeString(this.cityCode);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lon);
        dest.writeString(this.address);
    }

    protected Location(Parcel in) {
        this.city = in.readString();
        this.cityCode = in.readString();
        this.lat = in.readDouble();
        this.lon = in.readDouble();
        this.address = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}

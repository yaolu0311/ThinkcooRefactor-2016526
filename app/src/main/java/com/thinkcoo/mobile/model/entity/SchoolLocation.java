package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert.yao on 2016/8/11.
 */
public class SchoolLocation extends Location implements Parcelable{

    private String addressName;

    public static List<SchoolLocation> create(PoiAddress poiAddressi){
        List<SchoolLocation> schoolLocationList = new ArrayList<>();
        List<PoiAddress.Results> resultsList = poiAddressi.getResults();
        for (int i = 0; i < resultsList.size(); i++) {
            PoiAddress.Results results = resultsList.get(i);
            schoolLocationList.add(poiAddressResults2SchoolLocation(results));
        }
        return schoolLocationList;
    }

    private static SchoolLocation poiAddressResults2SchoolLocation(PoiAddress.Results results){
        SchoolLocation schoolLocation = new SchoolLocation();
        schoolLocation.setAddress(results.getAddress());
        schoolLocation.setAddressName(results.getName());
        schoolLocation.setLat(Double.parseDouble(results.getLocation().getLat()));
        schoolLocation.setLon(Double.parseDouble(results.getLocation().getLng()));
        return schoolLocation;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.addressName);
    }

    public SchoolLocation() {
    }

    protected SchoolLocation(Parcel in) {
        super(in);
        this.addressName = in.readString();
    }

    public static final Creator<SchoolLocation> CREATOR = new Creator<SchoolLocation>() {
        @Override
        public SchoolLocation createFromParcel(Parcel source) {
            return new SchoolLocation(source);
        }

        @Override
        public SchoolLocation[] newArray(int size) {
            return new SchoolLocation[size];
        }
    };
}

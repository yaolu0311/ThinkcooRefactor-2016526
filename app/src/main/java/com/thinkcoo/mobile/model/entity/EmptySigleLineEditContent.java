package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;

/**
 * Created by Robert.yao on 2016/8/11.
 */
public class EmptySigleLineEditContent implements com.thinkcoo.mobile.model.strategy.SingleLineEditContent {

    public EmptySigleLineEditContent() {
    }

    @Override
    public String getDisplayName() {
        return "";
    }

    @Override
    public void setDisplayName(String displayName) {

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    protected EmptySigleLineEditContent(Parcel in) {
    }

    public static final Creator<EmptySigleLineEditContent> CREATOR = new Creator<EmptySigleLineEditContent>() {
        @Override
        public EmptySigleLineEditContent createFromParcel(Parcel source) {
            return new EmptySigleLineEditContent(source);
        }

        @Override
        public EmptySigleLineEditContent[] newArray(int size) {
            return new EmptySigleLineEditContent[size];
        }
    };
}

package com.thinkcoo.mobile.model.strategy;

import android.os.Parcel;

/**
 * Created by Robert.yao on 2016/6/17.
 */
public class StringSingleLineEditContent implements SingleLineEditContent {

    private String content;

    public StringSingleLineEditContent(String content) {
        this.content = content;
    }

    @Override
    public String getDisplayName() {
        return content;
    }
    @Override
    public void setDisplayName(String displayName) {
        content = displayName;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
    }
    protected StringSingleLineEditContent(Parcel in) {
        this.content = in.readString();
    }

    public static final Creator<StringSingleLineEditContent> CREATOR = new Creator<StringSingleLineEditContent>() {
        @Override
        public StringSingleLineEditContent createFromParcel(Parcel source) {
            return new StringSingleLineEditContent(source);
        }

        @Override
        public StringSingleLineEditContent[] newArray(int size) {
            return new StringSingleLineEditContent[size];
        }
    };
}

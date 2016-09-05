package com.thinkcoo.mobile.model.entity.serverresponse;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/17.
 */
public class UserWorkExperienceResponse implements Parcelable {

    /**
     * eId : 56
     * time : 2014-06-17 00:00:00.0
     * content : dfdsfdsfstfewrt wtfrew ert qegrqw rg rqgrqew greg qe
     */

    private String eId;
    private String time;
    private String content;

    public String getEId() {
        return eId;
    }

    public void setEId(String eId) {
        this.eId = eId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eId);
        dest.writeString(this.time);
        dest.writeString(this.content);
    }

    public UserWorkExperienceResponse() {
    }

    protected UserWorkExperienceResponse(Parcel in) {
        this.eId = in.readString();
        this.time = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<UserWorkExperienceResponse> CREATOR = new Parcelable.Creator<UserWorkExperienceResponse>() {
        @Override
        public UserWorkExperienceResponse createFromParcel(Parcel source) {
            return new UserWorkExperienceResponse(source);
        }

        @Override
        public UserWorkExperienceResponse[] newArray(int size) {
            return new UserWorkExperienceResponse[size];
        }
    };

}

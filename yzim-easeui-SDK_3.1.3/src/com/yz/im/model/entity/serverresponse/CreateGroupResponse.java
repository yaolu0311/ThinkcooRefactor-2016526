package com.yz.im.model.entity.serverresponse;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cys on 2016/8/4
 */
public class CreateGroupResponse implements Parcelable {
    /**
     * groupId : 558
     * easemobGroupId : 226563750411370936
     */

    private int groupId;
    private String easemobGroupId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getEasemobGroupId() {
        return easemobGroupId;
    }

    public void setEasemobGroupId(String easemobGroupId) {
        this.easemobGroupId = easemobGroupId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.groupId);
        dest.writeString(this.easemobGroupId);
    }

    public CreateGroupResponse() {
    }

    protected CreateGroupResponse(Parcel in) {
        this.groupId = in.readInt();
        this.easemobGroupId = in.readString();
    }

    public static final Parcelable.Creator<CreateGroupResponse> CREATOR = new Parcelable.Creator<CreateGroupResponse>() {
        @Override
        public CreateGroupResponse createFromParcel(Parcel source) {
            return new CreateGroupResponse(source);
        }

        @Override
        public CreateGroupResponse[] newArray(int size) {
            return new CreateGroupResponse[size];
        }
    };
}

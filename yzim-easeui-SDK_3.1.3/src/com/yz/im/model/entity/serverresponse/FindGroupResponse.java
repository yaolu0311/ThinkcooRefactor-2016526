package com.yz.im.model.entity.serverresponse;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cys on 2016/8/1
 */
public class FindGroupResponse implements Parcelable {
    /**
     * circleIntroduce : 从不喝酒你慢慢CTV发红包，贝干很久。
     * image : http://117.34.109.231/yingzi-mobile/upload/chat/headportrait/20160506111855498.jpg
     * easemobGroupId : 193015415377494452
     * num : 6
     * groupId : 77
     * groupName : 今天星期五
     */

    private String circleIntroduce;
    private String image;
    private String easemobGroupId;
    private String num;
    private String groupId;
    private String groupName;

    public String getCircleIntroduce() {
        return circleIntroduce;
    }

    public void setCircleIntroduce(String circleIntroduce) {
        this.circleIntroduce = circleIntroduce;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEasemobGroupId() {
        return easemobGroupId;
    }

    public void setEasemobGroupId(String easemobGroupId) {
        this.easemobGroupId = easemobGroupId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.circleIntroduce);
        dest.writeString(this.image);
        dest.writeString(this.easemobGroupId);
        dest.writeString(this.num);
        dest.writeString(this.groupId);
        dest.writeString(this.groupName);
    }

    public FindGroupResponse() {
    }

    protected FindGroupResponse(Parcel in) {
        this.circleIntroduce = in.readString();
        this.image = in.readString();
        this.easemobGroupId = in.readString();
        this.num = in.readString();
        this.groupId = in.readString();
        this.groupName = in.readString();
    }

    public static final Parcelable.Creator<FindGroupResponse> CREATOR = new Parcelable.Creator<FindGroupResponse>() {
        @Override
        public FindGroupResponse createFromParcel(Parcel source) {
            return new FindGroupResponse(source);
        }

        @Override
        public FindGroupResponse[] newArray(int size) {
            return new FindGroupResponse[size];
        }
    };
}

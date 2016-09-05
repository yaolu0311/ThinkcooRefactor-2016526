package com.yz.im.model.entity.serverresponse;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by cys on 2016/7/22
 * 圈子信息 实体类
 */
public class GroupInfoResponse implements Parcelable {

    /**
     * realName : null
     * groupId : 520
     * groupImage : http://117.34.109.231/yingzi-mobile/upload/chat/headportrait/20160721151829875.jpg
     * groupName : 英雄联盟
     * groupNum : 2
     * stick : 0
     * disturb : 0
     * groupType : 1
     * circleIntroduce : null
     * easemobGroupId : 221281322134405568
     * oldUserId : 10017
     */

    private String realName;
    private String groupId;
    private String groupImage;
    private String groupName;
    private String groupNum;
    private String stick;
    private String disturb;
    private String groupType;
    private String circleIntroduce;
    private String easemobGroupId;
    private String oldUserId;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getStick() {
        return stick;
    }

    public void setStick(String stick) {
        this.stick = stick;
    }

    public String getDisturb() {
        return disturb;
    }

    public void setDisturb(String disturb) {
        this.disturb = disturb;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getCircleIntroduce() {
        return circleIntroduce;
    }

    public void setCircleIntroduce(String circleIntroduce) {
        this.circleIntroduce = circleIntroduce;
    }

    public String getEasemobGroupId() {
        return easemobGroupId;
    }

    public void setEasemobGroupId(String easemobGroupId) {
        this.easemobGroupId = easemobGroupId;
    }

    public String getOldUserId() {
        return oldUserId;
    }

    public void setOldUserId(String oldUserId) {
        this.oldUserId = oldUserId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.realName);
        dest.writeString(this.groupId);
        dest.writeString(this.groupImage);
        dest.writeString(this.groupName);
        dest.writeString(this.groupNum);
        dest.writeString(this.stick);
        dest.writeString(this.disturb);
        dest.writeString(this.groupType);
        dest.writeString(this.circleIntroduce);
        dest.writeString(this.easemobGroupId);
        dest.writeString(this.oldUserId);
    }

    public GroupInfoResponse() {
    }

    protected GroupInfoResponse(Parcel in) {
        this.realName = in.readString();
        this.groupId = in.readString();
        this.groupImage = in.readString();
        this.groupName = in.readString();
        this.groupNum = in.readString();
        this.stick = in.readString();
        this.disturb = in.readString();
        this.groupType = in.readString();
        this.circleIntroduce = in.readString();
        this.easemobGroupId = in.readString();
        this.oldUserId = in.readString();
    }

    public static final Parcelable.Creator<GroupInfoResponse> CREATOR = new Parcelable.Creator<GroupInfoResponse>() {
        @Override
        public GroupInfoResponse createFromParcel(Parcel source) {
            return new GroupInfoResponse(source);
        }

        @Override
        public GroupInfoResponse[] newArray(int size) {
            return new GroupInfoResponse[size];
        }
    };

    public String getDisplayId(){
        return TextUtils.isEmpty(easemobGroupId) ? groupId : easemobGroupId;
    }
}

package com.yz.im.model.db.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.administrator.publicmodule.entity.nullentities.Nullable;
import com.yz.im.model.entity.nullentities.NullGroup;
import com.yz.im.model.entity.serverresponse.GroupResponse;
import com.yzkj.android.orm.annotation.Column;
import com.yzkj.android.orm.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cys on 2016/7/19
 * 群组数据表
 */
@Table(name = "GropuInfoResponse_v2")
public class Group implements Nullable, Parcelable {

    public static Group NULL_GROUP = new NullGroup();

    public static List<Group> fromServerResponse(List<GroupResponse> data) {
        List<Group> groups = new ArrayList<>();
        if (data == null) {
            return groups;
        }
        for (GroupResponse response: data) {
            Group group = new Group();
            group.setEasemobGroupId(response.getEasemobGroupId());
            group.setDisturb(response.getDisturb());
            group.setStick(response.getStick());
            group.setStickTime(response.getStickTime());
            group.setGroupId(response.getGroupId());
            group.setGroupName(response.getGroupName());
            group.setGroupImage(response.getGroupImage());
            group.setGroupType(response.getGroupType());
            group.setOldUserId(response.getOldUserId());

            groups.add(group);
        }
        return groups;
    }

    @Column(name = "oldUserId")
    private String oldUserId;  //群主id
    @Column(name = "groupId", isId = true, autoGen =  false)
    private String groupId;  //圈子ID
    @Column(name = "easemobGroupId")
    private String easemobGroupId;  //环信圈子ID
    @Column(name = "groupImage")
    private String groupImage; //圈子头像
    @Column(name = "groupName")
    private String groupName; //圈子名称
    @Column(name = "groupInfor")
    private String groupInfor;
    @Column(name = "groupNum")
    private String groupNum;  //圈子成员数
    @Column(name = "groupCardName")
    private String groupCardName;
    @Column(name = "realName")
    private String realName;  //我的圈子名片
    @Column(name = "name")
    private String name;  //好友姓名
    @Column(name = "circleIntroduce")
    private String circleIntroduce;  //圈子简介
    @Column(name = "stick")
    private String stick;  //置顶设置
    @Column(name = "disturb")
    private String disturb;  //消息免打扰
    @Column(name = "groupType")
    private String groupType;  //圈子的类型("1"主动圈，“2”是被动圈，“3”是活动圈，“4”课程圈，“5”，只能查看)
    @Column(name = "stickTime")
    private String stickTime;

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
        dest.writeString(this.oldUserId);
        dest.writeString(this.groupId);
        dest.writeString(this.easemobGroupId);
        dest.writeString(this.groupImage);
        dest.writeString(this.groupName);
        dest.writeString(this.groupInfor);
        dest.writeString(this.groupNum);
        dest.writeString(this.groupCardName);
        dest.writeString(this.realName);
        dest.writeString(this.name);
        dest.writeString(this.circleIntroduce);
        dest.writeString(this.stick);
        dest.writeString(this.disturb);
        dest.writeString(this.groupType);
        dest.writeString(this.stickTime);
    }

    public Group() {
    }

    protected Group(Parcel in) {
        this.oldUserId = in.readString();
        this.groupId = in.readString();
        this.easemobGroupId = in.readString();
        this.groupImage = in.readString();
        this.groupName = in.readString();
        this.groupInfor = in.readString();
        this.groupNum = in.readString();
        this.groupCardName = in.readString();
        this.realName = in.readString();
        this.name = in.readString();
        this.circleIntroduce = in.readString();
        this.stick = in.readString();
        this.disturb = in.readString();
        this.groupType = in.readString();
        this.stickTime = in.readString();
    }

    public static final Parcelable.Creator<Group> CREATOR = new Parcelable.Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel source) {
            return new Group(source);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public String getOldUserId() {
        return oldUserId;
    }

    public void setOldUserId(String oldUserId) {
        this.oldUserId = oldUserId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getEasemobGroupId() {
        return easemobGroupId;
    }

    public void setEasemobGroupId(String easemobGroupId) {
        this.easemobGroupId = easemobGroupId;
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

    public String getGroupInfor() {
        return groupInfor;
    }

    public void setGroupInfor(String groupInfor) {
        this.groupInfor = groupInfor;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getGroupCardName() {
        return groupCardName;
    }

    public void setGroupCardName(String groupCardName) {
        this.groupCardName = groupCardName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCircleIntroduce() {
        return circleIntroduce;
    }

    public void setCircleIntroduce(String circleIntroduce) {
        this.circleIntroduce = circleIntroduce;
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

    public String getStickTime() {
        return stickTime;
    }

    public void setStickTime(String stickTime) {
        this.stickTime = stickTime;
    }

}

package com.yz.im.model.entity.serverresponse;

import android.os.Parcel;
import android.text.TextUtils;

import com.example.administrator.publicmodule.util.PinyinHelper;
import com.yz.im.IMHelper;
import com.yz.im.model.entity.BaseSortEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cys on 2016/7/27
 */
public class GroupMemberResponse extends BaseSortEntity {


    public static List<GroupMemberResponse> fromServerResponse(List<GroupMemberResponse> data) {
        List<GroupMemberResponse> responses = new ArrayList<>();
        if (data == null) {
            return responses;
        }
        PinyinHelper pinyinHelper = IMHelper.getInstance().getPinyinHelper();
        for (GroupMemberResponse entity : data) {

            String remarkName = entity.getRemarkName();
            String groupCircleName = entity.getCircleNick();
            String realName = entity.getRealName();
            String emName = TextUtils.isEmpty(remarkName) ? TextUtils.isEmpty(groupCircleName) ? realName : groupCircleName : remarkName;

            entity.setCheck(false);
            entity.setFirstLetter(pinyinHelper.getFirstUpCaseLetter(emName));
            entity.setNameLetters(pinyinHelper.getPinyins(emName));
            entity.setShowName(emName);
            responses.add(entity);
        }
        return responses;
    }

    /**
     * remarkName : null  好友昵称
     * realName : 高军
     * school : null
     * userId : 20044
     * imager : http://www.thinkcoo.com/yingzi-mobile/upload/personal/headportrait/20160623090442156.jpg
     * shield : 0 表示没有屏蔽此陌生人  1表示屏蔽此陌生人
     * groupType : 1
     * isFriends : 1表示是好友  0表示陌生人
     * circleNick : 圈子昵称
     * sno : null
     * doubtCnt : 0
     * idType : 1 表示圈主
     */

    private String remarkName;
    private String realName;
    private String school;
    private String userId;
    private String imager;
    private String shield;
    private String groupType;
    private String isFriends;
    private String blacklist;
    private String circleNick;
    private String sno;
    private String doubtCnt;
    private String idType;

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImager() {
        return imager;
    }

    public void setImager(String imager) {
        this.imager = imager;
    }

    public String getShield() {
        return shield;
    }

    public void setShield(String shield) {
        this.shield = shield;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getIsFriends() {
        return isFriends;
    }

    public void setIsFriends(String isFriends) {
        this.isFriends = isFriends;
    }

    public String getCircleNick() {
        return circleNick;
    }

    public void setCircleNick(String circleNick) {
        this.circleNick = circleNick;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getDoubtCnt() {
        return doubtCnt;
    }

    public void setDoubtCnt(String doubtCnt) {
        this.doubtCnt = doubtCnt;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public int compareTo(Object another) {
        if (another == null) {
            return -1;
        }
        if (!(another instanceof BaseSortEntity)) {
            return -1;
        }

        BaseSortEntity entity = (BaseSortEntity) another;

        if(isOwner(this)){
            return -1;
        }

        if(isOwner((GroupMemberResponse) entity)){
            return 1;
        }

        return this.getNameLetters().compareTo(entity.getNameLetters());
    }

    private boolean isOwner(GroupMemberResponse response){
        if (response == null) {
            return false;
        }
        return "1".equals(response.getIdType());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.remarkName);
        dest.writeString(this.realName);
        dest.writeString(this.school);
        dest.writeString(this.userId);
        dest.writeString(this.imager);
        dest.writeString(this.shield);
        dest.writeString(this.groupType);
        dest.writeString(this.isFriends);
        dest.writeString(this.blacklist);
        dest.writeString(this.circleNick);
        dest.writeString(this.sno);
        dest.writeString(this.doubtCnt);
        dest.writeString(this.idType);
    }

    public GroupMemberResponse() {
    }

    protected GroupMemberResponse(Parcel in) {
        super(in);
        this.remarkName = in.readString();
        this.realName = in.readString();
        this.school = in.readString();
        this.userId = in.readString();
        this.imager = in.readString();
        this.shield = in.readString();
        this.groupType = in.readString();
        this.isFriends = in.readString();
        this.blacklist = in.readString();
        this.circleNick = in.readString();
        this.sno = in.readString();
        this.doubtCnt = in.readString();
        this.idType = in.readString();
    }

    public static final Creator<GroupMemberResponse> CREATOR = new Creator<GroupMemberResponse>() {
        @Override
        public GroupMemberResponse createFromParcel(Parcel source) {
            return new GroupMemberResponse(source);
        }

        @Override
        public GroupMemberResponse[] newArray(int size) {
            return new GroupMemberResponse[size];
        }
    };
}

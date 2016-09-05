package com.yz.im.model.db.entity;


import android.os.Parcel;
import android.text.TextUtils;

import com.example.administrator.publicmodule.entity.nullentities.Nullable;
import com.example.administrator.publicmodule.util.PinyinHelper;
import com.yz.im.IMHelper;
import com.yz.im.model.entity.BaseSortEntity;
import com.yz.im.model.entity.nullentities.NullFriend;
import com.yz.im.model.entity.serverresponse.FriendResponse;
import com.yzkj.android.orm.annotation.Column;
import com.yzkj.android.orm.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cys on 2016/7/19
 * 好友数据表
 */
@Table(name = "friend_list_v2")
public class Friend extends BaseSortEntity implements Nullable{

    public static final Friend NULL_FRIEND = new NullFriend();


    public static List<Friend> fromServerResponse(List<FriendResponse> data) {
        List<Friend> friendList = new ArrayList<>();
        if (data == null) {
            return friendList;
        }
        PinyinHelper pinyinHelper = IMHelper.getInstance().getPinyinHelper();
        for (FriendResponse response : data) {
            Friend friend = new Friend();

            String remarkName = response.getRemarkName();
            String realName = response.getRealName();
            String emName = TextUtils.isEmpty(remarkName) ? realName : remarkName;

            friend.setUserId(response.getUserId());
            friend.setSchool(response.getSchool());
            friend.setSex(response.getSex());
            friend.setBlacklist(response.getBlacklist());
            friend.setShield(response.getShield());
            friend.setStickTime(response.getStickTime());
            friend.setImage(response.getImage());
            friend.setStick(response.getStick());
            friend.setDisturb(response.getDisturb());
            friend.setSignature(response.getSignature());
            friend.setRealName(realName);
            setName(friend, emName);
            friendList.add(friend);
        }
        return friendList;
    }

    @Column(name = "userId", isId = true, autoGen = false)
    private String userId;//好友id
    @Column(name = "school")
    private String school;//好友学校或者单位
    @Column(name = "sex")
    private String sex;//好友性别
    @Column(name = "blacklist")
    private String blacklist;//是否黑名单
    @Column(name = "beBlack")
    private String beBlack;//是否被对方拉黑
    @Column(name = "shield")
    private String shield;//设置是否屏蔽
    @Column(name = "stickTime")
    private String stickTime;//聊天置顶时间
    @Column(name = "image")
    private String image;//好友头像
    @Column(name = "stick")
    private String stick;//设置是否置顶
    @Column(name = "disturb")
    private String disturb;//设置是否免打扰
    @Column(name = "signature")
    private String signature;//个性签名
    @Column(name = "emUserName")
    private String emUserName;//环信好友（通讯录二次搜索模糊匹配）
    @Column(name = "sortLetters")
    private String sortLetters;// 首字母
    @Column(name = "remarkName")
    private String remarkName;//备注
    @Column(name = "nickName")
    private String nickName;//自信中的姓名
    @Column(name = "name")
    private String name;
    @Column(name = "realName")
    private String realName;

    @Override
    public boolean isEmpty() {
        return false;
    }

    public String getUserId() {
        return userId;
    }

    public Friend setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getSchool() {
        return school;
    }

    public Friend setSchool(String school) {
        this.school = school;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public Friend setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public Friend setBlacklist(String blacklist) {
        this.blacklist = blacklist;
        return this;
    }

    public String getBeBlack() {
        return beBlack;
    }

    public Friend setBeBlack(String beBlack) {
        this.beBlack = beBlack;
        return this;
    }

    public String getShield() {
        return shield;
    }

    public Friend setShield(String shield) {
        this.shield = shield;
        return this;
    }

    public String getStickTime() {
        return stickTime;
    }

    public Friend setStickTime(String stickTime) {
        this.stickTime = stickTime;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Friend setImage(String image) {
        this.image = image;
        return this;
    }

    public String getStick() {
        return stick;
    }

    public Friend setStick(String stick) {
        this.stick = stick;
        return this;
    }

    public String getDisturb() {
        return disturb;
    }

    public Friend setDisturb(String disturb) {
        this.disturb = disturb;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public Friend setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public String getEmUserName() {
        return emUserName;
    }

    public Friend setEmUserName(String emUserName) {
        this.emUserName = emUserName;
        return this;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public Friend setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
        return this;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public Friend setRemarkName(String remarkName) {
        this.remarkName = remarkName;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public Friend setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getName() {
        return name;
    }

    public Friend setName(String name) {
        this.name = name;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public Friend setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.userId);
        dest.writeString(this.school);
        dest.writeString(this.sex);
        dest.writeString(this.blacklist);
        dest.writeString(this.beBlack);
        dest.writeString(this.shield);
        dest.writeString(this.stickTime);
        dest.writeString(this.image);
        dest.writeString(this.stick);
        dest.writeString(this.disturb);
        dest.writeString(this.signature);
        dest.writeString(this.emUserName);
        dest.writeString(this.sortLetters);
        dest.writeString(this.remarkName);
        dest.writeString(this.nickName);
        dest.writeString(this.name);
        dest.writeString(this.realName);
    }

    public Friend() {
    }

    protected Friend(Parcel in) {
        super(in);
        this.userId = in.readString();
        this.school = in.readString();
        this.sex = in.readString();
        this.blacklist = in.readString();
        this.beBlack = in.readString();
        this.shield = in.readString();
        this.stickTime = in.readString();
        this.image = in.readString();
        this.stick = in.readString();
        this.disturb = in.readString();
        this.signature = in.readString();
        this.emUserName = in.readString();
        this.sortLetters = in.readString();
        this.remarkName = in.readString();
        this.nickName = in.readString();
        this.name = in.readString();
        this.realName = in.readString();
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel source) {
            return new Friend(source);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    public static void setName(Friend friend, String name){
        PinyinHelper pinyinHelper = new PinyinHelper();
        friend.setEmUserName(name);
        friend.setNickName(name);
        friend.setRemarkName(name);
        friend.setShowName(name);
        friend.setSortLetters(pinyinHelper.getFirstUpCaseLetter(name));
        friend.setFirstLetter(pinyinHelper.getFirstUpCaseLetter(name));
        friend.setNameLetters(pinyinHelper.getPinyins(name).toLowerCase());
    }
}

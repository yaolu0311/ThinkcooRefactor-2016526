package com.yz.im.model.entity.serverresponse;

/**
 * Created by cys on 2016/7/22
 * 好友信息 实体类
 */
public class FriendInfoResponse {


    /**
     * school : null
     * stick : 0  聊天置顶
     * disturb : 0   消息免打扰
     * sex : 未知
     * blacklist : 0   加入黑名单
     * beBlack : 1
     * shield : 0
     * image : http://117.34.109.231/yingzi-mobile/upload/personal/headportrait/20160506130809856.jpg
     * nickName : 糊涂
     * stickTime : null
     * userId : 10024
     * name : null
     * signature : 你好好看书吧唧唧歪歪1112
     */

    private String school;
    private String stick;
    private String disturb;
    private String sex;
    private String blacklist;
    private String beBlack;
    private String shield;
    private String image;
    private String nickName;
    private String stickTime;
    private String userId;
    private String name;
    private String signature;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }

    public String getBeBlack() {
        return beBlack;
    }

    public void setBeBlack(String beBlack) {
        this.beBlack = beBlack;
    }

    public String getShield() {
        return shield;
    }

    public void setShield(String shield) {
        this.shield = shield;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getStickTime() {
        return stickTime;
    }

    public void setStickTime(String stickTime) {
        this.stickTime = stickTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}

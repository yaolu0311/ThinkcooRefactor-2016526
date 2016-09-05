package com.yz.im.model.db.entity;

/**
 * 好友列表 数据表
 */
public class FriendList extends BaseBean {
    private static final long serialVersionUID = 1L;
    private int id;//本地数据库编号
    private String userId;//好友id
    private String school;//好友学校或者单位
    private String sex;//好友性别
    private String blacklist;//是否黑名单
    private String beBlack;//是否被对方拉黑
    private String shield;//设置是否屏蔽
    private String stickTime;//聊天置顶时间
    private String image;//好友头像
    private String stick;//设置是否置顶
    private String disturb;//设置是否免打扰
    private String signature;//个性签名
    private String emUserName;//环信好友（通讯录二次搜索模糊匹配）
    private String sortLetters;// 首字母
    private String remarkName;//备注
    private String nickName;//自信中的姓名
    private String name;
    private String realName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDisturb() {
        return disturb;
    }

    public void setDisturb(String disturb) {
        this.disturb = disturb;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }

    public String getStick() {
        return stick;
    }

    public void setStick(String stick) {
        this.stick = stick;
    }

    public String getShield() {
        return shield;
    }

    public void setShield(String shield) {
        this.shield = shield;
    }

    public String getBeBlack() {
        return beBlack;
    }

    public void setBeBlack(String beBlack) {
        this.beBlack = beBlack;
    }

    public String getStickTime() {
        return stickTime;
    }

    public void setStickTime(String stickTime) {
        this.stickTime = stickTime;
    }


    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getEmUserName() {
        return emUserName;
    }

    public void setEmUserName(String emUserName) {
        this.emUserName = emUserName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}

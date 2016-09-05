package com.yz.im.model.entity.serverresponse;

/**
 * Created by cys on 2016/8/5
 */
public class NoticeMessageResponse {

    /**
     * userId : 10017   邀请人id
     * membership : null    群成员个数
     * circleImage : null  群头像
     * messageState : 0   "-1"表示未验证， "0"表示验证通过
     * circleOwnId : null    圈主id
     * circleIntroduce : null   圈子简介
     * circleId : 520    圈子id
     * sendTime : 2016-07-21 15:23:12.0   邀请时间
     * easemobGroupId : null  圈子环信id
     * circleName : null   圈子名称
     * userImage : http://117.34.109.231/yingzi-mobile/upload/personal/headportrait/20160527094415637.jpg   邀请人的头像
     * userSex : 男
     * institution : null  fuck field
     * messageId : 490   消息id
     * messageType : 2   "0"表示好友验证消息  "1"表示其他通知  "2"表示圈子验证消息
     * messageContext : 阿妈   申请理由
     * userName : null   fuck  field
     * name :  邀请人姓名
     */

    private String userId;
    private String membership;
    private String circleImage;
    private String messageState;
    private String circleOwnId;
    private String circleIntroduce;
    private String circleId;
    private String sendTime;
    private String easemobGroupId;
    private String circleName;
    private String userImage;
    private String userSex;
    private String institution;
    private String messageId;
    private String messageType;
    private String messageContext;
    private String userName;
    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getCircleImage() {
        return circleImage;
    }

    public void setCircleImage(String circleImage) {
        this.circleImage = circleImage;
    }

    public String getMessageState() {
        return messageState;
    }

    public void setMessageState(String messageState) {
        this.messageState = messageState;
    }

    public String getCircleOwnId() {
        return circleOwnId;
    }

    public void setCircleOwnId(String circleOwnId) {
        this.circleOwnId = circleOwnId;
    }

    public String getCircleIntroduce() {
        return circleIntroduce;
    }

    public void setCircleIntroduce(String circleIntroduce) {
        this.circleIntroduce = circleIntroduce;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getEasemobGroupId() {
        return easemobGroupId;
    }

    public void setEasemobGroupId(String easemobGroupId) {
        this.easemobGroupId = easemobGroupId;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(String messageContext) {
        this.messageContext = messageContext;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

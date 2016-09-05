package com.thinkcoo.mobile.model.entity.serverresponse;

public class EventTimeResponse {
    private String eventTimeId;//'事件时段Id',
    private String eventId;//"事件Id'',
    private String accountId;//"用户ID"
    private String realName;// 创建者名称
    private String runDate;//"执行日期"
    private String eventName;//"事件名称"
    private String eventType;//"事件类型"
    private String isSystem;//"是否系统推荐"
    private String color;//颜色
    private String isAllDay;//是否全天
    private String eventPlace;//事件地点
    private String beginDate;//开始日期
    private String endDate;//结束日期
    private String beginTime;//开始时间
    private String endTime;//结束时间
    private String cycleValue;//周期值
    private long begingStamp;//开始时间(秒)
    private long endStamp;//结束时间(秒)
    private String circleId;//圈子Id
    private String easemobGroupId;//环信圈子ID

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEventTimeId() {
        return eventTimeId;
    }

    public void setEventTimeId(String eventTimeId) {
        this.eventTimeId = eventTimeId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIsAllDay() {
        return isAllDay;
    }

    public void setIsAllDay(String isAllDay) {
        this.isAllDay = isAllDay;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCycleValue() {
        return cycleValue;
    }

    public void setCycleValue(String cycleValue) {
        this.cycleValue = cycleValue;
    }

    public long getBegingStamp() {
        return begingStamp;
    }

    public void setBegingStamp(long begingStamp) {
        this.begingStamp = begingStamp;
    }

    public long getEndStamp() {
        return endStamp;
    }

    public void setEndStamp(long endStamp) {
        this.endStamp = endStamp;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public String getEasemobGroupId() {
        return easemobGroupId;
    }

    public void setEasemobGroupId(String easemobGroupId) {
        this.easemobGroupId = easemobGroupId;
    }
}
package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkcoo.mobile.model.entity.serverresponse.ScheduleResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/28.
 */
public class Schedule implements Parcelable {


    public static ScheduleResponse toServerResponse(Schedule schedule) {
        ScheduleResponse response = new ScheduleResponse();
        response.setEventId(schedule.getScheduleId());
        response.setAccountId(schedule.getScheduleCreateUser().getUserId());
        response.setEventName(schedule.getName());
        response.setEventType(String.valueOf(schedule.getType()));
        response.setColor(schedule.getColor());
        response.setEventUnit(schedule.getOrganization());
        response.setRemindWay(schedule.getRemindTime());
        response.setRemark(schedule.getRemark());
        response.setDelEventTimeids(schedule.getDeletedEventTimeIds());
        List<EventTime> localEventList = schedule.getEventTimeList();

        List<ScheduleResponse.EventTimeEntityBean> serverEventList = new ArrayList<>();
        for (int i = 0; i < localEventList.size() ; i++) {
            EventTime eventTime = localEventList.get(i);

            ScheduleResponse.EventTimeEntityBean eventTimeEntityBean = new ScheduleResponse.EventTimeEntityBean();
            eventTimeEntityBean.setAccountId(schedule.getScheduleCreateUser().getUserId()); //创建者的id
            eventTimeEntityBean.setEventTimeId(eventTime.getId());
            eventTimeEntityBean.setEventId(eventTime.getScheduleId());
            eventTimeEntityBean.setIsAllDay(eventTime.isAllDay() ? "1":"0");
            eventTimeEntityBean.setEventPlace(eventTime.getLocation());
            eventTimeEntityBean.setBeginDate(eventTime.getStartDateString());
            eventTimeEntityBean.setEndDate(eventTime.getEndDateString());
            eventTimeEntityBean.setCycleValue(eventTime.getRepeatType());
            serverEventList.add(eventTimeEntityBean);
        }

        response.setEventTimeEntity(serverEventList);
        return response;
    }

    public static Schedule fromServerResponse(ScheduleResponse scheduleResponse, User user) {
        Schedule localSchedule = new Schedule();
        localSchedule.setScheduleId(scheduleResponse.getEventId());
        localSchedule.setName(scheduleResponse.getEventName());
        localSchedule.setColor(scheduleResponse.getColor());
        localSchedule.setType(Integer.parseInt(scheduleResponse.getEventType()));
        localSchedule.setOrganization(scheduleResponse.getEventUnit());
        localSchedule.setRemark(scheduleResponse.getRemark());
        localSchedule.setRemindTime(scheduleResponse.getRemindWay());
        localSchedule.setScheduleCreateUser(new ScheduleCreateUser(scheduleResponse.getAccountId(),scheduleResponse.getAccountName()));

        List<ScheduleResponse.EventTimeEntityBean> serverTimeEvents = scheduleResponse.getEventTimeEntity();
        List<EventTime> localEventList = new ArrayList<>();
        for (int i = 0; i < serverTimeEvents.size(); i++) {
            ScheduleResponse.EventTimeEntityBean serverEventTime = serverTimeEvents.get(i);
            EventTime localEventTime = new EventTime();
            localEventTime.setCreateUser(new ScheduleCreateUser(serverEventTime.getAccountId(),scheduleResponse.getAccountName()));
            localEventTime.setCreatedAuthor(user.getUserId().equals(scheduleResponse.getAccountId()));
            localEventTime.setScheduleId(serverEventTime.getEventId());
            localEventTime.setId(serverEventTime.getEventTimeId());
            localEventTime.setAllDay(Integer.parseInt(serverEventTime.getIsAllDay()) ==1);
            localEventTime.setLocation(serverEventTime.getEventPlace());
            localEventTime.setStartMillis(serverEventTime.getStartMillis());
            localEventTime.setEndMillis(serverEventTime.getEndMillis());
            localEventTime.setRepeatType(serverEventTime.getCycleValue());

            localEventList.add(localEventTime);
        }
        localSchedule.setEventTimeList(localEventList);

        return localSchedule;
    }

    public static final int SCHEDULE_TYPE_MANAGER = 0x0001; // 管理
    public static final int SCHEDULE_TYPE_COURSE = 0x0002;// 授课
    public static final int SCHEDULE_TYPE_LEARN = 0x0003;// 自学
    public static final int SCHEDULE_TYPE_ACTIVITY = 0x0004;// 活动
    public static final int DEFAULT_TYPE = 0;

    public interface TypeColor {
        String SCHEDULE_MANAGER_COLOR = "#FF2D2D";
        String SCHEDULE_COURSE_COLOR = "#46A3FF";
        String SCHEDULE_LEARN_COLOR = "#9AFF02";
        String SCHEDULE_ACTIVITY_COLOR = "#FF5809";
    }

    public static String getColorFromType(int type) {
        switch (type) {
            case SCHEDULE_TYPE_MANAGER:
                return TypeColor.SCHEDULE_MANAGER_COLOR;
            case SCHEDULE_TYPE_COURSE:
                return TypeColor.SCHEDULE_COURSE_COLOR;
            case SCHEDULE_TYPE_LEARN:
                return TypeColor.SCHEDULE_LEARN_COLOR;
            case SCHEDULE_TYPE_ACTIVITY:
                return TypeColor.SCHEDULE_ACTIVITY_COLOR;
        }
        throw new IllegalArgumentException("invalid type");
    }
    /**
     * 日程id
     */
    private String scheduleId;
    /**
     * 日程名称
     */
    private String name;
    /***
     * 日程类别:管理，课程，学习，活动   分别为1,2,3,4
     */
    private int type = DEFAULT_TYPE;
    /**
     * 日程颜色
     */
    private String color;
    /**
     * 组织单位
     */
    private String organization;

    /**
     * 日程提醒时间
     */
    private String  remindTime;
    /**
     * 日程备注
     */
    private String remark;
    /**
     *日程创建人
     */
    private ScheduleCreateUser scheduleCreateUser;
    /**
     * 单个事件时段的集合
     */
    private List<EventTime> mEventTimeList;

    /**
     *  编缉时被删除时段的id,用”,“拼接
     */
    private String mDeletedEventTimeIds;

    public String getDeletedEventTimeIds() {
        return mDeletedEventTimeIds;
    }

    public void setDeletedEventTimeIds(String deletedEventTimeIds) {
        mDeletedEventTimeIds = deletedEventTimeIds;
    }

    public ScheduleCreateUser getScheduleCreateUser() {
        return scheduleCreateUser;
    }

    public void setScheduleCreateUser(ScheduleCreateUser scheduleCreateUser) {
        this.scheduleCreateUser = scheduleCreateUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
//        setScheduleStrategy(ScheduleStrategyFactory.create(type));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public List<EventTime> getEventTimeList() {
        return mEventTimeList;
    }

    public void setEventTimeList(List<EventTime> eventTimeList) {
        mEventTimeList = eventTimeList;
    }
    public String getColor() {
        return getColorFromType(getType());
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (type != schedule.type) return false;
        if (scheduleId != null ? !scheduleId.equals(schedule.scheduleId) : schedule.scheduleId != null)
            return false;
        if (name != null ? !name.equals(schedule.name) : schedule.name != null) return false;
        if (color != null ? !color.equals(schedule.color) : schedule.color != null) return false;
        if (organization != null ? !organization.equals(schedule.organization) : schedule.organization != null)
            return false;
        if (remindTime != null ? !remindTime.equals(schedule.remindTime) : schedule.remindTime != null)
            return false;
        if (remark != null ? !remark.equals(schedule.remark) : schedule.remark != null)
            return false;
        if (scheduleCreateUser != null ? !scheduleCreateUser.equals(schedule.scheduleCreateUser) : schedule.scheduleCreateUser != null)
            return false;
        return mEventTimeList != null ? mEventTimeList.equals(schedule.mEventTimeList) : schedule.mEventTimeList == null;

    }

    @Override
    public int hashCode() {
        int result = scheduleId != null ? scheduleId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        result = 31 * result + (remindTime != null ? remindTime.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (scheduleCreateUser != null ? scheduleCreateUser.hashCode() : 0);
        result = 31 * result + (mEventTimeList != null ? mEventTimeList.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.scheduleId);
        dest.writeString(this.name);
        dest.writeInt(this.type);
        dest.writeString(this.color);
        dest.writeString(this.organization);
        dest.writeString(this.remindTime);
        dest.writeString(this.remark);
        dest.writeParcelable(this.scheduleCreateUser, flags);
        dest.writeTypedList(this.mEventTimeList);
    }

    public Schedule() {
    }

    protected Schedule(Parcel in) {
        this.scheduleId = in.readString();
        this.name = in.readString();
        this.type = in.readInt();
        this.color = in.readString();
        this.organization = in.readString();
        this.remindTime = in.readString();
        this.remark = in.readString();
        this.scheduleCreateUser = in.readParcelable(ScheduleCreateUser.class.getClassLoader());
        this.mEventTimeList = in.createTypedArrayList(EventTime.CREATOR);
    }

    public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel source) {
            return new Schedule(source);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };

}

package com.thinkcoo.mobile.model.entity;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.thinkcoo.mobile.model.entity.serverresponse.EventTimeResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Utils;
import com.thinkcoo.mobile.utils.DateUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leevin
 * CreateTime: 2016/7/19  9:57
 * 单个时段的事件
 */
public class EventTime implements Parcelable {


    private static final String DEFAUT_COLOR = "#46A3FF";

    // 本地时段实体
    public static List<EventTime> fromServerResponse(List<EventTimeResponse> serverEventTimeList, ServerDataConverter serverDataConverter, User user) {
        List<EventTime> eventTimeList = new ArrayList<>();
        for (int i = 0; i < serverEventTimeList.size() ; i++) {
            EventTimeResponse serverEventTime = serverEventTimeList.get(i);
            EventTime eventTime = new EventTime();
            eventTime.setId(serverEventTime.getEventTimeId());
            eventTime.setColor(serverEventTime.getColor());
            eventTime.setEventName(serverEventTime.getEventName());
            eventTime.setScheduleType(serverDataConverter.stringToInt(serverEventTime.getEventType(),-1));
            eventTime.setScheduleId(serverEventTime.getEventId());
            eventTime.setCreateUser(new ScheduleCreateUser(serverEventTime.getAccountId(), serverEventTime.getRealName()));
            eventTime.setCreatedAuthor(user.getUserId().equals(serverEventTime.getAccountId()));
            eventTime.setAllDay(serverDataConverter.stringToBoolean(serverEventTime.getIsAllDay()));
            eventTime.setRepeatType(serverEventTime.getCycleValue());
            eventTime.setStartMillis(serverEventTime.getBegingStamp());
            eventTime.setEndMillis(serverEventTime.getEndStamp());
            eventTime.setRunDate(serverEventTime.getRunDate());
            eventTime.setLocation(serverEventTime.getEventPlace());
            eventTime.setCircleId(serverEventTime.getCircleId());
            eventTime.setEasemobGroupId(serverEventTime.getEasemobGroupId());
            eventTimeList.add(eventTime);
        }
        return eventTimeList;
    }

    // 转化View中绘制的event
    public static List<Event> toEvent(List<EventTime> eventTimes) {
        List<Event> events = new ArrayList<>();
        if (eventTimes == null || eventTimes.size() == 0) {
            events.add(new Event());
            return events;
        }
        for (EventTime eventTime : eventTimes) {
            Event event = new Event();
            event.scheduleId = eventTime.getScheduleId();
            event.createdUser = eventTime.getCreateUser();
            event.isCreateAuthor = eventTime.isCreatedAuthor();
            event.title = eventTime.getEventName();
            event.scheduleType = eventTime.getScheduleType();
            event.location = eventTime.getLocation();
            event.id =eventTime.getId();
            event.setStartMillis(eventTime.getStartMillis());
            event.setEndMillis(eventTime.getEndMillis());
            event.startTime = eventTime.getStartTime();
            event.endTime = eventTime.getEndTime();
            event.startDay = eventTime.getStartDay();
            event.endDay = eventTime.getEndDay();
            event.allDay = eventTime.isAllDay();
            event.color = parseColor(eventTime.getColor(), DEFAUT_COLOR);
            event.runDate = eventTime.getRunDate();
            event.circleId = eventTime.getCircleId();
            event.easemobGroupId = eventTime.getEasemobGroupId();
            events.add(event);
        }
        return events;
    }

    private static int parseColor(String color, String defaultColor) {
        try {
            return Color.parseColor(color);
        } catch (Exception e) {
            return Color.parseColor(defaultColor);
        }
    }

    private ScheduleCreateUser createUser;
    private boolean isCreatedAuthor;// 是否是创建者
    private String eventName;
    private String scheduleId;
    private int scheduleType;
    private String id;
    private String color;
    private boolean isAllDay;
    private long startMillis;
    private long endMillis;
    private int startTime;
    private int endTime;
    private int startDay;
    private int endDay;
    private String location;
    private String runDate;// 哪天的事件,参考用，本地无其它意义
    private String repeatType; // 0,1,2,3,4,5,6 是以这种类开回传的
    private  List<Weekday> mRepeatWeekdayList ;// 重复星期的集合
    private String startDateTime;
    private String endDateTime;
    private String circleId; //圈子Id
    private String easemobGroupId;//环信圈子ID

    public static final String[] repeats = new String[]{"每周日", "每周一", "每周二", "每周三", "每周四", "每周五", "每周六"};
    public static final String[] repeatsAbbre = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    public static final String REPEAT_WORK_DAY = "工作日";
    public static final String REPEAT_EVERY_DAY = "每天";

    public List<Weekday> getRepeatWeekdayList() {
        List<Weekday> weekdayList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            weekdayList.add(new Weekday(i, false, repeats[i], repeatsAbbre[i]));
        }
        String repeatTypeStr = getRepeatType();
        if (TextUtils.isEmpty(repeatType)) {
            return weekdayList;
        }
        String[] split = repeatTypeStr.split(",");
        for (int i = 0; i < split.length ; i++) {
            int dayPosition = 0;
            try {
                dayPosition = Integer.parseInt(split[i]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            weekdayList.get(dayPosition).isChecked = true;
        }
        return weekdayList;
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
    public boolean isCreatedAuthor() {
        return isCreatedAuthor;
    }

    public void setCreatedAuthor(boolean createdAuthor) {
        isCreatedAuthor = createdAuthor;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isAllDay() {
        return isAllDay;
    }

    public void setAllDay(boolean allDay) {
        isAllDay = allDay;
    }

    public long getStartMillis() {
        return startMillis;
    }

    public void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
        this.startDateTime = DateUtils.millisToDateString(startMillis);
    }

    public long getEndMillis() {
        return endMillis;
    }

    public void setEndMillis(long endMillis) {
        this.endMillis = endMillis;
        this.endDateTime = DateUtils.millisToDateString(endMillis);
    }

    public int getStartTime() {
        return isAllDay()? 0 :Utils.getMinutesInDayTime(getStartMillis());
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return isAllDay() ? Utils.DAY_IN_MINUTE :Utils.getMinutesInDayTime(getEndMillis());
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getStartDay() {
        return  DateUtils.millisToJulianDay(getStartMillis());
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getEndDay() {
        return DateUtils.millisToJulianDay(getEndMillis());
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public ScheduleCreateUser getCreateUser() {
        return createUser;
    }

    public void setCreateUser(ScheduleCreateUser createUser) {
        this.createUser = createUser;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(int scheduleType) {
        this.scheduleType = scheduleType;
    }

    // 得到String的日期格式,
    public String getStartDateString() {
        long startMillis = getStartMillis();
        if (startMillis == 0) {
            throw new IllegalArgumentException("startMillis must not be zero");
        }
        return DateUtils.millisToDateString(startMillis);
    }

    public String getEndDateString() {
        long endMillis = getEndMillis();
        if (endMillis == 0) {
            throw new IllegalArgumentException("startMillis must not be zero");
        }
        return DateUtils.millisToDateString(endMillis);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventTime eventTime = (EventTime) o;

        if (isCreatedAuthor != eventTime.isCreatedAuthor) return false;
        if (scheduleType != eventTime.scheduleType) return false;
        if (isAllDay != eventTime.isAllDay) return false;
        if (startMillis != eventTime.startMillis) return false;
        if (endMillis != eventTime.endMillis) return false;
        if (startTime != eventTime.startTime) return false;
        if (endTime != eventTime.endTime) return false;
        if (startDay != eventTime.startDay) return false;
        if (endDay != eventTime.endDay) return false;
        if (createUser != null ? !createUser.equals(eventTime.createUser) : eventTime.createUser != null)
            return false;
        if (eventName != null ? !eventName.equals(eventTime.eventName) : eventTime.eventName != null)
            return false;
        if (scheduleId != null ? !scheduleId.equals(eventTime.scheduleId) : eventTime.scheduleId != null)
            return false;
        if (id != null ? !id.equals(eventTime.id) : eventTime.id != null) return false;
        if (color != null ? !color.equals(eventTime.color) : eventTime.color != null) return false;
        if (location != null ? !location.equals(eventTime.location) : eventTime.location != null)
            return false;
        if (runDate != null ? !runDate.equals(eventTime.runDate) : eventTime.runDate != null)
            return false;
        if (repeatType != null ? !repeatType.equals(eventTime.repeatType) : eventTime.repeatType != null)
            return false;
        if (mRepeatWeekdayList != null ? !mRepeatWeekdayList.equals(eventTime.mRepeatWeekdayList) : eventTime.mRepeatWeekdayList != null)
            return false;
        if (startDateTime != null ? !startDateTime.equals(eventTime.startDateTime) : eventTime.startDateTime != null)
            return false;
        return endDateTime != null ? endDateTime.equals(eventTime.endDateTime) : eventTime.endDateTime == null;

    }

    @Override
    public int hashCode() {
        int result = createUser != null ? createUser.hashCode() : 0;
        result = 31 * result + (isCreatedAuthor ? 1 : 0);
        result = 31 * result + (eventName != null ? eventName.hashCode() : 0);
        result = 31 * result + (scheduleId != null ? scheduleId.hashCode() : 0);
        result = 31 * result + scheduleType;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (isAllDay ? 1 : 0);
        result = 31 * result + (int) (startMillis ^ (startMillis >>> 32));
        result = 31 * result + (int) (endMillis ^ (endMillis >>> 32));
        result = 31 * result + startTime;
        result = 31 * result + endTime;
        result = 31 * result + startDay;
        result = 31 * result + endDay;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (runDate != null ? runDate.hashCode() : 0);
        result = 31 * result + (repeatType != null ? repeatType.hashCode() : 0);
        result = 31 * result + (mRepeatWeekdayList != null ? mRepeatWeekdayList.hashCode() : 0);
        result = 31 * result + (startDateTime != null ? startDateTime.hashCode() : 0);
        result = 31 * result + (endDateTime != null ? endDateTime.hashCode() : 0);
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.createUser, flags);
        dest.writeByte(this.isCreatedAuthor ? (byte) 1 : (byte) 0);
        dest.writeString(this.eventName);
        dest.writeString(this.scheduleId);
        dest.writeInt(this.scheduleType);
        dest.writeString(this.id);
        dest.writeString(this.color);
        dest.writeByte(this.isAllDay ? (byte) 1 : (byte) 0);
        dest.writeLong(this.startMillis);
        dest.writeLong(this.endMillis);
        dest.writeInt(this.startTime);
        dest.writeInt(this.endTime);
        dest.writeInt(this.startDay);
        dest.writeInt(this.endDay);
        dest.writeString(this.location);
        dest.writeString(this.runDate);
        dest.writeString(this.repeatType);
        dest.writeList(this.mRepeatWeekdayList);
        dest.writeString(this.startDateTime);
        dest.writeString(this.endDateTime);
        dest.writeString(this.circleId);
        dest.writeString(this.easemobGroupId);
    }

    public EventTime() {
    }

    protected EventTime(Parcel in) {
        this.createUser = in.readParcelable(ScheduleCreateUser.class.getClassLoader());
        this.isCreatedAuthor = in.readByte() != 0;
        this.eventName = in.readString();
        this.scheduleId = in.readString();
        this.scheduleType = in.readInt();
        this.id = in.readString();
        this.color = in.readString();
        this.isAllDay = in.readByte() != 0;
        this.startMillis = in.readLong();
        this.endMillis = in.readLong();
        this.startTime = in.readInt();
        this.endTime = in.readInt();
        this.startDay = in.readInt();
        this.endDay = in.readInt();
        this.location = in.readString();
        this.runDate = in.readString();
        this.repeatType = in.readString();
        this.mRepeatWeekdayList = new ArrayList<Weekday>();
        in.readList(this.mRepeatWeekdayList, Weekday.class.getClassLoader());
        this.startDateTime = in.readString();
        this.endDateTime = in.readString();
        this.circleId = in.readString();
        this.easemobGroupId = in.readString();
    }

    public static final Creator<EventTime> CREATOR = new Creator<EventTime>() {
        @Override
        public EventTime createFromParcel(Parcel source) {
            return new EventTime(source);
        }

        @Override
        public EventTime[] newArray(int size) {
            return new EventTime[size];
        }
    };
}

package com.thinkcoo.mobile.model.entity.serverresponse;

import android.text.TextUtils;

import com.thinkcoo.mobile.utils.DateUtils;

import java.util.List;

/**
 * Created by Leevin
 * CreateTime: 2016/7/22  17:09
 */
public class ScheduleResponse {


    /**
     * accountId : 事件创建者id
     * accountName:事件创建者name
     * eventName : 事件名称
     * eventType : 事件类型
     * color : 颜色
     * eventUnit : 单位
     * remindWay : 提醒方式
     * remark : 备注
     * eventTimeEntity : [{"accountId":"用户ID","eventTimeId":"时段ID","eventId":"事件ID","isAllDay":"是否全天","eventPlace":"事件地点","beginDate":"","endDate":"","cycleValue":"0周天依次类推"}]
     */
    private String eventId;
    private String accountId;
    private String accountName;
    private String eventName;
    private String eventType;
    private String color;
    private String eventUnit;
    private String remindWay;
    private String remark;
    private String delEventTimeids;

    /**
     * eventId :事件的id
     * accountId : 用户ID
     * eventTimeId : 时段ID
     * eventId : 事件ID
     * isAllDay : 是否全天
     * eventPlace : 事件地点
     * beginDate :
     * endDate :
     * cycleValue : 0周天依次类推
     */

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    private List<EventTimeEntityBean> eventTimeEntity;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEventUnit() {
        return eventUnit;
    }

    public void setEventUnit(String eventUnit) {
        this.eventUnit = eventUnit;
    }

    public String getRemindWay() {
        return remindWay;
    }

    public void setRemindWay(String remindWay) {
        this.remindWay = remindWay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDelEventTimeids() {
        return delEventTimeids;
    }

    public void setDelEventTimeids(String delEventTimeids) {
        this.delEventTimeids = delEventTimeids;
    }

    public List<EventTimeEntityBean> getEventTimeEntity() {
        return eventTimeEntity;
    }

    public void setEventTimeEntity(List<EventTimeEntityBean> eventTimeEntity) {
        this.eventTimeEntity = eventTimeEntity;
    }

    public static class EventTimeEntityBean {
        private String accountId;
        private String eventTimeId;
        private String eventId;
        private String isAllDay;
        private String eventPlace;
        private String beginDate;
        private String endDate;
        private String cycleValue;

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
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

        public String getCycleValue() {
            return cycleValue;
        }

        public void setCycleValue(String cycleValue) {
            this.cycleValue = cycleValue;
        }

        public long getStartMillis() {
            String beginDate = getBeginDate();
            if (TextUtils.isEmpty(beginDate)) {
                return  -1;
            }
            return  DateUtils.DateStringTomillis(beginDate);
        }

        public long getEndMillis() {
            String endDate = getEndDate();
            if (TextUtils.isEmpty(endDate)) {
                return -1;
            }
            return DateUtils.DateStringTomillis(endDate);
        }
    }
}

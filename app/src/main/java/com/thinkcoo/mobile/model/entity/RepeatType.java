package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Leevin
 * CreateTime: 2016/7/19  10:20
 */
public class RepeatType implements Parcelable {
    /**
     * 后台定义 0,1,2,3,4,5,6 分别标记周天，周一，周二，周三，周四，周五，周六
     *
     */
    private boolean isRepeatSunday;
    private boolean isRepeatMonday ;
    private boolean isRepeatTuesday;
    private boolean isRepeatWednesday;
    private boolean isRepeatThursday;
    private boolean isRepeatFriday;
    private boolean isRepeatSaturday;

    public boolean isRepeatMonday() {
        return isRepeatMonday;
    }

    public void setRepeatMonday(boolean repeatMonday) {
        isRepeatMonday = repeatMonday;
    }

    public boolean isRepeatSunday() {
        return isRepeatSunday;
    }

    public void setRepeatSunday(boolean repeatSunday) {
        isRepeatSunday = repeatSunday;
    }

    public boolean isRepeatTuesday() {
        return isRepeatTuesday;
    }

    public void setRepeatTuesday(boolean repeatTuesday) {
        isRepeatTuesday = repeatTuesday;
    }

    public boolean isRepeatWednesday() {
        return isRepeatWednesday;
    }

    public void setRepeatWednesday(boolean repeatWednesday) {
        isRepeatWednesday = repeatWednesday;
    }

    public boolean isRepeatThursday() {
        return isRepeatThursday;
    }

    public void setRepeatThursday(boolean repeatThursday) {
        isRepeatThursday = repeatThursday;
    }

    public boolean isRepeatFriday() {
        return isRepeatFriday;
    }

    public void setRepeatFriday(boolean repeatFriday) {
        isRepeatFriday = repeatFriday;
    }

    public boolean isRepeatSaturday() {
        return isRepeatSaturday;
    }

    public void setRepeatSaturday(boolean repeatSaturday) {
        isRepeatSaturday = repeatSaturday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepeatType that = (RepeatType) o;

        if (isRepeatSunday != that.isRepeatSunday) return false;
        if (isRepeatMonday != that.isRepeatMonday) return false;
        if (isRepeatTuesday != that.isRepeatTuesday) return false;
        if (isRepeatWednesday != that.isRepeatWednesday) return false;
        if (isRepeatThursday != that.isRepeatThursday) return false;
        if (isRepeatFriday != that.isRepeatFriday) return false;
        return isRepeatSaturday == that.isRepeatSaturday;

    }

    @Override
    public int hashCode() {
        int result = (isRepeatSunday ? 1 : 0);
        result = 31 * result + (isRepeatMonday ? 1 : 0);
        result = 31 * result + (isRepeatTuesday ? 1 : 0);
        result = 31 * result + (isRepeatWednesday ? 1 : 0);
        result = 31 * result + (isRepeatThursday ? 1 : 0);
        result = 31 * result + (isRepeatFriday ? 1 : 0);
        result = 31 * result + (isRepeatSaturday ? 1 : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isRepeatSunday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isRepeatMonday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isRepeatTuesday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isRepeatWednesday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isRepeatThursday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isRepeatFriday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isRepeatSaturday ? (byte) 1 : (byte) 0);
    }

    public RepeatType() {
    }

    protected RepeatType(Parcel in) {
        this.isRepeatSunday = in.readByte() != 0;
        this.isRepeatMonday = in.readByte() != 0;
        this.isRepeatTuesday = in.readByte() != 0;
        this.isRepeatWednesday = in.readByte() != 0;
        this.isRepeatThursday = in.readByte() != 0;
        this.isRepeatFriday = in.readByte() != 0;
        this.isRepeatSaturday = in.readByte() != 0;
    }

    public static final Parcelable.Creator<RepeatType> CREATOR = new Parcelable.Creator<RepeatType>() {
        @Override
        public RepeatType createFromParcel(Parcel source) {
            return new RepeatType(source);
        }

        @Override
        public RepeatType[] newArray(int size) {
            return new RepeatType[size];
        }
    };
}

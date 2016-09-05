package com.thinkcoo.mobile.presentation.views.component.mydayview;

import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.widget.TextView;

import com.thinkcoo.mobile.model.entity.EventTime;
import com.thinkcoo.mobile.model.entity.Weekday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by  Leevin
 * on 2016/7/11 ,0:48.
 */
public class Utils {

    public static final long SECOND_IN_MILLIS = 1000;
    public static final long MINUTE_IN_MILLIS = SECOND_IN_MILLIS * 60;
    public static final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * 60;
    public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;
    public static final long WEEK_IN_MILLIS = DAY_IN_MILLIS * 7;
    public static final int HOUR_IN_MINUTE = 60;
    public static final int DAY_IN_MINUTE = HOUR_IN_MINUTE*24;
    /**
     * @return the first day of week in android.text.format.Time
     */
    public static int getFirstDayOfWeek(Context context) {
        int startDay;
        startDay = Calendar.getInstance().getFirstDayOfWeek();
        if (startDay == Calendar.SATURDAY) {
            return Time.SATURDAY;
        } else if (startDay == Calendar.MONDAY) {
            return Time.MONDAY;
        } else {
            return Time.SUNDAY;
        }
    }

    public static boolean isSaturday(int column, int firstDayOfWeek) {
        return (firstDayOfWeek == Time.SUNDAY && column == 6)
                || (firstDayOfWeek == Time.MONDAY && column == 5)
                || (firstDayOfWeek == Time.SATURDAY && column == 0);
    }

    public static boolean isSunday(int column, int firstDayOfWeek) {
        return (firstDayOfWeek == Time.SUNDAY && column == 0)
                || (firstDayOfWeek == Time.MONDAY && column == 6)
                || (firstDayOfWeek == Time.SATURDAY && column == 1);
    }


    public static void setDate(TextView view, long millis,Context context) {
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                | DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_MONTH;
//                | DateUtils.FORMAT_ABBREV_WEEKDAY;
        String dateString = DateUtils.formatDateTime(context, millis, flags);
        view.setText(dateString);
    }

    public static void  setTime(TextView view, long millis,Context context) {
        int flags = DateUtils.FORMAT_SHOW_TIME;
        if (DateFormat.is24HourFormat(context)) {
            flags |= DateUtils.FORMAT_24HOUR;
        }
        String timeString = DateUtils.formatDateTime(context, millis, flags);
        view.setText(timeString);
    }


    public static List<Integer> getRepeatPostion(List<Weekday> weekdayList){
        List<Integer> selectPostions = new ArrayList<>();
        for (int i = 0; i < weekdayList.size(); i++) {
            Weekday selectday = weekdayList.get(i);
            if (selectday.isChecked) {
                selectPostions.add(selectday.position);
            }
        }
        return selectPostions;
    }



    public static String getRepeatResultString(List<Weekday> weekdayList) {
        String result = "";
        List<Integer> repeatPostions = getRepeatPostion(weekdayList);
        if (repeatPostions.size() == 0) {
            return result;
        }

        if (repeatPostions.size() == 1) {
            return result = weekdayList.get(repeatPostions.get(0)).perWeekday;
        }

        if (isRepeatEveryday(weekdayList)) {
            return EventTime.REPEAT_EVERY_DAY;
        }

        if (isRepeatWorkday(weekdayList)) {
            return EventTime.REPEAT_WORK_DAY;
        }
        List<String> repeatDays = new ArrayList<>();
        for (Integer repeatPostion : repeatPostions) {
            String weekday = weekdayList.get(repeatPostion).weekday;
            repeatDays.add(weekday);
        }
        return result = list2String(repeatDays);
    }

    public static String getRepeatResult(List<Weekday> weekdayList) {
        List<Integer> repeatPostion = getRepeatPostion(weekdayList);
        return list2String(repeatPostion);
    }

    public static <T extends Object> String list2String(List<T> list) {
        if (list.isEmpty()) {
            return "";
        }
        StringBuilder buffer = new StringBuilder(list.size() * 16);
        Iterator<?> it =list.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next != it) {
                buffer.append(next);
            } else {
                buffer.append("(this Collection)");
            }
            if (it.hasNext()) {
                buffer.append(",");
            }
        }
        return buffer.toString();
    }

    public static boolean isRepeatWorkday(List<Weekday> repeatWeekdayList) {
        boolean flag = true;
        for (int i = 1; i < 6; i++) {
            flag &= repeatWeekdayList.get(i).isChecked;
        }
        return flag;
    }

    public static boolean isRepeatEveryday(List<Weekday> repeatWeekdayList){
        boolean flag = true;
        for (int i = 0; i < 7; i++) {
            flag &= repeatWeekdayList.get(i).isChecked;
        }
        return flag;
    }

    public static int getMinutesInDayTime(long timeMillis) {
        if (timeMillis == 0) {
            throw new IllegalArgumentException("please confirm endMillis value ,it cannot be 0");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        int minutes = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour * Utils.HOUR_IN_MINUTE + minutes;
    }

}

package com.thinkcoo.mobile.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.Time;
import android.text.style.ForegroundColorSpan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Leevin
 * CreateTime: 2016/6/6  15:19
 */
public class DateUtils {

    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND2 = "yyyy-MM-dd-HH:mm:ss";
    public static final String HOUR_MINUTE = "HH:mm";

    public static String dateToString(Date date, String pattern) {
        String format = null;
        try {
            format = new SimpleDateFormat(pattern).format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }

    public static Date stringToDate(String dateStr, String pattern) {
        Date parse = null;
        try {
            parse = new SimpleDateFormat(pattern).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    // 将毫秒值转化为“yyyy-MM-dd HH:mm:ss” 日期格式
    public static String millisToDateString(long timeMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        Date date = new Date(timeMillis);
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

    // 将“yyyy-MM-dd HH:mm:ss” 日期格式转化为毫秒值
    public static long DateStringTomillis(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
            Date date = dateFormat.parse(dateStr);
            long timeMillis = date.getTime();
            return timeMillis;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 将毫秒值转化为“yyyy-MM-dd ” 日期格式
    public static String millisToDateString2(long timeMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(YEAR_MONTH_DAY);
        Date date = new Date(timeMillis);
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

    public static String getDateFromJulianDay(int julianDay) {
        Time time = new Time();
        time.setJulianDay(julianDay);
        long startMillis = time.toMillis(true);
        return DateUtils.millisToDateString2(startMillis);
    }

    public static String millisToString3(long timeMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(YEAR_MONTH_DAY_HOUR_MINUTE_SECOND2);
        Date date = new Date(timeMillis);
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

    public static int millisToJulianDay(long timeMillis) {
        Time time = new Time();
        if (timeMillis == 0) {
            throw new IllegalArgumentException(" timeMillis cannot be  0 !!");
        }
        time.set(timeMillis);
        return Time.getJulianDay(timeMillis, time.gmtoff);
    }

    public static SpannableStringBuilder setTextForeColor(String content) {
        if (TextUtils.isEmpty(content)) {
            content = "";
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        if (!content.contains(":")) {
            return builder;
        }
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        int index = content.indexOf(":")+1;
        builder.setSpan(redSpan, index, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }

}

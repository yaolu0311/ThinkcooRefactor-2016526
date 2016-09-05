package com.thinkcoo.mobile.model.entity;

/**
 * Created by Leevin
 * CreateTime: 2016/8/2  17:21
 */
public class Weekday {
    public int position;
    public boolean isChecked;
    public String perWeekday;
    public String weekday;

    public Weekday(int position, boolean isChecked, String perWeekday, String weekday) {
        this.position = position;
        this.isChecked = isChecked;
        this.perWeekday = perWeekday;
        this.weekday = weekday;
    }

}

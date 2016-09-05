package com.thinkcoo.mobile.model.strategy;

/**
 * Created by Administrator on 2016/6/28.
 */
public class ScheduleStrategyFactory {

    public static final int SCHEDULE_TYPE_MANAGER = 0x0001; // 管理
    public static final int SCHEDULE_TYPE_COURSE = 0x0002;// 授课
    public static final int SCHEDULE_TYPE_LEARN = 0x0003;// 自学
    public static final int SCHEDULE_TYPE_ACTIVITY = 0x0004;// 活动

    public static final String SCHEDULE_MANAGER_COLOR = "#FF2D2D";
    public static final String SCHEDULE_COURSE_COLOR = "#46A3FF";
    public static final String SCHEDULE_LEARN_COLOR = "# 9AFF02";
    public static final String SCHEDULE_ACTIVITY_COLOR = "#FF5809";

    public static ScheduleStrategy create(int type){

        switch (type){
            case SCHEDULE_TYPE_MANAGER:
                return new ScheduleStrategyManager();
            case SCHEDULE_TYPE_COURSE:
                return new ScheduleStrategyCourse();
            case SCHEDULE_TYPE_ACTIVITY:
                return new ScheduleStrategyActivity();
            case SCHEDULE_TYPE_LEARN:
                return new ScheduleStrategyLearn();
            default:
                throw new IllegalArgumentException("unknown schedule type");
        }
    }

}

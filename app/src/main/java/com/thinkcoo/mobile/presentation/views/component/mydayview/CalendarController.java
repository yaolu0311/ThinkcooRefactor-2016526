package com.thinkcoo.mobile.presentation.views.component.mydayview;

import android.content.Context;
import android.text.format.DateUtils;
import android.text.format.Time;

import com.thinkcoo.mobile.model.entity.Schedule;
import com.thinkcoo.mobile.presentation.views.activitys.base.Navigator;

import java.util.WeakHashMap;

/**
 * Created by Leevin
 * CreateTime: 2016/7/12  8:29
 */
public class CalendarController {

    public static final long EXTRA_CREATE_ALL_DAY = 0x10;
    private Context mContext;
    private Time mTime = new Time();
    private static WeakHashMap<Context, CalendarController> instances = new WeakHashMap<>();

    Navigator mNavigator;
    /**
     * Creates and/or returns an instance of CalendarController associated with
     * the supplied context. It is best to pass in the current Activity.
     *
     * @param context The activity if at all possible.
     */
    public static CalendarController getInstance(Context context) {
        synchronized (instances) {
            CalendarController controller = instances.get(context);
            if (controller == null) {
                controller = new CalendarController(context);
                instances.put(context, controller);
            }
            return controller;
        }
    }

    private CalendarController(Context context) {
        mContext = context;
        mTime.setToNow();
    }

    public static void removeInstance(Context context) {
        instances.remove(context);
    }

    public Time getTime() {
        return mTime;
    }

    public void setTime(long timeMillis) {
        mTime.set(timeMillis);
    }

    public void setNavigator(Navigator navigator) {
        mNavigator = navigator;
    }

    public void gotoDayView(Time selectedTime) {
        mNavigator.navigateToDayViewActivity(mContext ,selectedTime);
    }


    public void createEvent(long startMillis, long endMillis, long extraLong) {
        mNavigator.navigateToEditScheduleToAdd(mContext,startMillis,endMillis,extraLong == EXTRA_CREATE_ALL_DAY);
    }

    public void viewEvent(Event selectedEvent) {
        switch (selectedEvent.scheduleType) {
            case Schedule.SCHEDULE_TYPE_MANAGER:
                mNavigator.navigateToMerbermanagerDetail(mContext, selectedEvent);
                break;
            case Schedule.SCHEDULE_TYPE_COURSE:
                mNavigator.navigateToCourseDetail(mContext, selectedEvent);
                break;
            case Schedule.SCHEDULE_TYPE_LEARN:
                mNavigator.navigateToSelfLeanDetail(mContext, selectedEvent);
                break;
            case Schedule.SCHEDULE_TYPE_ACTIVITY:
                mNavigator.navigateToActivityDetail(mContext, selectedEvent);
                break;
        }
    }

    public void updateTitle(Context context ,Time start, Time end, int formatFlags) {
        String mTitle = "";
        long startMillis = start.toMillis(true);
        mTitle = DateUtils.formatDateTime(context, startMillis, formatFlags);
        mTitleChangeListener.update(mTitle);
    }


    private TitleChangeListener mTitleChangeListener;

    public interface TitleChangeListener {
        void update(String title);
    }

    public void setTitleChangeListener(TitleChangeListener titleChangeListener) {
        this.mTitleChangeListener = titleChangeListener;
    }
}

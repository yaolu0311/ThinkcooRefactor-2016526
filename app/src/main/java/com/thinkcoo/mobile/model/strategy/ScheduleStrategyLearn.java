package com.thinkcoo.mobile.model.strategy;

import android.widget.RelativeLayout;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.Schedule;

/**
 * Created by Administrator on 2016/6/28.
 */
public class ScheduleStrategyLearn implements ScheduleStrategy {
    @Override
    public int getMoreLayoutId() {
        return R.layout.item_schedule_more_2;
    }

    @Override
    public String getCellColor() {
        return ScheduleStrategyFactory.SCHEDULE_LEARN_COLOR;
    }

    @Override
    public void buildDetailInfoView(RelativeLayout relativeLayout, Schedule schedule) {

    }

    @Override
    public boolean isNeedTimeIntervals(RelativeLayout relativeLayout) {
        return false;
    }

    @Override
    public void buildButtonView(RelativeLayout relativeLayout, Schedule schedule) {

    }
}

package com.thinkcoo.mobile.model.strategy;

import android.widget.RelativeLayout;
import com.thinkcoo.mobile.model.entity.Schedule;

/**
 * Created by Administrator on 2016/6/28.
 */
public interface ScheduleStrategy {

    /**
     * 不同的日程类型对应不同的更多布局
     */
    int getMoreLayoutId();

    /**
     *
     * 不同的日程显示格子的颜色不一样
     */
    String getCellColor();

    /**
     *不同的类别 详情信息视图显示不同，在这里自己编辑
     */
    void buildDetailInfoView(RelativeLayout relativeLayout, Schedule schedule);

    /**
     * 是否需要添加时段
     */
    boolean isNeedTimeIntervals(RelativeLayout relativeLayout );

    /**
     *
     */
    void buildButtonView(RelativeLayout relativeLayout, Schedule schedule);

}

package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.Schedule;

/**
 * Created by Leevin
 * CreateTime: 2016/6/30  10:38
 */
public interface AddEditScheduleView extends MvpView ,BaseHintView,BaseActivityHelpView{

    void setSchedule(Schedule schedule);
    Schedule getSchedule();
    void setResultOk(Schedule updatedSchedule);
    void closeActivity();
}

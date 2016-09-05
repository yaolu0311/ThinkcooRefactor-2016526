package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import java.util.List;

/**
 * Created by Leevin
 * CreateTime: 2016/7/27  15:53
 */
public interface EventView extends MvpView{
    void setEvents(List<Event> events);
}

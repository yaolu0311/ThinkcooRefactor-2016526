package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.Location;

/**
 * Created by Leevin
 * CreateTime: 2016/8/18  10:32
 */
public interface TrainHomeView extends MvpView,BaseHintView{

    void setLocation(Location location);
    void getLocationFailure();
}

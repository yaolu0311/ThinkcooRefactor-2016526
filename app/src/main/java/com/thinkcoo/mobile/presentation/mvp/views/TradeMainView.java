package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.Location;

/**
 * Created by Robert.yao on 2016/7/29.
 */
public interface TradeMainView extends MvpView{
    void setLocation(Location location);
    void getLocationFailure();

}

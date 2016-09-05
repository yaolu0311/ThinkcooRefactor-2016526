package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.Location;

/**
 * Created by Robert.yao on 2016/8/19.
 */
public interface GetJobMainView extends MvpView{

    void setLocation(Location location);

}

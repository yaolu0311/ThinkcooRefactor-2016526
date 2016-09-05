package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.presentation.mvp.views.IGoodsFilterView;

import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/2.
 */
public class GoodsFilterPresenter extends MvpBasePresenter<IGoodsFilterView>{

    @Inject
    public GoodsFilterPresenter() {
    }
    public void loadSchoolList(Location location) {

    }
}

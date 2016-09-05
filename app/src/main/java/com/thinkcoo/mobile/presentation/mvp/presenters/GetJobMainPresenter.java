package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.domain.location.GetLocationUseCase;
import com.thinkcoo.mobile.presentation.mvp.views.GetJobMainView;

import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/19.
 */
public class GetJobMainPresenter extends MvpBasePresenter<GetJobMainView> {

    GetLocationUseCase mGetLocationUseCase;

    @Inject
    public GetJobMainPresenter(GetLocationUseCase getLocationUseCase) {
        mGetLocationUseCase = getLocationUseCase;
    }

    public void startRequestLocation(){}
}

package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.domain.location.GetLocationUseCase;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.presentation.mvp.views.TradeMainView;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/7/29.
 */
public class TradeMainPresenter extends MvpBasePresenter<TradeMainView> {

    GetLocationUseCase mGetLocationUseCase;

    @Inject
    public TradeMainPresenter(GetLocationUseCase getLocationUseCase) {
        mGetLocationUseCase = getLocationUseCase;
    }
    public void getLocation(){
        mGetLocationUseCase.execute(getSub());
    }
    private Subscriber<Location> getSub() {

        return new Subscriber<Location>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()){
                    return;
                }
                getView().getLocationFailure();
            }
            @Override
            public void onNext(Location location) {
                if (!isViewAttached()){
                    return;
                }
                getView().setLocation(location);
            }
        };
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mGetLocationUseCase.unSubscribe();
    }
}

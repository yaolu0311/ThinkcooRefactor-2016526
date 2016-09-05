package com.thinkcoo.mobile.presentation.mvp;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.domain.user.QueryAreaUseCase;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WangYY on 2016/3/22.
 */
public class AddreassPresenter extends MvpBasePresenter<MvpView> {

    private QueryAreaUseCase mQueryAreaUseCase;

    @Inject
    public AddreassPresenter(QueryAreaUseCase queryAreaUseCase) {
        mQueryAreaUseCase = queryAreaUseCase;
    }

    //Todo
    public void getAreaList() {
        if (!isViewAttached()) {
            return;
        }

        mQueryAreaUseCase.execute(getQueryAreaUserCaseSub());
    }

    private Subscriber getQueryAreaUserCaseSub() {
        return new Subscriber() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        };
    }


}

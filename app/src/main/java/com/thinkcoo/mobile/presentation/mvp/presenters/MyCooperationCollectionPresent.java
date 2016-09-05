package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.domain.cooperation.MyCooperationCollectionCase;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * author ：ml on 2016/7/22
 */
public class MyCooperationCollectionPresent extends MvpBasePresenter<MvpView> {

    private MyCooperationCollectionCase mMyCooperationCollectionCase;
    private ErrorMessageFactory mErrorMessageFactory;

    @Inject
    public MyCooperationCollectionPresent(MyCooperationCollectionCase myCooperationCollectionCase, ErrorMessageFactory errorMessageFactory) {
        this.mMyCooperationCollectionCase = myCooperationCollectionCase;
        this.mErrorMessageFactory = errorMessageFactory;
    }

    ;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        // TODO: 2016/7/22 解绑
    }


    public void loadCooperationCollect() {
        if (!isViewAttached()) {
            return;
        }
        mMyCooperationCollectionCase.execute(getMyCooperationCollectionSub());
    }

    public Subscriber getMyCooperationCollectionSub() {
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

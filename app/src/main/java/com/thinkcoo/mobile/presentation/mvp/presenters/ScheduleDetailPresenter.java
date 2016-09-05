package com.thinkcoo.mobile.presentation.mvp.presenters;


import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.domain.schedule.DeleteventCase;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseScheduleDetailActivity;


import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/6/28.
 */
public class ScheduleDetailPresenter extends MvpBasePresenter<BaseScheduleDetailActivity> {
    private DeleteventCase mDeleteventCase;
    private ErrorMessageFactory mErrorMessageFactory;
    @Inject
    public ScheduleDetailPresenter(DeleteventCase deleteventCase, ErrorMessageFactory errorMessageFactory) {
        this.mDeleteventCase = deleteventCase;
        this.mErrorMessageFactory = errorMessageFactory;

    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mDeleteventCase.unSubscribe();

    }

    public void Deletevent(String eventId) {
        if (!isViewAttached()) {
            return;
        }
        mDeleteventCase.execute(getmDeleteventCaseSub(),eventId);
    }

    public Subscriber getmDeleteventCaseSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
            getView().closeSelf();
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

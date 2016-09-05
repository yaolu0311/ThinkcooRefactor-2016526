package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.domain.cooperation.LoadCooperationDataCase;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.FindCooperationMainActivityView;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * author ：ml on 2016/7/22
 */
public class FindCooperationMainPresent extends MvpBasePresenter<MvpView> {
    private LoadCooperationDataCase mLoadCooperationDataCase;
    private ErrorMessageFactory mErrorMessageFactory;

    @Inject
    public FindCooperationMainPresent(LoadCooperationDataCase loadCooperationDataCase, ErrorMessageFactory errorMessageFactory) {
        this.mLoadCooperationDataCase = loadCooperationDataCase;
        this.mErrorMessageFactory = errorMessageFactory;
    }

    public FindCooperationMainActivityView getFindCooperationMainActivityView() {
        return (FindCooperationMainActivityView) getView();
    };

    public void loadCooperationData() {
        if (!isViewAttached()) {
            return;
        }
        mLoadCooperationDataCase.execute(getLoadCooperationData());

    }
public Subscriber getLoadCooperationData(){
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

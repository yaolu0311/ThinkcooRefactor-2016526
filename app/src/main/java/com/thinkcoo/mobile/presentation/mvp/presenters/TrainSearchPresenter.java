package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.domain.train.LoadAreaUseCase;
import com.thinkcoo.mobile.model.entity.Address;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.TrainSearchView;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Leevin
 * CreateTime: 2016/8/18  13:41
 */
public class TrainSearchPresenter extends MvpBasePresenter<TrainSearchView> {

    private static final String TAG = "TrainSearchPresenter";
    LoadAreaUseCase mLoadAreaUseCase;
    ErrorMessageFactory mErrorMessageFactory;

    @Inject
    public TrainSearchPresenter(LoadAreaUseCase loadAreaUseCase, ErrorMessageFactory errorMessageFactory) {
        mLoadAreaUseCase = loadAreaUseCase;
        mErrorMessageFactory = errorMessageFactory;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mLoadAreaUseCase.unSubscribe();
    }

    public void loadArea(String code) {
        if (!isViewAttached()) {
            return;
        }
        mLoadAreaUseCase.execute(getLoadAreaUseCaseSub(),code);
    }

    private Subscriber getLoadAreaUseCaseSub() {
        return new Subscriber<List<Address>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                ThinkcooLog.e(TAG,e.getMessage(),e);
            }

            @Override
            public void onNext(List<Address> addresses) {
                if (!isViewAttached()) {
                    return;
                }
                getView().setArea(addresses);
            }
        };
    }
}

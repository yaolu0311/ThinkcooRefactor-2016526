package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.domain.location.GetLocationUseCase;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.TrainHomeView;
import javax.inject.Inject;
import rx.Subscriber;

/**
 * Created by Leevin
 * CreateTime: 2016/8/18  9:43
 */
public class LoadLocationPresenter extends MvpBasePresenter<TrainHomeView> {

    GetLocationUseCase mGetLocationUseCase;
    ErrorMessageFactory mErrorMessageFactory;

    @Inject
    public LoadLocationPresenter(GetLocationUseCase getLocationUseCase, ErrorMessageFactory errorMessageFactory) {
        mGetLocationUseCase = getLocationUseCase;
        mErrorMessageFactory = errorMessageFactory;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mGetLocationUseCase.unSubscribe();
    }


    public void getLocation() {
        if (!isViewAttached()) {
            return;
        }
    mGetLocationUseCase.execute(getLocationUseCaseSub());
    }

    private Subscriber getLocationUseCaseSub() {
        return new Subscriber<Location>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().getLocationFailure();
            }

            @Override
            public void onNext(Location location) {
                if (!isViewAttached()) {
                    return;
                }
                getView().setLocation(location);
            }
        };
    }
}

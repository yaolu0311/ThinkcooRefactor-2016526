package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.user.GetUserBasicInfoUseCase;
import com.thinkcoo.mobile.model.entity.UserBasicInfo;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.UserMainView;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WangYY 16/3/22.
 */
public class UserMainPresenter extends MvpBasePresenter<UserMainView>{

    GetUserBasicInfoUseCase mGetUserBasicInfoUseCase;
    ErrorMessageFactory mErrorMessageFactory;
    UserBasicInfo mUserBasicInfo;

    @Inject
    public UserMainPresenter(GetUserBasicInfoUseCase getUserBasicInfoUseCase, ErrorMessageFactory errorMessageFactory ) {
        this.mGetUserBasicInfoUseCase = getUserBasicInfoUseCase;
        this.mErrorMessageFactory = errorMessageFactory;
    }

    public void getUserBasicInfo(boolean isUpdateNow){
        if (!isViewAttached()){
            return;
        }
        mGetUserBasicInfoUseCase.execute(getUserBasicInfoSub(),isUpdateNow);
    }

    public UserMainView getUserMainView(){
        return getView();
    }

    private Subscriber getUserBasicInfoSub () {
        return new Subscriber<UserBasicInfo>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
                ThinkcooLog.e("---->", throwable.getMessage(), throwable);
                if (!isViewAttached()) {
                    return;
                }
                getUserMainView().showToast(mErrorMessageFactory.createErrorMsg(throwable));
            }

            @Override
            public void onNext(UserBasicInfo userBasicInfo) {
                if (!isViewAttached()) {
                    return;
                }
                mUserBasicInfo = userBasicInfo;
                getUserMainView().setUserMainInfo(userBasicInfo);
            }
        };

    }


    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mGetUserBasicInfoUseCase.unSubscribe();
    }
}

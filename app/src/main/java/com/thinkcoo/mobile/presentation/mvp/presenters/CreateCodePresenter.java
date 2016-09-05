package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.user.GetUserBasicInfoUseCase;
import com.thinkcoo.mobile.domain.user.InviteFriendUseCase;
import com.thinkcoo.mobile.model.entity.UserBasicInfo;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.CreateCodeView;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/3/22.
 */
public class CreateCodePresenter extends MvpBasePresenter<CreateCodeView> {


    GetUserBasicInfoUseCase mGetUserBasicInfoUseCase;
    ErrorMessageFactory mErrorMessageFactory;


    @Inject
    public CreateCodePresenter(GetUserBasicInfoUseCase getUserBasicInfoUseCase, ErrorMessageFactory errorMessageFactory ) {
        this.mGetUserBasicInfoUseCase = getUserBasicInfoUseCase;

        this.mErrorMessageFactory = errorMessageFactory;
    }

    public void getUserBasicInfo(boolean isUpdateNow){
        if (!isViewAttached()){
            return;
        }
        getView().showProgressDialog(R.string.loading);
        mGetUserBasicInfoUseCase.execute(getUserBasicInfoSub(),isUpdateNow);
    }



    private Subscriber getUserBasicInfoSub () {
        return new Subscriber<UserBasicInfo>() {
            @Override
            public void onCompleted() {
                getView().hideProgressDialogIfShowing();
            }

            @Override
            public void onError(Throwable throwable) {
                ThinkcooLog.e("---->", throwable.getMessage(), throwable);
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(throwable));
            }

            @Override
            public void onNext(UserBasicInfo userBasicInfo) {
                if (!isViewAttached()) {
                    return;
                }
                getView().setUserMainInfo(userBasicInfo);
            }
        };

    }

}

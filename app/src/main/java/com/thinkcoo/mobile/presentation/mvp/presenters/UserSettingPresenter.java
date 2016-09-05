package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.account.LogoutUseCase;
import com.thinkcoo.mobile.presentation.mvp.views.UserSettingView;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/8/17.
 */
public class UserSettingPresenter extends MvpBasePresenter<UserSettingView>{

    LogoutUseCase mLogoutUseCase;

    @Inject
    public UserSettingPresenter(LogoutUseCase logoutUseCase) {
        mLogoutUseCase = logoutUseCase;
    }

    public void logout(){
        getView().showProgressDialog(R.string.logouting);
        mLogoutUseCase.execute(getLogoutSub());
    }

    private Subscriber getLogoutSub() {
        return new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()){
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showProgressDialog(R.string.logout_failuer);
            }

            @Override
            public void onNext(String o) {
                getView().hideProgressDialogIfShowing();
                getView().gotoLogin(o);
            }
        };
    }

}

package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.BuildConfig;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.account.LoginUseCase;
import com.thinkcoo.mobile.domain.user.UserEnvironmentInitUseCase;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.LoginView;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/3/22.
 */
public class LoginPresenter extends MvpBasePresenter<MvpView> {


    @Inject
    LoginUseCase mUserLoginUseCase;
    @Inject
    UserEnvironmentInitUseCase mUserEnvironmentInitUseCase;
    @Inject
    ErrorMessageFactory mErrorMessageFactory;
    @Inject
    InputCheckUtil mInputCheckUtil;

    @Inject
    public LoginPresenter(LoginUseCase userLoginUseCase, UserEnvironmentInitUseCase userInitEnvironmentUseCase, ErrorMessageFactory errorMessageFactory,
                          InputCheckUtil inputCheckUtil) {
        this.mUserLoginUseCase = userLoginUseCase;
        this.mUserEnvironmentInitUseCase = userInitEnvironmentUseCase;
        this.mErrorMessageFactory = errorMessageFactory;
        this.mInputCheckUtil = inputCheckUtil;
    }

    public void login() {
        if (!isViewAttached()) {
            return;
        }
        if (BuildConfig.DEBUG){
//            getLoginView().setPhoneNumber("17792089656");
//            getLoginView().setPassword("abc123456");
        }
        Account account = createAccountFromView();
        if (checkInputPass(account)) {
            getLoginView().showProgressDialog(R.string.loading);
            mUserLoginUseCase.execute(getLoginSub(), account);
        }
    }

    public LoginView getLoginView() {
        return (LoginView) getView();
    }

    private Subscriber getLoginSub() {
        return new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                if (!isViewAttached()) {
                    return;
                }
                getLoginView().hideProgressDialogIfShowing();
                getLoginView().showToast(mErrorMessageFactory.createErrorMsg(throwable));
            }

            @Override
            public void onNext(User user) {
                if (!isViewAttached()) {
                    return;
                }
                initEnvironment(user);
            }
        };
    }

    public void initEnvironment(User user) {
        mUserEnvironmentInitUseCase.execute(getUserInitEnvironmentSub(), user);
    }

    private Subscriber getUserInitEnvironmentSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getLoginView().hideProgressDialogIfShowing();
                getLoginView().gotoHomePage();
                mUserEnvironmentInitUseCase.unSubscribe();
            }

            @Override
            public void onError(Throwable throwable) {
                if (!isViewAttached()) {
                    return;
                }
                getLoginView().hideProgressDialogIfShowing();
                getLoginView().showToast(mErrorMessageFactory.createErrorMsg(throwable));
            }

            @Override
            public void onNext(Object o) {

            }
        };
    }

    private Account createAccountFromView() {
        String accountName = getLoginView().getPhoneNumber();
        String password = getLoginView().getPassword();
        return new Account(accountName, false, password);
    }

    private boolean checkInputPass(Account account) {

        if (TextUtils.isEmpty(account.getAccountName())) {
            getLoginView().showToast(getString(R.string.account_must_be_not_empty));
            return false;
        }
        if (TextUtils.isEmpty(account.getPassword())) {
            getLoginView().showToast(getString(R.string.password_must_be_not_empty));
            return false;
        }
        if (!mInputCheckUtil.checkPhoneNumber(account.getAccountName())) {
            getLoginView().showToast(getString(R.string.account_name_format_error));
            return false;
        }
        if (mInputCheckUtil.checkPassword(account.getPassword())) {
            getLoginView().showToast(getString(R.string.account_format_password_fail));
            return false;
        }
        return true;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mUserLoginUseCase.unSubscribe();
        mUserEnvironmentInitUseCase.unSubscribe();
    }

    public String getString(int resId) {
        return getLoginView().getActivityContext().getString(resId);
    }
}

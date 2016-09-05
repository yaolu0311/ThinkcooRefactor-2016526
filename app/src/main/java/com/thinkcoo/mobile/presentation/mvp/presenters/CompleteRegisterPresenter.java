package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.account.AutoLoginUseCase;
import com.thinkcoo.mobile.domain.account.CompleteRegisterUseCase;
import com.thinkcoo.mobile.domain.account.LoginUseCase;
import com.thinkcoo.mobile.domain.user.UserEnvironmentInitUseCase;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.CompleteRegisterView;
import com.thinkcoo.mobile.utils.InputCheckUtil;
import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/5/24.
 */
public class CompleteRegisterPresenter extends MvpBasePresenter<MvpView> {

    private final static String CODE="860661";

    CompleteRegisterUseCase mCompleteRegisterUseCase;
    AutoLoginUseCase mAutoLoginUseCase;
    InputCheckUtil mInputCheckUtil;
    ErrorMessageFactory mErrorMessageFactory;

    private Account mAccount;

    @Inject
    public CompleteRegisterPresenter(CompleteRegisterUseCase completeRegisterUseCase,AutoLoginUseCase autoLoginUseCase,
                                     InputCheckUtil inputCheckUtil, ErrorMessageFactory errorMessageFactory) {
        this.mCompleteRegisterUseCase = completeRegisterUseCase;
        this.mInputCheckUtil = inputCheckUtil;
        this.mErrorMessageFactory = errorMessageFactory;
        mAutoLoginUseCase = autoLoginUseCase;
    }

    private CompleteRegisterView getCompleteRegisterView() {
        return (CompleteRegisterView) getView();
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mCompleteRegisterUseCase.unSubscribe();
        mAutoLoginUseCase.unSubscribe();
    }

    public void completeAccountRegister(String accountName) {
        if (!isViewAttached()) {
            return;
        }
        mAccount = createAccount(accountName);
        if (!checkPasswordFormat() || !checkUserName()) {
            return;
        }
        getCompleteRegisterView().showProgressDialog(R.string.loading);
        mCompleteRegisterUseCase.execute(getCompleteRegisterSub(), mAccount, CODE,getCompleteRegisterView().getUserName());

    }

    private Account createAccount(String accountName) {
        return new Account(accountName, false,getCompleteRegisterView().getPassWord());
    }

    private boolean checkPasswordFormat() {

        if (null == mAccount || TextUtils.isEmpty(mAccount.getPassword())) {
            getCompleteRegisterView().showToast(getString(R.string.password_is_empty));
            return false;
        }
        if (mInputCheckUtil.checkPassword(mAccount.getPassword())) {
            getCompleteRegisterView().showToast(getString(R.string.password_format_error));
            return false;
        }
        return true;
    }

    private boolean checkUserName() {
        String userName = getCompleteRegisterView().getUserName();
        if (TextUtils.isEmpty(userName)) {
            getCompleteRegisterView().showToast(getString(R.string.username_is_empty));
            return false;
        }
        return true;
    }

    private Subscriber getCompleteRegisterSub(){

        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getCompleteRegisterView().hideProgressDialogIfShowing();
                mAutoLoginUseCase.execute(getAutoLoginSub(),mAccount);
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getCompleteRegisterView().hideProgressDialogIfShowing();
                getCompleteRegisterView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(Void v) {

            }
        };
    }

    private Subscriber getAutoLoginSub(){
        return new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getCompleteRegisterView().showToast("登录失败,请再次尝试登录");
                getCompleteRegisterView().gotoLoginPage(mAccount);
            }

            @Override
            public void onNext(Object object) {
                if (!isViewAttached()) {
                    return;
                }
                getCompleteRegisterView().gotoHomePage();
            }
        };
    }

    private String getString(int resId){
       return getCompleteRegisterView().getActivityContext().getString(resId);
    }
}

package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.account.RequestFindPasswordUseCase;
import com.thinkcoo.mobile.domain.account.RequestVcodeUseCase;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.CheckVcode;
import com.thinkcoo.mobile.model.entity.Vcode;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.RequestFindPasswordView;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WangYY on 2016/5/24.
 */
public class RequestFindPasswordPresenter extends MvpBasePresenter<MvpView> {

    private  RequestVcodeUseCase mRequestVcodeUseCase;
    private RequestFindPasswordUseCase mRequestFindPasswordUseCase;
    private ErrorMessageFactory mErrorMessageFactory;
    private InputCheckUtil mInputCheckUtil;
    private Vcode mResponseVcode;

    @Inject
    public RequestFindPasswordPresenter(RequestVcodeUseCase requestVcodeUseCase, RequestFindPasswordUseCase requestFindPasswordUseCase, ErrorMessageFactory errorMessageFactory, InputCheckUtil inputCheckUtil) {
        this.mRequestVcodeUseCase = requestVcodeUseCase;
        this.mRequestFindPasswordUseCase = requestFindPasswordUseCase;
        this.mErrorMessageFactory = errorMessageFactory;
        this.mInputCheckUtil = inputCheckUtil;
    }

    public RequestFindPasswordView getRequestFindPasswordView() {
        return (RequestFindPasswordView) getView();
    }

    public void requestObtainVcode() {
        if (!isViewAttached()) {
            return;
        }
        if (!checkAccount()) {
            return;
        }
        getRequestFindPasswordView().startVcodeCountDown();
        mRequestVcodeUseCase.execute(requestVcodeUseCaseSub(), getAccountFromView());
    }

    public void requestFindPassword() {
        if (!isViewAttached()) {
            return;
        }
        if (!checkAccount() || !checkVcode()) {
            return;
        }
        getRequestFindPasswordView().showProgressDialog(R.string.loading);
        mRequestFindPasswordUseCase.execute(requestFindPasswordUseCaseSub(), getAccountFromView(), mResponseVcode);
    }

    private Subscriber requestVcodeUseCaseSub () {
        return new Subscriber<Vcode>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getRequestFindPasswordView().showToast(mErrorMessageFactory.createErrorMsg(e));
                getRequestFindPasswordView().stopVcodeCountDown();
            }

            @Override
            public void onNext(Vcode vcode) {
                mResponseVcode = vcode;
            }
        };
    }
    private Subscriber requestFindPasswordUseCaseSub () {
        return new Subscriber<CheckVcode>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e("请求找回密码----------》", e.getMessage());
                if (!isViewAttached()) {
                    return;
                }
                getRequestFindPasswordView().hideProgressDialogIfShowing();
                getRequestFindPasswordView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(CheckVcode checkVcode) {
                if (!isViewAttached()) {
                    return;
                 }
                getRequestFindPasswordView().hideProgressDialogIfShowing();
                getRequestFindPasswordView().gotoCompleteFindPasswordPage(checkVcode);
            }
        };
    }
    private boolean checkVcode() {
        String vcodeContent = getRequestFindPasswordView().getVcodeContent();
        if (TextUtils.isEmpty(vcodeContent)) {
            getRequestFindPasswordView().showToast(getString(R.string.vcode_must_be_not_empty));
            return false;
        }

        if (!vcodeContent.equals(mResponseVcode.getContent())) {
            getRequestFindPasswordView().showToast(getString(R.string.vcode_error));
            return false;
        }
        return true;
    }

    private boolean checkAccount() {
        String accountName = getRequestFindPasswordView().getAccountName();
        if (TextUtils.isEmpty(accountName)) {
            getRequestFindPasswordView().showToast(getString(R.string.accout_name_must_be_not_empty));
            return false;
        }

        if (!mInputCheckUtil.checkPhoneNumber(accountName)) {
            getRequestFindPasswordView().showToast(getString(R.string.account_name_format_error));
            return false;
        }
        return true;
    }

    public Account getAccountFromView() {
        Account account = new Account(getRequestFindPasswordView().getAccountName(), false,"");
        return account;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mRequestVcodeUseCase.unSubscribe();
        mRequestFindPasswordUseCase.unSubscribe();
    }

    private String getString(int resId){
        return getRequestFindPasswordView().getActivityContext().getString(resId);
    }
}

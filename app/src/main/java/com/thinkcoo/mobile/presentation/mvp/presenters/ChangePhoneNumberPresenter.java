package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.account.ChangePhoneNumberUseCase;
import com.thinkcoo.mobile.domain.account.GetOldPhoneNumberUseCase;
import com.thinkcoo.mobile.domain.account.RequestVcodeUseCase;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.Vcode;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.ChangePhoneNumberView;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Leevin
 * CreateTime: 2016/8/8  10:25
 */
public class ChangePhoneNumberPresenter extends MvpBasePresenter<ChangePhoneNumberView> {

    private RequestVcodeUseCase mRequestVcodeUseCase;
    private GetOldPhoneNumberUseCase mGetOldPhoneNumberUseCase;
    private ChangePhoneNumberUseCase mChangePhoneNumberUseCase;
    private ErrorMessageFactory mErrorMessageFactory;
    private InputCheckUtil mInputCheckUtil;
    private Vcode mResponseVcode;
    private String mOldPhoneNumbe;

    @Inject
    public ChangePhoneNumberPresenter(RequestVcodeUseCase requestVcodeUseCase, GetOldPhoneNumberUseCase getOldPhoneNumberUseCase, ChangePhoneNumberUseCase changePhoneNumberUseCase, ErrorMessageFactory errorMessageFactory, InputCheckUtil inputCheckUtil) {
        mRequestVcodeUseCase = requestVcodeUseCase;
        mGetOldPhoneNumberUseCase = getOldPhoneNumberUseCase;
        mChangePhoneNumberUseCase = changePhoneNumberUseCase;
        mErrorMessageFactory = errorMessageFactory;
        mInputCheckUtil = inputCheckUtil;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mRequestVcodeUseCase.unSubscribe();
        mGetOldPhoneNumberUseCase.unSubscribe();
        mChangePhoneNumberUseCase.unSubscribe();
    }


    public void getOldPhoneNumber() {
        if (!isViewAttached()) {
            return;
        }
        mGetOldPhoneNumberUseCase.execute(getOldPhoneNumberSub());
    }

    private Subscriber getOldPhoneNumberSub() {
        return new Subscriber<Account>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(Account account) {
                if (!isViewAttached()) {
                    return;
                }
                mOldPhoneNumbe = account.getAccountName();
                getView().setOldPhoneNumber(mOldPhoneNumbe);
            }
        };
    }

    public void requestVcode() {
        if (!isViewAttached()) {
            return;
        }
        String newPhoneNumber = getView().getNewPhoneNumber();
        if (!checkPhoneNumber(newPhoneNumber)) {
            return;
        }
        getView().startVcodeCountDown();
        mRequestVcodeUseCase.execute(getRequestVcodeUseCaseSub(), new Account(newPhoneNumber,false,""));
    }

    private Subscriber getRequestVcodeUseCaseSub() {
        return new Subscriber<Vcode>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
                getView().stopVcodeCountDown();
            }

            @Override
            public void onNext(Vcode vcode) {
                if (!isViewAttached()) {
                    return;
                }
                mResponseVcode = vcode;
                getView().stopVcodeCountDown();
            }
        };
    }

    public void cummit() {
        if (!isViewAttached()) {
            return;
        }
        String newPhoneNumber = getView().getNewPhoneNumber();
        String password = getView().getLoginPassword();
        if (!checkPhoneNumber(newPhoneNumber) || !checkVcode() || !checkPassword(password)) {
            return;
        }
        getView().showProgressDialog(R.string.commiting);
        mChangePhoneNumberUseCase.execute(getChangePhoneNumSub(),new Account(newPhoneNumber,false,password),mResponseVcode,mOldPhoneNumbe);
    }

    private Subscriber getChangePhoneNumSub() {
        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(Void aVoid) {

            }
        };
    }

    private boolean checkVcode() {
        String vcodeContent = getView().getVcodeContent();
        if (TextUtils.isEmpty(vcodeContent)) {
            getView().showToast(getString(R.string.vcode_must_be_not_empty));
            return false;
        }

        if (!vcodeContent.equals(mResponseVcode.getContent())) {
            getView().showToast(getString(R.string.vcode_error));
            return false;
        }

        return true;
    }

    private boolean checkPhoneNumber(String newPhoneNumber) {
        if (TextUtils.isEmpty(newPhoneNumber)) {
            getView().showToast(getString(R.string.accout_name_must_be_not_empty));
            return false;
        }

        if (!mInputCheckUtil.checkPhoneNumber(newPhoneNumber)) {
            getView().showToast(getString(R.string.account_name_format_error));
            return false;
        }
        return true;
    }

    private boolean checkPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            getView().showToast(getString(R.string.password_is_empty));
            return false;
        }

        return true;
    }

    public String getString(int resId){
        return getView().getActivityContext().getString(resId);
    }
}

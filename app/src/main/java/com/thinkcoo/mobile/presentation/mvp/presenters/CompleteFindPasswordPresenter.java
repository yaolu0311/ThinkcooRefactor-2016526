package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;
import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.account.CompleteFindPasswordUseCase;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.CheckVcode;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.CompleteFindPasswordView;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Wangyy on 2016/5/24.
 */
public class CompleteFindPasswordPresenter extends MvpBasePresenter<MvpView> {

    CompleteFindPasswordUseCase mCompleteFindPasswordUseCase;
    ErrorMessageFactory mErrorMessageFactory;
    InputCheckUtil mInputCheckUtil;

    @Inject
    public CompleteFindPasswordPresenter(CompleteFindPasswordUseCase completeFindPasswordUseCase, InputCheckUtil inputCheckUtil, ErrorMessageFactory errorMessageFactory) {
        this.mCompleteFindPasswordUseCase = completeFindPasswordUseCase;
        this.mInputCheckUtil = inputCheckUtil;
        this.mErrorMessageFactory = errorMessageFactory;
    }
    public void commitNewPassword(CheckVcode checkVcode) {
        if (!isViewAttached()) {
            return;
        }
        if (!checkInputpassWord(getCompleteFindPasswordView().getPassword())) {
            return;
        }
        getCompleteFindPasswordView().showProgressDialog(R.string.loading);
        mCompleteFindPasswordUseCase.execute(reSetNEwPasswordSub(), checkVcode,getCompleteFindPasswordView().getPassword());
    }

    private Subscriber reSetNEwPasswordSub () {
        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                getCompleteFindPasswordView().gotoLoginPage();
            }

            @Override
            public void onError(Throwable throwable) {
                Log.wtf("设置新密码", throwable.getMessage(), throwable);
                if (!isViewAttached()) {
                    return;
                }
                getCompleteFindPasswordView().hideProgressDialogIfShowing();
                getCompleteFindPasswordView().showToast(mErrorMessageFactory.createErrorMsg(throwable));
            }

            @Override
            public void onNext(Void aVoid) {
                if (!isViewAttached()) {
                    return;
                }
            }


        };
    }
    public CompleteFindPasswordView getCompleteFindPasswordView() {
        return (CompleteFindPasswordView) getView();
    }

    private boolean checkInputpassWord(String passWord) {

        if (TextUtils.isEmpty(passWord)) {
            getCompleteFindPasswordView().showToast(getString(R.string.password_must_be_not_empty));
            return false;
        }

        if (mInputCheckUtil.checkPassword(passWord)) {
            getCompleteFindPasswordView().showToast(getString(R.string.account_format_password_fail));
            return false;
        }
        return true;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mCompleteFindPasswordUseCase.unSubscribe();
    }

    private String getString(int resId){
       return getCompleteFindPasswordView().getActivityContext().getString(resId);
    }
}

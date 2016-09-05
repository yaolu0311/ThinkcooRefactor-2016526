package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.user.ModifyPasswordUseCase;
import com.thinkcoo.mobile.presentation.mvp.views.ModifyPasswordView;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by cys on 2016/8/8
 */
public class ModifyPasswordPresenter extends MvpBasePresenter<ModifyPasswordView>{

    private ModifyPasswordUseCase mUseCase;
    private InputCheckUtil mInputCheckUtil;

    @Inject
    public ModifyPasswordPresenter(ModifyPasswordUseCase useCase, InputCheckUtil inputCheckUtil) {
        mUseCase = useCase;
        this.mInputCheckUtil = inputCheckUtil;
    }

    public void modifyPassword(String oldPwd, String newPwd){
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(oldPwd)) {
            getView().showToast(getView().getActivityContext().getString(R.string.input_old_password));
            return;
        }
        if (!checkInputpassWord(newPwd)) {
            getView().showToast(getView().getActivityContext().getString(R.string.input_new_password));
            return;
        }

        getView().showProgressDialog(R.string.commiting);
        mUseCase.execute(getSub(), oldPwd, newPwd);
    }

    private boolean checkInputpassWord(String passWord) {
        if (TextUtils.isEmpty(passWord)) {
            getView().showToast(getView().getActivityContext().getString(R.string.input_new_password));
            return false;
        }

        if (mInputCheckUtil.checkPassword(passWord)) {
            getView().showToast(getView().getActivityContext().getString(R.string.account_format_password_fail));
            return false;
        }
        return true;
    }

    private Subscriber getSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().goToLoginPage();
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(getView().getActivityContext().getString(R.string.modify_failture));
            }

            @Override
            public void onNext(Object o) {

            }
        };
    }

    @Override
    public void detachView(boolean retainInstance) {
        mUseCase.unSubscribe();
    }
}

package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.user.ChangeUserSingatureUseCase;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.SignatureView;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WangYY on 2016/3/22.
 */
public class SignaturePresenter extends MvpBasePresenter<MvpView> {


    @Inject
    ChangeUserSingatureUseCase mChangeUserSingatureUseCase;
    //@Inject
    //InitUserEnvironmentUseCase initUserEnvironmentUseCase;
    @Inject
    ErrorMessageFactory mErrorMessageFactory;
    @Inject
    InputCheckUtil mInputCheckUtil;

    @Inject
    public SignaturePresenter(ChangeUserSingatureUseCase changeUserSingatureUseCase, ErrorMessageFactory errorMessageFactory, InputCheckUtil inputCheckUtil) {
        this.mChangeUserSingatureUseCase = changeUserSingatureUseCase;
        this.mErrorMessageFactory = errorMessageFactory;
        this.mInputCheckUtil = inputCheckUtil;
    }

    public void updateSignature() {
        if (!isViewAttached()) {
            return;
        }
        getSignatureView().showProgressDialog(R.string.loading);
        mChangeUserSingatureUseCase.execute(changeUserSingatureSub(), getSignatureView().getSignature());
    }

    public SignatureView getSignatureView() {
        return (SignatureView) getView();
    }

    private Subscriber changeUserSingatureSub() {
        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                getSignatureView().hideProgressDialogIfShowing();
                getSignatureView().resultToUserMainInfoActivity();
            }

            @Override
            public void onError(Throwable throwable) {
                if (!isViewAttached()) {
                    return;
                }
                getSignatureView().hideProgressDialogIfShowing();
                getSignatureView().showToast(mErrorMessageFactory.createErrorMsg(throwable));
            }

            @Override
            public void onNext(Void aVoid) {
                if (!isViewAttached()) {
                    return;
                }
            }


        };
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mChangeUserSingatureUseCase.unSubscribe();
    }
}

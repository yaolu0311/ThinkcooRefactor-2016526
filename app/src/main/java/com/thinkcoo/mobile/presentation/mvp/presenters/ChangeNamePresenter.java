package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.user.ChangeUserNameCase;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.ChangeNameView;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WangYY on 2016/3/22.
 */
public class ChangeNamePresenter extends MvpBasePresenter<MvpView> {


    @Inject
    ChangeUserNameCase mChangeUserNameCase;
    @Inject
    ErrorMessageFactory mErrorMessageFactory;
    @Inject
    InputCheckUtil mInputCheckUtil;

    @Inject
    public ChangeNamePresenter(ChangeUserNameCase changeUserNameCase, ErrorMessageFactory errorMessageFactory, InputCheckUtil inputCheckUtil) {
        this.mChangeUserNameCase = changeUserNameCase;
        this.mErrorMessageFactory = errorMessageFactory;
        this.mInputCheckUtil = inputCheckUtil;
    }

    public void changeName() {
        if (!isViewAttached()) {
            return;
        }
        if(TextUtils.isEmpty( getChangeNameView().getName())){
            getChangeNameView().showToast(getChangeNameView().getActivityContext().getString(R.string.qingshuruxingming));
            return;
        }
        getChangeNameView().showProgressDialog(R.string.loading);
        mChangeUserNameCase.execute(changeUserNameSub(), getChangeNameView().getName());
    }

    public ChangeNameView getChangeNameView() {
        return (ChangeNameView) getView();
    }

    private Subscriber changeUserNameSub() {
        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                getChangeNameView().hideProgressDialogIfShowing();
                getChangeNameView().resultToUserMainInfoActivity();
            }

            @Override
            public void onError(Throwable throwable) {
                if (!isViewAttached()) {
                    return;
                }
                getChangeNameView().hideProgressDialogIfShowing();
                getChangeNameView().showToast(mErrorMessageFactory.createErrorMsg(throwable));
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
        mChangeUserNameCase.unSubscribe();
    }
}

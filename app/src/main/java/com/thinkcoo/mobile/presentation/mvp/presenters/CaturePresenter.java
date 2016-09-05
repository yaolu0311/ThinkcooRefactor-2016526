package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.user.InviteFriendUseCase;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.CaptureView;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Wyy on 2016/3/22.
 */
public class CaturePresenter extends MvpBasePresenter<CaptureView> {

    ErrorMessageFactory mErrorMessageFactory;
    InviteFriendUseCase mInviteFriendUseCase;

    @Inject
    public CaturePresenter(InviteFriendUseCase inviteFriendUseCase, ErrorMessageFactory errorMessageFactory ) {
        this.mInviteFriendUseCase = inviteFriendUseCase;
        this.mErrorMessageFactory = errorMessageFactory;
    }

    public void inviteFriend(String invitedUserId){
        if (!isViewAttached()){
            return;
        }
        getView().showProgressDialog(R.string.loading);
        mInviteFriendUseCase.execute(inviteFriendSub(),invitedUserId);
    }

    private Subscriber inviteFriendSub () {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                getView().hideProgressDialogIfShowing();
                getView().closeSelf();
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
            public void onNext(Object o) {
                if (!isViewAttached()) {
                    return;
                }

            }
        };

    }


    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mInviteFriendUseCase.unSubscribe();
    }
}

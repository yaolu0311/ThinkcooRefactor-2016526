package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.schedule.AddMerberCase;
import com.thinkcoo.mobile.domain.schedule.SerchStudentsCase;
import com.thinkcoo.mobile.domain.user.FeedBackCase;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.FeedBackView;
import com.thinkcoo.mobile.presentation.mvp.views.ManualAddView;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/7/4.
 */
public class FeedBackPresenter extends MvpBasePresenter<FeedBackView> {
    @Inject
    FeedBackCase mFeedBackCase;

    ErrorMessageFactory mErrorMessageFactory;
    @Inject
    InputCheckUtil mInputCheckUtil;
    private static final String TAG = "FeedBackPresenter";

    @Inject
    public FeedBackPresenter(FeedBackCase feedBackCase, ErrorMessageFactory  errorMessageFactory, InputCheckUtil inputCheckUtil) {
        this.mFeedBackCase = feedBackCase;
        this.mErrorMessageFactory = errorMessageFactory;
        this.mInputCheckUtil = inputCheckUtil;
    }

    public void feedBack(String feedbackContent, String contact) {
        getView().showProgressDialog(R.string.loading);
        if (mInputCheckUtil.checkPhoneNumber(contact)) {
            mFeedBackCase.execute(feedBackSub(), feedbackContent, contact, "");
        } else if (mInputCheckUtil.checkEmail(contact)) {
            mFeedBackCase.execute(feedBackSub(), feedbackContent, "", contact);
        }

    }



    private Subscriber feedBackSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().closeSelf();
            }

            @Override
            public void onError(Throwable throwable) {
                if (!isViewAttached()) {
                    return;
                }
                                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(throwable));
            }

            @Override
            public void onNext(Object o) {

            }
        };
    }
}




package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.schedule.AddNoticeCase;
import com.thinkcoo.mobile.domain.schedule.GetNoticeListCase;
import com.thinkcoo.mobile.model.entity.EventNoticeEntity;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.NoticeView;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/8/5.
 */
public class NoticePresenter extends MvpBasePresenter<NoticeView> {
    @Inject
    GetNoticeListCase mGetNoticeListCase;
    @Inject
    AddNoticeCase mAddNoticeCase;
    @Inject
    ErrorMessageFactory mErrorMessageFactory;
    @Inject
    InputCheckUtil mInputCheckUtil;
    private static final String TAG = "NoticePresenter";

    @Inject
    public NoticePresenter(GetNoticeListCase getNoticeListCase, AddNoticeCase addNoticeCase, ErrorMessageFactory errorMessageFactory, InputCheckUtil inputCheckUtil) {
        this.mGetNoticeListCase = getNoticeListCase;
        this.mAddNoticeCase = addNoticeCase;
        this.mErrorMessageFactory = errorMessageFactory;
        this.mInputCheckUtil = inputCheckUtil;
    }

    public void getNoticeList() {
        getView().showProgressDialog(R.string.loading);
        mGetNoticeListCase.execute(getNoticeListSub(), getView().getEvent().scheduleId);
    }


    private Subscriber<List<EventNoticeEntity>> getNoticeListSub() {

        return new Subscriber<List<EventNoticeEntity>>() {


            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG, e.getMessage(), e);
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(List<EventNoticeEntity> studentList) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().setNoticeList(studentList);
            }
        };
    }

    public void addNotice() {
        getView().showProgressDialog(R.string.loading);
        mAddNoticeCase.execute(addNoticeSub(), getView().getEvent().scheduleId, getView().getContent());
    }

    private Subscriber addNoticeSub() {

        return new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG, e.getMessage(), e);
                if (!isViewAttached()){
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(Object o) {
                if (!isViewAttached()){
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getNoticeList();
            }
        };
    }


    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mAddNoticeCase.unSubscribe();
        mGetNoticeListCase.unSubscribe();
    }
}

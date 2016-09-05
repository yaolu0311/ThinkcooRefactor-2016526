package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.schedule.AddMerberCase;
import com.thinkcoo.mobile.domain.schedule.SerchStudentsCase;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.ActivityMerberView;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/7/27.
 */
public class ActiveMemberPresenter extends MvpBasePresenter<ActivityMerberView> {
    @Inject
    SerchStudentsCase mSerchStudentsCase;
    @Inject
    AddMerberCase mAddMerberCase;
    @Inject
    ErrorMessageFactory mErrorMessageFactory;
    @Inject
    InputCheckUtil mInputCheckUtil;
    private static final String TAG = "ManualAddPresenter";
    @Inject
    public ActiveMemberPresenter(SerchStudentsCase serchStudentsCase, AddMerberCase addMerberCase, ErrorMessageFactory errorMessageFactory, InputCheckUtil inputCheckUtil) {
        this.mSerchStudentsCase = serchStudentsCase;
        this.mAddMerberCase = addMerberCase;
        this.mErrorMessageFactory = errorMessageFactory;
        this.mInputCheckUtil = inputCheckUtil;
    }

    public void addMerberToGroup(String eventId, String groupid,String accountIds) {
        getView().showProgressDialog(R.string.loading);
        mAddMerberCase.execute(addMerberToGroupSub(), eventId, groupid, accountIds);
    }

    private Subscriber addMerberToGroupSub() {

        return new Subscriber() {
            @Override
            public void onCompleted() {
                getView().hideProgressDialogIfShowing();
                getView().setResult();
                getView().closeSelf();
            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG, e.getMessage(), e);
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(Object o) {

            }


        };
    }





    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mAddMerberCase.unSubscribe();
        mSerchStudentsCase.unSubscribe();
    }

}

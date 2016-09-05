package com.thinkcoo.mobile.presentation.mvp.presenters;


import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.schedule.CreateClassCase;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.CreateActivityGroupView;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/7/4.
 */
public class CreatActivityGroupPresenter extends MvpBasePresenter<CreateActivityGroupView> {
    public static final String TAG = "CreateClassPresenter";
    CreateClassCase mCreateClassCase;
    ErrorMessageFactory mErrorMessageFactory;
    List<Student> mStudentList;

    @Inject
    public CreatActivityGroupPresenter(CreateClassCase createtCase, ErrorMessageFactory errorMessageFactoryrror) {
        mCreateClassCase = createtCase;
        mErrorMessageFactory = errorMessageFactoryrror;

    }


    public void createClass(String eventId, String schoolName, String className) {
        getView().showProgressDialog(R.string.loading);
        mCreateClassCase.execute(createClassSub(), eventId, schoolName, className);
    }


    private Subscriber createClassSub() {

        return new Subscriber<String>() {
            @Override
            public void onCompleted() {
                getView().hideProgressDialogIfShowing();
            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG, e.getMessage(), e);
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(String groupid) {

                getView().toActiveMemberActivity(groupid);
            }


        };
    }


    @Override
    public void detachView(boolean retainInstance) {
        mCreateClassCase.unSubscribe();

    }
}

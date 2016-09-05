package com.thinkcoo.mobile.presentation.mvp.presenters;


import android.view.View;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.schedule.AddMerberCase;
import com.thinkcoo.mobile.domain.schedule.CreateClassCase;
import com.thinkcoo.mobile.domain.schedule.SerchStudentsCase;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.CreateClassView;
import com.thinkcoo.mobile.presentation.views.activitys.StudentManageActivity;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/7/4.
 */
public class CreateClassPresenter extends MvpBasePresenter<CreateClassView> {
    public static final String TAG = "CreateClassPresenter";

    SerchStudentsCase mSerchStudentsCase;
    CreateClassCase mCreateClassCase;
    AddMerberCase mAddMerberCase;
    ErrorMessageFactory mErrorMessageFactory;
    List<Student> mStudentList;
    @Inject
    public CreateClassPresenter(SerchStudentsCase serchStudentsCase, CreateClassCase createtCase, AddMerberCase addMerberCase,ErrorMessageFactory errorMessageFactoryrror) {
        mSerchStudentsCase = serchStudentsCase;
        mCreateClassCase = createtCase;
        mAddMerberCase = addMerberCase;
        mErrorMessageFactory = errorMessageFactoryrror;

    }


    private Subscriber<List<Student>> serchStudentsSub() {

        return new Subscriber<List<Student>>() {


            @Override
            public void onCompleted() {
                getView().hideProgressDialogIfShowing();
            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG, e.getMessage(), e);
                getView().getSerchLayout().setVisibility(View.GONE);
                getView().getNoSerchLayout().setVisibility(View.VISIBLE);
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(List<Student> studentList) {
                if (!isViewAttached()) {
                    return;
                }
                getView().getSerchLayout().setVisibility(View.VISIBLE);
                getView().getNoSerchLayout().setVisibility(View.GONE);
                mStudentList = studentList;
                getView().setStudents(studentList);
            }
        };
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

                if(!getView().getSelectStudentId().equals("")){
                    addMerberToGroup(getView().getevent().scheduleId,groupid,getView().getSelectStudentId());
                }else {
                    getView().setResult();
                    getView().closeSelf();
                }
            }


        };
    }


    public void addMerberToGroup(String eventId, String groupid,String accountIds) {
        getView().showProgressDialog(R.string.loading);
        mAddMerberCase.execute(addMerberToGroupSub(), eventId, groupid, accountIds);
    }

//    private String getaccountIds(List<Student> listStudent) {
//        String accountIds="";
//        for(int i=0;i<mStudentList.size();i++){
//            if(mStudentList.get(i).isCheck()==true){
//                accountIds+=mStudentList.get(i).getAccountId()+",";
//            }
//        }
//        ThinkcooLog.d(TAG,accountIds);
//        if(accountIds.equals("")){
//            return accountIds;
//        }
//        return accountIds.substring(0,accountIds.length()- 1);
//    }


    private Subscriber addMerberToGroupSub() {

        return new Subscriber() {
            @Override
            public void onCompleted() {
                getView().setResult();
                getView().closeSelf();
                getView().hideProgressDialogIfShowing();
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
        mSerchStudentsCase.unSubscribe();
        mCreateClassCase.unSubscribe();
        mAddMerberCase.unSubscribe();
    }
}

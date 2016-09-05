package com.thinkcoo.mobile.presentation.mvp.presenters;


import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.schedule.ConfirmStudentListCase;
import com.thinkcoo.mobile.domain.schedule.GetClassListCase;
import com.thinkcoo.mobile.domain.schedule.GetStudentListCase;
import com.thinkcoo.mobile.domain.schedule.RemoveClassCase;
import com.thinkcoo.mobile.model.entity.ClassGroup;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.StudentManageView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WYY
 * CreateTime: 2016/6/14  11:04
 */
public class StudentManagePresenter extends MvpBasePresenter<StudentManageView> {

    public static final String TAG = "StudentManagePresenter";

    GetClassListCase mGetClassListCase;
    GetStudentListCase mGetStudentListCase;
    ConfirmStudentListCase mConfirmStudentListCase;
    RemoveClassCase mRemoveClassCase;
    ErrorMessageFactory mErrorMessageFactory;
    List<ClassGroup> mClassGroupList;
    @Inject
    public StudentManagePresenter(GetClassListCase getClassListCase, GetStudentListCase getStudentListCase, RemoveClassCase removeClassCase, ConfirmStudentListCase ConfirmStudentListCase, ErrorMessageFactory errorMessageFactory) {
        mGetClassListCase = getClassListCase;
        mGetStudentListCase = getStudentListCase;
        mConfirmStudentListCase = ConfirmStudentListCase;
        mRemoveClassCase = removeClassCase;
        mErrorMessageFactory = errorMessageFactory;
    }
    public void loadClassList(String eventId,boolean isChange) {
        getView().showProgressDialog(R.string.loading);

        mGetClassListCase.execute(loadClsssSub(isChange),eventId);
    }


    private Subscriber<List<ClassGroup>> loadClsssSub(final boolean isChange) {

        return new Subscriber<List<ClassGroup>>() {
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
            public void onNext(List<ClassGroup> classGroupList) {
                if (!isViewAttached()) {
                    return;
                }
                mClassGroupList = classGroupList;
                if (isChange){
                    getView().setSelectClassPosion(classGroupList.size()-1);
                }
                getView().setClassList(classGroupList);

                if(classGroupList.size()>0) {
                    loadStudentList(getView().getEvent().scheduleId, classGroupList.get(getView().getSelectClassPosion()).getGroupId());
                }else{
                    getView().setStudentList(Collections.EMPTY_LIST,false);
                }
            }
        };
    }
    public void removeClass(String groupId) {
        getView().showProgressDialog(R.string.loading);

        mRemoveClassCase.execute(removeClassSub(),groupId);
    }


    private Subscriber removeClassSub() {

        return new Subscriber() {
            @Override
            public void onCompleted() {
                loadClassList(getView().getEvent().scheduleId,true);
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
    
    public void loadStudentList(String eventId,String groupId) {
        getView().showProgressDialog(R.string.loading);

        mGetStudentListCase.execute(loadStudentSSub(),eventId,groupId);
    }


    private Subscriber<List<Student>> loadStudentSSub() {

        return new Subscriber<List<Student>>() {
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
            public void onNext(List<Student> studentList) {
                if (!isViewAttached()) {
                    return;
                }
               getView().setStudentList(studentList,false);
            }
        };
    }

    public void confirmStudentList(String eventId,String groupId,String accountIds) {
        getView().showProgressDialog(R.string.loading);

        mConfirmStudentListCase.execute(confirmStudentListSub(),eventId,groupId,accountIds);
    }


    private Subscriber<List<Student>> confirmStudentListSub() {

        return new Subscriber() {
            @Override
            public void onCompleted() {
                loadStudentList(getView().getEvent().scheduleId, mClassGroupList.get(getView().getSelectClassPosion()).getGroupId());// TODO: 2016/7/15//// TODO: 2016/7/4
//                getView().hideProgressDialogIfShowing();
            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG, e.getMessage(), e);
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
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
        mGetClassListCase.unSubscribe();
        mGetStudentListCase.unSubscribe();
        mConfirmStudentListCase.unSubscribe();
        mRemoveClassCase.unSubscribe();
    }
}

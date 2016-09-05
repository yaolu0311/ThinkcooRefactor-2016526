package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.train.DeleteAppointmentOrCollectionUseCase;
import com.thinkcoo.mobile.domain.train.LoadTrainAppointmentListUseCase;
import com.thinkcoo.mobile.model.entity.TrainCourse;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.TrainAppointmentView;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * author ：ml on 2016/7/27
 */
public class MyAppointmentPresenter extends MvpBasePresenter<TrainAppointmentView> {

    private static final String TAG = "MyAppointmentPresenter";
    private LoadTrainAppointmentListUseCase mLoadTrainAppointmentListUseCase;
    private DeleteAppointmentOrCollectionUseCase mDeleteAppointmentOrCollectionUseCase;
    private ErrorMessageFactory mMessageFactory;

    @Inject
    public MyAppointmentPresenter(LoadTrainAppointmentListUseCase loadTrainAppointmentListUseCase, DeleteAppointmentOrCollectionUseCase deleteAppointmentOrCollectionUseCase, ErrorMessageFactory messageFactory) {
        mLoadTrainAppointmentListUseCase = loadTrainAppointmentListUseCase;
        mDeleteAppointmentOrCollectionUseCase = deleteAppointmentOrCollectionUseCase;
        mMessageFactory = messageFactory;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mLoadTrainAppointmentListUseCase.unSubscribe();
        mDeleteAppointmentOrCollectionUseCase.unSubscribe();
    }


    public void loadMyAppointmentList() {
        if (!isViewAttached()) {
            return;
        }
        mLoadTrainAppointmentListUseCase.execute(getAppointmentSub());
    }

    public Subscriber getAppointmentSub(){
        return new Subscriber<List<TrainCourse>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().showError(e,true);
                ThinkcooLog.e(TAG,e.getMessage(),e);
            }

            @Override
            public void onNext(List<TrainCourse> trainCourseEntities) {
                if (!isViewAttached()) {
                    return;
                }
                getView().setDataList(trainCourseEntities);
            }
        };

    }

    public void deleteMyAppointment() {
        if (!isViewAttached()) {
            return;
        }
        getView().showProgressDialog(R.string.loading);
        mDeleteAppointmentOrCollectionUseCase.execute(getDeleteAppointmentSub());
    }

    private Subscriber getDeleteAppointmentSub() {
        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(getString(R.string.delete_success));
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(getString(R.string.delete_failture));
                ThinkcooLog.e(TAG,e.getMessage(),e);
            }

            @Override
            public void onNext(Void aVoid) {
            }
        };
    }

    public String getString(int resId){
        return getView().getActivityContext().getString(resId);
    }
}

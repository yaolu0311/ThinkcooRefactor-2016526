package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.schedule.GetClassListCase;
import com.thinkcoo.mobile.model.entity.ClassGroup;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.ScoreAnalysisView;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/8/20.
 */
public class ScoreAnalysisPresenter extends MvpBasePresenter<ScoreAnalysisView> {

    public static final String TAG = "StudentManagePresenter";

    GetClassListCase mGetClassListCase;
    ErrorMessageFactory mErrorMessageFactory;
    @Inject
    public ScoreAnalysisPresenter(GetClassListCase getClassListCase , ErrorMessageFactory errorMessageFactory) {
        mGetClassListCase = getClassListCase;
        mErrorMessageFactory = errorMessageFactory;
    }
    public void loadClassList(String eventId) {
        getView().showProgressDialog(R.string.loading);

        mGetClassListCase.execute(loadClsssSub(),eventId);
    }


    private Subscriber<List<ClassGroup>> loadClsssSub() {

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

                getView().setClassList(classGroupList);


            }
        };
    }









    @Override
    public void detachView(boolean retainInstance) {
        mGetClassListCase.unSubscribe();

    }
}
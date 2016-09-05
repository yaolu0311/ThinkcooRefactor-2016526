package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.schedule.LoadClassListCase;
import com.thinkcoo.mobile.model.entity.ClassGroup;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.RockCallResultView;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * author ：ml on 2016/7/12
 */
public class RockCallResultPresenter extends MvpBasePresenter<RockCallResultView> {

    LoadClassListCase mLoadClassListCase;
    ErrorMessageFactory mErrorMessageFactory;

    @Inject
    public RockCallResultPresenter(LoadClassListCase loadClassListCase,ErrorMessageFactory errorMessageFactory) {
        mLoadClassListCase = loadClassListCase;
        mErrorMessageFactory = errorMessageFactory;
    }

    public void loadClassListData(String eventId) {
        getView().showProgressDialog(R.string.loading);
        mLoadClassListCase.execute(getLoadClassListDataSub(),eventId);
    }

    private Subscriber getLoadClassListDataSub() {

        return new Subscriber<List<ClassGroup>>(){

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e("SEE",e.getMessage(),e);
                if (!isViewAttached()){
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
                getView().closeSelf();
            }

            @Override
            public void onNext(List<ClassGroup> classResponses) {
                if (!isViewAttached()){
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().setClassList(classResponses);
            }
        };
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mLoadClassListCase.unSubscribe();
    }




}

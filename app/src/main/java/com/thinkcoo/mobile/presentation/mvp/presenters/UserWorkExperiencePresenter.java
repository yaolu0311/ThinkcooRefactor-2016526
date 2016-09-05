package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.user.UserDeleteWorkExperienceUseCase;
import com.thinkcoo.mobile.domain.user.UserWorkExperienceUseCase;
import com.thinkcoo.mobile.model.entity.UserWorkExperienceEntity;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.UserWorkExperienceView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/6/17.
 */
public class UserWorkExperiencePresenter extends MvpBasePresenter<MvpView>{

    UserWorkExperienceUseCase mExperienceUseCase;
    UserDeleteWorkExperienceUseCase mUserDeleteWorkExperienceUseCase;
    ErrorMessageFactory mErrorMessageFactory;

    @Inject
    public UserWorkExperiencePresenter(UserWorkExperienceUseCase experienceUseCase, UserDeleteWorkExperienceUseCase userDeleteWorkExperienceUseCase, ErrorMessageFactory errorMessageFactory) {
        mExperienceUseCase = experienceUseCase;
        mUserDeleteWorkExperienceUseCase = userDeleteWorkExperienceUseCase;
        mErrorMessageFactory = errorMessageFactory;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mExperienceUseCase.unSubscribe();
    }

    private UserWorkExperienceView getUserWorkExperienceView() {
        return (UserWorkExperienceView) getView();
    }

    public void loadWorkExperienceList(String workId){
        if (!isViewAttached()) {
            return;
        }
        if (TextUtils.isEmpty(workId)) {
            return;
        }
        getUserWorkExperienceView().showProgressDialog(R.string.loading);
        mExperienceUseCase.execute(getExperienceList(), workId);
    }

    private Subscriber getExperienceList() {
        return new Subscriber<List<UserWorkExperienceEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getUserWorkExperienceView().hideProgressDialogIfShowing();
                getUserWorkExperienceView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(List<UserWorkExperienceEntity> list) {
                if (!isViewAttached()) {
                    return;
                }
                getUserWorkExperienceView().hideProgressDialogIfShowing();
                if (null == list) {
                    list = new ArrayList<>();
                }
                getUserWorkExperienceView().setData(list);
            }
        };
    }

    public void deleteWorkExperience(String workId, String experienceId) {
        if (!isViewAttached()) {
            return;
        }
        if (TextUtils.isEmpty(experienceId)) {
            return;
        }
        getUserWorkExperienceView().showProgressDialog(R.string.deleting);
        mUserDeleteWorkExperienceUseCase.execute(getDeleteExperienceSub(workId), experienceId);
    }

    private Subscriber getDeleteExperienceSub(final String workId) {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                loadWorkExperienceList(workId);
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getUserWorkExperienceView().hideProgressDialogIfShowing();
                getUserWorkExperienceView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(Object o) {

            }
        };
    }
}

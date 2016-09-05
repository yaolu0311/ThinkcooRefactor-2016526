package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.user.AddUserExperienceUseCase;
import com.thinkcoo.mobile.domain.user.UpdateUserExperienceUseCase;
import com.thinkcoo.mobile.model.entity.UserWorkExperienceEntity;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.UserExperienceDetailView;
import com.thinkcoo.mobile.presentation.views.activitys.UserWorkExperienceDetailActivity;
import com.thinkcoo.mobile.utils.FieldCheckUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/6/17.
 */
public class UserExperienceDetailPresenter extends MvpBasePresenter<UserExperienceDetailView> {

    AddUserExperienceUseCase mAddUserExperienceUseCase;
    UpdateUserExperienceUseCase mUpdateUserExperienceUseCase;
    ErrorMessageFactory mErrorMessageFactory;
    FieldCheckUtil mFieldCheckUtil;

    @Inject
    public UserExperienceDetailPresenter(AddUserExperienceUseCase addUserExperienceUseCase, UpdateUserExperienceUseCase updateUserExperienceUseCase, ErrorMessageFactory errorMessageFactory, FieldCheckUtil fieldCheckUtil) {
        mAddUserExperienceUseCase = addUserExperienceUseCase;
        mUpdateUserExperienceUseCase = updateUserExperienceUseCase;
        mErrorMessageFactory = errorMessageFactory;
        mFieldCheckUtil = fieldCheckUtil;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mAddUserExperienceUseCase.unSubscribe();
        mUpdateUserExperienceUseCase.unSubscribe();
    }

    public void addExperience(String workId, String time, String content) {
        if (!isViewAttached()) {
            return;
        }
        if (TextUtils.isEmpty(workId)) {
            getView().showToast(getView().getActivityContext().getString(R.string.failture_operate));
            return;
        }

        UserWorkExperienceEntity entity = getExperienceEntity("", time, content);
        if (!checkFieldIsEmpty(entity)) {
            return;
        }

        getView().showProgressDialog(R.string.uploading);
        mAddUserExperienceUseCase.execute(getAddExperienceSub(), workId, content, time);
    }

    public Subscriber getAddExperienceSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(getView().getActivityContext().getString(R.string.do_action_success));
                getView().closeSelf();
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(Object o) {

            }
        };
    }

    private UserWorkExperienceEntity getExperienceEntity(String experienceId, String time, String content) {
        UserWorkExperienceEntity entity = new UserWorkExperienceEntity();
        entity.setTime(time);
        entity.setContent(content);
        entity.setId(experienceId);
        return entity;
    }

    private boolean checkFieldIsEmpty(UserWorkExperienceEntity entity) {
        return mFieldCheckUtil.checkEntityFieldsFormat(getView().getActivityContext(), entity, getView());
    }

    public void updateExperience(String experienceId, String time, String content) {
        if (!isViewAttached()) {
            return;
        }

        UserWorkExperienceEntity entity = getExperienceEntity(experienceId, time, content);
        if (!checkFieldIsEmpty(entity)) {
            return;
        }

        if (checkIsEqualOldEntity(entity)) {
            getView().showToast(getView().getActivityContext().getString(R.string.update_success));
            getView().closeSelf();
            return;
        }
        getView().showProgressDialog(R.string.waiting);
        mUpdateUserExperienceUseCase.execute(getModifyExperienceSub(), entity);
    }

    private boolean checkIsEqualOldEntity(UserWorkExperienceEntity entity) {
        if (null == entity) {
            return false;
        }
        UserWorkExperienceEntity oldEntity = ((UserWorkExperienceDetailActivity) getView().getActivityContext()).getResponse();
        if (null == oldEntity) {
            return false;
        }
        return oldEntity.equals(entity);
    }

    public Subscriber getModifyExperienceSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(getView().getActivityContext().getString(R.string.do_action_success));
                getView().closeSelf();
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(Object o) {
            }
        };
    }
}

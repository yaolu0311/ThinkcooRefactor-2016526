package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.thinkcoo.mobile.domain.user.AddUserSkillCase;
import com.thinkcoo.mobile.domain.user.DeleteUserSkillCase;
import com.thinkcoo.mobile.domain.user.UserSkillCase;
import com.thinkcoo.mobile.model.entity.UserSkill;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/5/30.
 */
public class UserSkillPresenter extends MvpBasePresenter<MvpView> {

    private static final String TAG = "UserSkillPresenter";
    private UserSkillCase mUserSkillCase;
    private AddUserSkillCase mAddUserSkillCase;
    private DeleteUserSkillCase mDeleteUserSkillCase;
    private ErrorMessageFactory messageFactory;

    @Inject
    public UserSkillPresenter(UserSkillCase mUserSkillCase, AddUserSkillCase mAddUserSkillCase, DeleteUserSkillCase mDeleteUserSkillCase, ErrorMessageFactory messageFactory) {
        this.mUserSkillCase = mUserSkillCase;
        this.mAddUserSkillCase = mAddUserSkillCase;
        this.mDeleteUserSkillCase = mDeleteUserSkillCase;
        this.messageFactory = messageFactory;
    }

    public MvpLceView getLceView() {
        return (MvpLceView) getView();
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mUserSkillCase.unSubscribe();
        mAddUserSkillCase.unSubscribe();
        mDeleteUserSkillCase.unSubscribe();
    }

    public void loadUserSkill() {
        if (!isViewAttached()) {
            return;
        }
        mUserSkillCase.execute(getUserSkillCaseSub());
    }

    private Subscriber getUserSkillCaseSub() {
        return new Subscriber<List<UserSkill>>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG,e.getMessage());
                if (!isViewAttached()) {
                    return;
                }
                getLceView().showError(e, false);
            }

            @Override
            public void onNext(List<UserSkill> userSkill) {
                getLceView().setData(userSkill);
            }
        };
    }

    public void addUserSkill(String skill) {
        if (!isViewAttached()) {
            return;
        }
        mAddUserSkillCase.execute(getAddUserSkillCaseSub(), skill);

    }

    private Subscriber getAddUserSkillCaseSub() {
        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                loadUserSkill();
            }
            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getLceView().showError(e, false);
            }
            @Override
            public void onNext(Void v) {
                getLceView().showContent();
            }
        };
    }

    public void deleteUserSkill(String skillId) {
        if (!isViewAttached()) {
            return;
        }
        mDeleteUserSkillCase.execute(getDeleteUserSkillCaseSub(), skillId);
    }

    private Subscriber getDeleteUserSkillCaseSub() {
        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                loadUserSkill();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void o) {

            }
        };
    }
}

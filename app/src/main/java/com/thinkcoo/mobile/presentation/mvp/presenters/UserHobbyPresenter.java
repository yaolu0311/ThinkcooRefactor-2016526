package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.thinkcoo.mobile.domain.user.DeteleUserHobbyCase;
import com.thinkcoo.mobile.domain.user.EditUserHobbyCase;
import com.thinkcoo.mobile.domain.user.UserHobbyCase;
import com.thinkcoo.mobile.model.entity.UserHobby;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/6/1.
 */
public class UserHobbyPresenter extends MvpBasePresenter<MvpView> {

    private UserHobbyCase mUserHobbyCase;
    private EditUserHobbyCase mEditUserHobbyCase;
    private DeteleUserHobbyCase mDeteleUserHobbyCase;
    private ErrorMessageFactory messageFactory;

    @Inject
    public UserHobbyPresenter(UserHobbyCase mUserHobbyCase, EditUserHobbyCase mEditUserHobbyCase, DeteleUserHobbyCase mDeteleUserHobbyCase, ErrorMessageFactory messageFactory) {
        this.mUserHobbyCase = mUserHobbyCase;
        this.mEditUserHobbyCase = mEditUserHobbyCase;
        this.mDeteleUserHobbyCase = mDeteleUserHobbyCase;
        this.messageFactory = messageFactory;
    }

    public MvpLceView getLceView() {
        return (MvpLceView) getView();
    }


    public Subscriber getmUserHobbyCaseSub() {

        return new Subscriber<List<UserHobby>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e("tag", e.getMessage());
                if (!isViewAttached()) {
                    return;
                }
                getLceView().showError(e, false);

            }

            @Override
            public void onNext(List<UserHobby> userHobbies) {
                getLceView().setData(userHobbies);
            }

        };
    }

    public void LoadUserHobby() {
        mUserHobbyCase.execute(getmUserHobbyCaseSub());
    }


    public void editUserHobby(String hobby) {
        if (!isViewAttached()) {
            return;
        }
        mEditUserHobbyCase.execute(getmEditUserHobbyCaseSub(), hobby);

    }

    public Subscriber getmEditUserHobbyCaseSub() {
        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                LoadUserHobby();

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

            }


        };
    }


    public void deteleUserHobby(String hobbyId) {
        if (!isViewAttached()) {
            return;
        }
        mDeteleUserHobbyCase.execute(getmDeteleUserHobbyCaseSub(), hobbyId);
    }


    public Subscriber getmDeteleUserHobbyCaseSub() {
        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                LoadUserHobby();
            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e("tag", e.getMessage());
                if (!isViewAttached()) {
                    return;
                }
                getLceView().showError(e, false);

            }

            @Override
            public void onNext(Void V) {

            }
        };

    }

    ;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mUserHobbyCase.unSubscribe();
        mEditUserHobbyCase.unSubscribe();
        mDeteleUserHobbyCase.unSubscribe();

    }


}

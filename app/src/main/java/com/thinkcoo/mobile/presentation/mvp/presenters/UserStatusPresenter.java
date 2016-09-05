package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.user.DeleteUserStatusUseCase;
import com.thinkcoo.mobile.domain.user.GetUserStatusListUseCase;
import com.thinkcoo.mobile.domain.user.ToggleUserStatusUseCase;
import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.UserStatusView;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/5/30.
 */
public class UserStatusPresenter extends MvpBasePresenter<MvpView>{

    GetUserStatusListUseCase mGetUserStatusListUseCase;
    DeleteUserStatusUseCase mDeleteUserStatusUseCase;
    ToggleUserStatusUseCase mToggleUserStatusUseCase;
    InputCheckUtil mInputCheckUtil;
    ErrorMessageFactory mErrorMessageFactory;

    @Inject
    public UserStatusPresenter(GetUserStatusListUseCase getUserStatusListUseCase, DeleteUserStatusUseCase deleteUserStatusUseCase,
                               ToggleUserStatusUseCase toggleUserStatusUseCase, InputCheckUtil inputCheckUtil,
                               ErrorMessageFactory errorMessageFactory) {
        mGetUserStatusListUseCase = getUserStatusListUseCase;
        mDeleteUserStatusUseCase = deleteUserStatusUseCase;
        mToggleUserStatusUseCase = toggleUserStatusUseCase;
        mInputCheckUtil = inputCheckUtil;
        mErrorMessageFactory = errorMessageFactory;
    }

    private UserStatusView getUserStatusView(){
        return (UserStatusView) getView();
    }

    public void loadUserStatus(boolean isUpdateNow){
        if (!isViewAttached()) {
            return;
        }
        getUserStatusView().showProgressDialog(R.string.loading);
        mGetUserStatusListUseCase.execute(getUserStatusListSub(),isUpdateNow);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mGetUserStatusListUseCase.unSubscribe();
        mDeleteUserStatusUseCase.unSubscribe();
        mToggleUserStatusUseCase.unSubscribe();

    }

    private Subscriber getUserStatusListSub(){
        return new Subscriber<List<UserStatus>>() {
            @Override
            public void onCompleted() {
                //2016/6/13 姚橹改动
                //把这块代码移动上来，是为了配合Observable.empty()
                if (!isViewAttached()) {
                    return;
                }
                getUserStatusView().hideProgressDialogIfShowing();
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getUserStatusView().hideProgressDialogIfShowing();
                getUserStatusView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(List<UserStatus> userStatuses) {
                getUserStatusView().setData(userStatuses);
            }
        };
    }

    public void deleteUserStatus(UserStatus userStatus) {
        if (!isViewAttached()) {
            return;
        }
        getUserStatusView().showProgressDialog(R.string.loading);
        mDeleteUserStatusUseCase.execute(getDeleteUserStatusSub(), userStatus);
    }

    private Subscriber getDeleteUserStatusSub(){
        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()){
                    return;
                }
                getUserStatusView().showToast(getUserStatusView().getActivityContext().getResources().getString(R.string.delete_success));
                loadUserStatus(true);
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()){
                    return;
                }
                getUserStatusView().hideProgressDialogIfShowing();
                getUserStatusView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(Void v) {}
        };
    }

    public void toggleUserStatusOpenStatus(UserStatus userStatus, String openOrCloseStatus){
        if (!isViewAttached()){
            return;
        }
        getUserStatusView().showProgressDialog(R.string.loading);
        mToggleUserStatusUseCase.execute(getToggleUserStatusSub(), userStatus.getId(), openOrCloseStatus);
    }

    private Subscriber getToggleUserStatusSub(){
        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()){
                    return;
                }
                getUserStatusView().showToast(getUserStatusView().getActivityContext().getResources().getString(R.string.do_action_success));
                loadUserStatus(true);
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()){
                    return;
                }
                getUserStatusView().hideProgressDialogIfShowing();
                getUserStatusView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(Void v) {}
        };
    }

}

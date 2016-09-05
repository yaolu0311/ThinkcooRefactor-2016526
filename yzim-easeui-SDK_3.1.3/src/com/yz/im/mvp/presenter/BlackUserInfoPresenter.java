package com.yz.im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.DeleteFriendUseCase;
import com.yz.im.domain.FriendInfoUseCase;
import com.yz.im.domain.FriendToggleUseCase;
import com.yz.im.domain.ReliefBlackUserUseCase;
import com.yz.im.model.entity.serverresponse.FriendInfoResponse;
import com.yz.im.mvp.mvpContract.BlackInfoContact;

import rx.Subscriber;

/**
 * Created by cys on 2016/7/28
 */
public class BlackUserInfoPresenter extends BlackInfoContact.BlackInfoPresenter {

    private static String TAG = "BlackUserInfoPresenter";

    private ReliefBlackUserUseCase mReliefBlackUserUseCase;
    private DeleteFriendUseCase mDeleteFriendUseCase;
    private FriendToggleUseCase mToggleUseCase;
    private FriendInfoUseCase mInfoUseCase;
    private ErrorMessageFactory mErrorMessageFactory;

    public BlackUserInfoPresenter(Context context) {
        mDeleteFriendUseCase = new DeleteFriendUseCase(context);
        mReliefBlackUserUseCase = new ReliefBlackUserUseCase(context);
        mInfoUseCase = new FriendInfoUseCase(context);
        mToggleUseCase = new FriendToggleUseCase(context);
        mErrorMessageFactory = ErrorMessageFactory.getInstance(context);
    }

    @Override
    public void loadBlackFriendInfo(String userId) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(userId)) {
            return;
        }

        getView().showProgressDialog(R.string.loading_data);
        mInfoUseCase.execute(getFriendInfoSub(), userId);
    }

    private Subscriber<FriendInfoResponse> getFriendInfoSub() {
        return new Subscriber<FriendInfoResponse>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                doErrorEVent(e);
            }

            @Override
            public void onNext(FriendInfoResponse friendInfoResponse) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().setData(friendInfoResponse);
            }
        };
    }

    @Override
    public void deleteFriend(String friendId) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(friendId)) {
            return;
        }

        getView().showProgressDialog(R.string.deleting);
        mDeleteFriendUseCase.execute(getDeleteSub(), friendId, "1");
    }

    private Subscriber getDeleteSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().closeSelf();
            }

            @Override
            public void onError(Throwable e) {
                doErrorEVent(e);
            }

            @Override
            public void onNext(Object o) {
            }
        };
    }

    @Override
    public void reliefBlack(String friendId) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(friendId)) {
            return;
        }

        getView().showProgressDialog(R.string.waiting);
        mReliefBlackUserUseCase.execute(getReliefSub(), friendId);
    }

    private Subscriber getReliefSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().showToast(R.string.modify_success);
                getView().changeToggleStatus();
            }

            @Override
            public void onError(Throwable e) {
                doErrorEVent(e);
            }

            @Override
            public void onNext(Object o) {

            }
        };
    }


    @Override
    public void addFriendToBlack(String friendId, String popMsg, String disturbMsg, String blackList) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(friendId) || TextUtils.isEmpty(popMsg) || TextUtils.isEmpty(disturbMsg)
                || TextUtils.isEmpty(blackList)) {
            getView().showToast(R.string.operation_failture);
            return;
        }

        getView().showProgressDialog(R.string.committing);
        mToggleUseCase.execute(getToggleSub(), friendId, popMsg, disturbMsg, blackList);
    }

    private Subscriber getToggleSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().showToast(R.string.modify_success);
                getView().changeToggleStatus();
            }

            @Override
            public void onError(Throwable e) {
                doErrorEVent(e);
            }

            @Override
            public void onNext(Object o) {

            }
        };
    }

    private void doErrorEVent(Throwable e) {
        if (!isViewAttached()) {
            return;
        }
        getView().hideProgressDialog();
        getView().showToast(mErrorMessageFactory.createErrorMsg(e));
        ThinkcooLog.e(TAG, e.getMessage(), e);
    }
}

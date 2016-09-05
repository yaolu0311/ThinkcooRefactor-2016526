package com.yz.im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.DeleteFriendUseCase;
import com.yz.im.domain.FriendInfoUseCase;
import com.yz.im.domain.FriendToggleUseCase;
import com.yz.im.model.entity.serverresponse.FriendInfoResponse;
import com.yz.im.mvp.mvpContract.ProfileContract;

import rx.Subscriber;

/**
 * Created by cys on 2016/7/11
 */
public class FriendInfoPresenter extends ProfileContract.ProfilePresenter {

    private static String TAG = "ProfilePresenter";

    private Context mContext;
    private FriendInfoUseCase mInfoUseCase;
    private FriendToggleUseCase mToggleUseCase;
    private DeleteFriendUseCase mDeleteFriendUseCase;
    private ErrorMessageFactory mMessageFactory;

    public FriendInfoPresenter(Context context) {
        mContext = context;
        mInfoUseCase = new FriendInfoUseCase(context);
        mToggleUseCase = new FriendToggleUseCase(context);
        mDeleteFriendUseCase = new DeleteFriendUseCase(context);
        mMessageFactory = ErrorMessageFactory.getInstance(context);
    }

    @Override
    public void loadFriendInfo( String friendId) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(friendId)) {
            getView().showToast(R.string.load_user_info_failure);
            return;
        }

        getView().showProgressDialog(R.string.loading_data);
        mInfoUseCase.execute(getFriendInfoSub(), friendId);
    }

    private Subscriber<FriendInfoResponse> getFriendInfoSub() {
        return new Subscriber<FriendInfoResponse>(){

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().showToast(mMessageFactory.createErrorMsg(e));
                ThinkcooLog.e(TAG, e.getMessage(), e);
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

    public void updateToggleStatus(String friendId, String popMsg, String disturbMsg, String blackList) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(friendId) || TextUtils.isEmpty(popMsg) || TextUtils.isEmpty(disturbMsg) || TextUtils.isEmpty(blackList)) {
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
                getView().refreshToggleStatus();
                getView().showToast(R.string.modify_success);
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().showToast(mMessageFactory.createErrorMsg(e));
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(Object o) {

            }
        };
    }

    public void deleteFriend(String friendId, String type) {
        if (!isViewAttached()) {
            return;
        }
        if (TextUtils.isEmpty(friendId)) {
            getView().showToast(R.string.delete_failture);
            return;
        }

        getView().showProgressDialog(R.string.deleting);
        mDeleteFriendUseCase.execute(getDeleteFriendSub(), friendId, type);
    }

    private Subscriber getDeleteFriendSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().showToast(R.string.delete_success);
                getView().closeSelf();
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().showToast(mMessageFactory.createErrorMsg(e));
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(Object o) {
            }
        };
    }

}

package com.yz.im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.LoadGroupMemberUseCase;
import com.yz.im.domain.TransferGroupUseCase;
import com.yz.im.model.entity.serverresponse.GroupMemberResponse;
import com.yz.im.mvp.mvpContract.GroupMemberContact;

import java.util.List;

import rx.Subscriber;

/**
 * Created by cys on 2016/7/27
 */
public class GroupMemberListPresenter extends GroupMemberContact.GroupMemberPresenter {

    private static String TAG = "GroupMemberListPresenter";

    private Context mContext;
    private LoadGroupMemberUseCase mMemberUseCase;
    private TransferGroupUseCase mTransferGroupUseCase;
    private ErrorMessageFactory mErrorMessageFactory;

    public GroupMemberListPresenter(Context context) {
        mContext = context;
        mMemberUseCase = new LoadGroupMemberUseCase(context);
        mTransferGroupUseCase = new TransferGroupUseCase(context);
        mErrorMessageFactory = ErrorMessageFactory.getInstance(context);
    }

    @Override
    public void loadGroupMemberList(String groupId) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(groupId)) {
            getView().showToast(R.string.operation_failture);
            return;
        }
        getView().showProgressDialog(R.string.loading_data);
        mMemberUseCase.execute(getMemberSub(), groupId);
    }

    private Subscriber<List<GroupMemberResponse>> getMemberSub() {
        return new Subscriber<List<GroupMemberResponse>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(List<GroupMemberResponse> list) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().setData(list);
            }
        };
    }

    @Override
    public void transferGroup(String groupId, String newUserId) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(groupId) || TextUtils.isEmpty(newUserId)) {
            getView().showToast(R.string.transfer_group_failture);
            return;
        }
        getView().showProgressDialog(R.string.committing);
        mTransferGroupUseCase.execute(getTransferSub(), groupId, newUserId);
    }

    private Subscriber getTransferSub() {
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
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(Object o) {

            }
        };
    }
}

package com.yz.im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.GroupInfoUseCase;
import com.yz.im.domain.QuitGroupUseCase;
import com.yz.im.domain.UpdateGroupStatus;
import com.yz.im.model.entity.serverresponse.GroupInfoResponse;
import com.yz.im.mvp.mvpContract.GroupInfoContact;

import rx.Subscriber;

/**
 * ThinkCoo20160718
 * Created by cys on 2016/7/23 0023
 */
public class GroupInfoPresenter extends GroupInfoContact.GroupInfoPresenter{

    private static String TAG = "GroupInfoPresenter";

    private Context mContext;
    private GroupInfoUseCase mGroupInfoUseCase;
    private UpdateGroupStatus mUpdateGroupStatus;
    private QuitGroupUseCase mQuitGroupUseCase;
    private ErrorMessageFactory mMessageFactory;

    public GroupInfoPresenter(Context context) {
        mContext = context;
        mGroupInfoUseCase = new GroupInfoUseCase(context);
        mUpdateGroupStatus = new UpdateGroupStatus(context);
        mQuitGroupUseCase = new QuitGroupUseCase(context);
        mMessageFactory = ErrorMessageFactory.getInstance(context);
    }

    @Override
    public void loadGroupInfo(String groupId) {
        if (!isViewAttached()) {
            return;
        }
        if (TextUtils.isEmpty(groupId)) {
            getView().showToast(R.string.group_id_is_null);
            return;
        }

        getView().showProgressDialog(R.string.loading_data);
        mGroupInfoUseCase.execute(getGroupInfoSub(), groupId);
    }

    private Subscriber getGroupInfoSub() {
        return new Subscriber<GroupInfoResponse>() {
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
            public void onNext(GroupInfoResponse response) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                if (response == null) {
                    getView().showToast(R.string.failed_to_load_data);
                    return;
                }
                getView().setData(response);
            }
        };
    }

    public void updateToggleStatus(String groupId, String msgUp, String msgDisturb) {
        if (!isViewAttached()) {
            return;
        }
        if (TextUtils.isEmpty(groupId)) {
            getView().showToast(R.string.operation_failture);
            return;
        }
        getView().showProgressDialog(R.string.committing);
        mUpdateGroupStatus.execute(getUpdateGroupSub(), groupId, msgUp, msgDisturb);
    }

    private Subscriber getUpdateGroupSub() {
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

    /**
     * @param type 0表示主动退出  1表示被管理员删除
     */
    public void quitGroup(String groupId, String friendId, String type) {
        if (!isViewAttached()) {
            return;
        }
        if(TextUtils.isEmpty(type)){
            getView().showToast(R.string.operation_failture);
            return;
        }

        if (TextUtils.isEmpty(groupId)) {
            getView().showToast(R.string.group_id_is_null);
            return;
        }

        if("1".equals(type) && TextUtils.isEmpty(friendId)){
            getView().showToast(R.string.operation_failture);
            return;
        }

        getView().showProgressDialog(R.string.committing);
        mQuitGroupUseCase.execute(getQuitGroupSub(), groupId, friendId, type);
    }

    private Subscriber getQuitGroupSub() {
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
                getView().showToast(mMessageFactory.createErrorMsg(e));
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(Object o) {

            }
        };
    }
}

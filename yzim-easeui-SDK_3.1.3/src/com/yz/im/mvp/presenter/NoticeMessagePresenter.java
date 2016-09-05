package com.yz.im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.IMHelper;
import com.yz.im.domain.DealWithNoticeMsgUseCase;
import com.yz.im.domain.IMBaseCase;
import com.yz.im.domain.LoadNoticeMsgUseCase;
import com.yz.im.domain.RefreshFriendListUseCase;
import com.yz.im.domain.RefreshGroupListUseCase;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.model.db.entity.Group;
import com.yz.im.model.entity.serverresponse.NoticeMessageResponse;
import com.yz.im.mvp.mvpContract.MessageNoticeContact;
import com.yz.im.services.BackgroundInitService;
import com.yz.im.ui.activity.NoticeMessageActivity;

import java.util.List;

import rx.Subscriber;

/**
 * Created by cys on 2016/8/5
 */
public class NoticeMessagePresenter extends MessageNoticeContact.NoticePresenter {

    private static String TAG = "MessageNoticePresenter";

    private Context mContext;
    private LoadNoticeMsgUseCase mLoadNoticeMsgUseCase;
    private DealWithNoticeMsgUseCase mDealWithNoticeMsgUseCase;
    private RefreshGroupListUseCase mGroupListUseCase;
    private RefreshFriendListUseCase mFriendListUseCase;
    private ErrorMessageFactory mMessageFactory;

    public NoticeMessagePresenter(Context context) {
        mContext = context;
        mLoadNoticeMsgUseCase = new LoadNoticeMsgUseCase(context);
        mDealWithNoticeMsgUseCase = new DealWithNoticeMsgUseCase(context);
        mGroupListUseCase = new RefreshGroupListUseCase(context, IMBaseCase.MAIN_THREAD, IMBaseCase.EXECUTOR_THREAD);
        mFriendListUseCase = new RefreshFriendListUseCase(context, IMBaseCase.MAIN_THREAD, IMBaseCase.EXECUTOR_THREAD);
        mMessageFactory = ErrorMessageFactory.getInstance(context);
    }

    @Override
    public void loadMessage(String type) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(type)) {
            getView().showToast(R.string.load_data_failture);
            return;
        }

        getView().showProgressDialog(R.string.loading_data);
        mLoadNoticeMsgUseCase.execute(getListSub(type), type);
    }

    private Subscriber getListSub(final String type) {
        return new Subscriber<List<NoticeMessageResponse>>() {
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
            public void onNext(List<NoticeMessageResponse> list) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                if (list == null || list.size() <= 0) {
                    getView().showToast(R.string.temp_no_data);
                    return;
                }
                if (NoticeMessageActivity.TYPE_VERIFY_MSG.equals(type)) {
                    getView().setVerifyMsgList(list);
                } else {
                    getView().setNoticeMsgList(list);
                }
            }
        };
    }

    @Override
    public void doWithMessage(NoticeMessageResponse response, String operationType) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(response.getMessageId())) {
            getView().showToast(R.string.operation_failture);
            return;
        }

        getView().showProgressDialog(R.string.waiting);
        mDealWithNoticeMsgUseCase.execute(getDealWithSub(response, operationType), response.getMessageId(), operationType);
    }

    private Subscriber getDealWithSub(final NoticeMessageResponse response, final String operationType) {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                boolean flag = NoticeMessageActivity.ACTION_DELETE_OTHRE_MSG.equals(operationType);
                if (flag) {
                    getView().hideProgressDialog();
                    getView().refreshOtherMessageData();
                } else {
                    refreshData(response);
                }
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

    private void refreshData(NoticeMessageResponse response) {
        String userId = IMHelper.getInstance().getInfoResponse().getUserId();
        String type = response.getMessageType();
        if(NoticeMessageActivity.MSG_FROM_FRIEND.equals(type)){
            mFriendListUseCase.execute(getFriendListSub(), IMHelper.getInstance().getInfoResponse().getUserId(), BackgroundInitService.FRIEND_ALL);
        }else{
            mGroupListUseCase.execute(getGroupListSub(), IMHelper.getInstance().getInfoResponse().getUserId());
        }
    }

    private Subscriber getFriendListSub() {
        return new Subscriber<List<Friend>>() {
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
            public void onNext(List<Friend> objects) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().refreshVerifyMessageData();
            }
        };
    }

    private Subscriber getGroupListSub() {
        return new Subscriber<List<Group>>() {
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
            public void onNext(List<Group> objects) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().refreshVerifyMessageData();
            }
        };
    }

}

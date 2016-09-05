package com.yz.im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.SendJoinGroupReasonUseCase;
import com.yz.im.mvp.mvpContract.BaseInviteContact;

import rx.Subscriber;

/**
 * Created by cys on 2016/8/2
 */
public class SendJoinGroupReasonPresenter extends BaseInviteContact.BaseInvitePresenter {
    private static String TAG = "SendInviteReasonPresenter";

    private SendJoinGroupReasonUseCase mUseCase;
    private ErrorMessageFactory mMessageFactory;

    public SendJoinGroupReasonPresenter(Context context) {
        mUseCase = new SendJoinGroupReasonUseCase(context);
        mMessageFactory = ErrorMessageFactory.getInstance(context);
    }

    @Override
    public void sendInviteReason(String... args) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(args[2])) {
            getView().showToast(R.string.write_invite_reason);
            return;
        }

        if (TextUtils.isEmpty(args[0])) {
            getView().showToast(R.string.group_id_is_null);
            return;
        }

        getView().showProgressDialog(R.string.sending);
        mUseCase.execute(getInviteReasonSub(), args[0], args[2]);
    }

    private Subscriber getInviteReasonSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().showToast(R.string.invite_send);
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

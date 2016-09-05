package com.yz.im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.SendInviteUserReasonUseCase;
import com.yz.im.mvp.mvpContract.BaseInviteContact;

import rx.Subscriber;

/**
 * Created by cys on 2016/7/29
 */
public class SendInviteUserReasonPresenter extends BaseInviteContact.BaseInvitePresenter {

    private static String TAG = "SendInviteUserReasonPresenter";

    private SendInviteUserReasonUseCase mUseCase;
    private ErrorMessageFactory mMessageFactory;

    public SendInviteUserReasonPresenter(Context context) {
        mUseCase  = new SendInviteUserReasonUseCase(context);
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

        if (TextUtils.isEmpty(args[1])) {
            getView().showToast(R.string.user_id_is_null);
            return;
        }

        getView().showProgressDialog(R.string.sending);
        mUseCase.execute(getInviteReasonSub(), args[1], args[2] );
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

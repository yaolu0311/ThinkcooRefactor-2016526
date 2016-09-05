package com.yz.im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.SystemSettingUseCase;
import com.yz.im.mvp.mvpContract.SystemSettingContact;

import rx.Subscriber;

/**
 * Created by cys on 2016/8/3
 */
public class SystemSettingPresenter extends SystemSettingContact.SettingPresenter {

    private static String TAG = "SystemSettingPresenter";

    private Context mContext;
    private SystemSettingUseCase mCase;
    private ErrorMessageFactory mMessageFactory;

    public SystemSettingPresenter(Context context) {
        mContext = context;
        mCase = new SystemSettingUseCase(context);
        mMessageFactory = ErrorMessageFactory.getInstance(context);
    }

    @Override
    public void updateSetting(String newMessage, String textingMsg, String unfamiliarMsg) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(newMessage) || TextUtils.isEmpty(textingMsg) || TextUtils.isEmpty(unfamiliarMsg)) {
            getView().showToast(R.string.operation_failture);
            return;
        }

        getView().showProgressDialog(R.string.committing);
        mCase.execute(getSub(), newMessage, textingMsg, unfamiliarMsg);
    }

    private Subscriber getSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().showToast(R.string.modify_success);
                getView().refreshStatus();
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

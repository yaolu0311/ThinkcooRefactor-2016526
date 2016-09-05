package com.yz.im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.ReportUseCase;
import com.yz.im.mvp.mvpContract.ReportContact;

import rx.Subscriber;

/**
 * Created by cys on 2016/7/26
 */
public class ReportPresenter extends ReportContact.ReportPresenter{

    private static String TAG = "ReportPresenter";

    private ReportUseCase mUseCase;
    private ErrorMessageFactory mMessageFactory;

    public ReportPresenter(Context context) {
        mUseCase = new ReportUseCase(context);
        mMessageFactory = ErrorMessageFactory.getInstance(context);
    }

    @Override
    public void uploadReport(String friendId, String groupId, String reportReason, String context) {
        if (!isViewAttached()) {
            return;
        }

        if(!checkArgumentsIsLegal(friendId, groupId, reportReason, context)){
            return;
        }

        getView().showProgressDialog(R.string.committing);
        mUseCase.execute(getSub(), friendId, groupId, reportReason, context);
    }

    private Subscriber getSub() {
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

    private boolean checkArgumentsIsLegal(String friendId, String groupId, String reportReason, String context) {
        if (TextUtils.isEmpty(friendId) && TextUtils.isEmpty(groupId)) {
            getView().showToast(R.string.operation_failture);
            return false;
        }
        if (TextUtils.isEmpty(reportReason) && TextUtils.isEmpty(context)) {
            getView().showToast(R.string.report_notive);
            return false;
        }
        return true;
    }
}

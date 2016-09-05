package com.yz.im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.SingleEditTextUseCase;
import com.yz.im.mvp.mvpContract.SingleEditTextContract;

import rx.Subscriber;

/**
 * Created by cys on 2016/7/26
 */
public class SingleEditTextPresenter extends SingleEditTextContract.SingleEditTextPresenter{

    private static String TAG = "UpdateFriendRemarkPresenter";

    private SingleEditTextUseCase mUseCase;
    private ErrorMessageFactory mMessageFactory;

    public SingleEditTextPresenter(Context context) {
        mUseCase = new SingleEditTextUseCase(context);
        mMessageFactory = ErrorMessageFactory.getInstance(context);
    }

    @Override
    public void updateContent(String friendId, String groupId, String content, String editType) {
        if (!isViewAttached()) {
            return;
        }

        if(!checkField(friendId, groupId, content, editType)){
            return;
        }

        String oldName = getView().getOldContent();
        if (content.equals(oldName)) {
            getView().showToast(R.string.remark_no_change);
            return;
        }

        getView().showProgressDialog(R.string.updating);
        mUseCase.execute(getSub(), friendId, groupId, content, editType);
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

    private boolean checkField(String friendId, String groupId, String content, String editType) {
        if (TextUtils.isEmpty(content)) {
            getView().showToast(R.string.content_is_null);
            return false;
        }
        return true;
    }
}

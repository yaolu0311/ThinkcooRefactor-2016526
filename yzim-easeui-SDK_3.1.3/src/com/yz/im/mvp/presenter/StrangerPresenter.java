package com.yz.im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.LoadStrangerInfoUseCase;
import com.yz.im.domain.UpdateShieldStatusUseCase;
import com.yz.im.model.entity.serverresponse.StrangerInfoResponse;
import com.yz.im.mvp.mvpContract.StrangerInfoContact;

import rx.Subscriber;

/**
 * Created by cys on 2016/7/28
 */
public class StrangerPresenter extends StrangerInfoContact.StrangerInfoPresenter{

    private static String TAG  = "StrangerPresenter";

    private Context mContext;
    private LoadStrangerInfoUseCase mStrangerInfoUseCase;
    private UpdateShieldStatusUseCase mShieldStatusUseCase;
    private ErrorMessageFactory mErrorMessageFactory;

    public StrangerPresenter(Context context) {
        mContext = context;
        mErrorMessageFactory = ErrorMessageFactory.getInstance(context);
        mStrangerInfoUseCase = new LoadStrangerInfoUseCase(context);
        mShieldStatusUseCase = new UpdateShieldStatusUseCase(context);
    }

    @Override
    public void loadStrangerInfo(String userId) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(userId)) {
            getView().showToast(R.string.load_user_info_failure);
            return;
        }

        getView().showProgressDialog(R.string.loading_data);
        mStrangerInfoUseCase.execute(getStrangerInfoSub(), userId);
    }

    private Subscriber getStrangerInfoSub() {
        return new Subscriber<StrangerInfoResponse>() {
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
            public void onNext(StrangerInfoResponse obj) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().setData(obj);
            }
        };
    }

    @Override
    public void updateShieldStatus(String userId, String type) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(type)) {
            getView().showToast(R.string.operation_failture);
            return;
        }

        getView().showProgressDialog(R.string.committing);
        mShieldStatusUseCase.execute(getShieldSub(), userId, type);
    }

    private Subscriber getShieldSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().toggleButtonStatus();
                getView().showToast(R.string.modify_success);
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

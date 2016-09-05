package com.yz.im.mvp.presenter;

import android.content.Context;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.TelephoneContactUseCase;
import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.mvp.mvpContract.TelephoneContact;

import java.util.List;

import rx.Subscriber;

/**
 * Created by cys on 2016/8/2
 */
public class TelephoneContactPresenter extends TelephoneContact.TelephonePresenter{

    private static String TAG = "TelephoneContactPresenter";

    private Context mContext;
    private TelephoneContactUseCase mUseCase;
    private ErrorMessageFactory mMessageFactory;

    public TelephoneContactPresenter(Context context) {
        mContext = context;
        mUseCase = new TelephoneContactUseCase(context);
        mMessageFactory = ErrorMessageFactory.getInstance(context);
    }

    @Override
    public void loadTelephoneContact() {
        if (!isViewAttached()) {
            return;
        }
        getView().showProgressDialog(R.string.loading_data);
        mUseCase.execute(getSub());
    }

    private Subscriber getSub() {
        return new Subscriber<List<FindUserResponse>>() {
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
            public void onNext(List<FindUserResponse> list) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                if (list == null || list.size()<=0) {
                    getView().showToast(R.string.no_matcher_user);
                    return;
                }

                getView().setData(list);
            }
        };
    }
}

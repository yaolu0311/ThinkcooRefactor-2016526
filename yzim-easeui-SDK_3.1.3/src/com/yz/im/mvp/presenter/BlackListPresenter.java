package com.yz.im.mvp.presenter;

import android.content.Context;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.IMHelper;
import com.yz.im.domain.LoadBlackListUseCase;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.mvp.mvpContract.BlackListContact;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by cys on 2016/8/3
 */
public class BlackListPresenter extends BlackListContact.BlackUserListPresenter {

    public static final String TAG = "BlackListPresenter";

    private Context mContext;
    private LoadBlackListUseCase mUseCase;
    private ErrorMessageFactory mMessageFactory;

    public BlackListPresenter(Context context) {
        mContext = context;
        mUseCase = new LoadBlackListUseCase(context);
        mMessageFactory = ErrorMessageFactory.getInstance(context);
    }

    @Override
    public void loadBlackUserList(String userType) {
        if (!isViewAttached()) {
            return;
        }
        getView().showProgressDialog(R.string.loading_data);
        mUseCase.execute(getSub(), IMHelper.getInstance().getInfoResponse().getUserId(), userType);
    }

    private Subscriber getSub() {
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
            public void onNext(List<Friend> list) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                if (list == null ) {
                    list = new ArrayList<>();
                    getView().showToast(R.string.no_matcher_black);
                }
                getView().setData(list);
            }
        };
    }
}

package com.yz.im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.FindUserByNumberUseCase;
import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.mvp.mvpContract.RecommendUserContact;

import java.util.List;

import rx.Subscriber;

/**
 * Created by cys on 2016/8/2
 */
public class RecommendUserPresenter extends RecommendUserContact.RecommendPresenter {

    private static String TAG = "RecommendUserPresenter";

    private FindUserByNumberUseCase mUseCase;
    private ErrorMessageFactory mMessageFactory;

    public RecommendUserPresenter(Context context) {
        mUseCase = new FindUserByNumberUseCase(context);
        mMessageFactory = ErrorMessageFactory.getInstance(context);
    }

    @Override
    public void loadRecommendUser(String searchValue, String findUserType, String pageNo, String pageSize) {
        if (!isViewAttached()) {
            return;
        }

        getView().showProgressDialog(R.string.loading_data);
        mUseCase.execute(getSearchSub(), searchValue, findUserType, pageNo, pageSize);
    }

    private Subscriber getSearchSub() {
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
                if (list == null || list.size() <= 0) {
                    getView().showToast(R.string.no_recommend_user);
                    return;
                }
                getView().setList(list);
            }
        };
    }
}

package com.yz.im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.FindUserByNameUseCase;
import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.mvp.mvpContract.FindUserExactContact;

import java.util.List;

import rx.Subscriber;

/**
 * Created by cys on 2016/8/2
 */
public class FindExactUserPresenter extends FindUserExactContact.FindUserExactPresenter {

    private static String TAG = "FindExactUserPresenter";

    private FindUserByNameUseCase mUseCase;
    private ErrorMessageFactory mMessageFactory;

    public FindExactUserPresenter(Context context) {
        mUseCase = new FindUserByNameUseCase(context);
        mMessageFactory = ErrorMessageFactory.getInstance(context);
    }

    @Override
    public void loadUserList(String areaCode, String school, String department, String professional, String realName, String pageNo, String pageSize) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(realName)) {
            getView().showToast(R.string.user_name_is_empty);
            return;
        }

        getView().showProgressDialog(R.string.loading_data);
        mUseCase.execute(getSearchSub(), areaCode, school, department, professional, realName, pageNo, pageSize);
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
                    getView().showToast(R.string.no_matcher_user);
                }

                getView().goToUserListPage(list);
            }
        };
    }
}

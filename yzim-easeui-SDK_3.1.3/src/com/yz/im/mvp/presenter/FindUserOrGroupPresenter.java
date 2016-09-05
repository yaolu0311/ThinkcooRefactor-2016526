package com.yz.im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.FindGroupUseCase;
import com.yz.im.domain.FindInterestGroupUseCase;
import com.yz.im.domain.FindUserByNumberUseCase;
import com.yz.im.model.entity.serverresponse.FindGroupResponse;
import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.mvp.mvpContract.FindUserOrGroupContact;

import java.util.List;

import rx.Subscriber;

/**
 * Created by cys on 2016/8/1
 */
public class FindUserOrGroupPresenter extends FindUserOrGroupContact.FindUserGroupPresenter {

    private static final String TAG = "FindUserOrGroupPresenter";

    private Context mContext;
    private FindUserByNumberUseCase mUserByNumberUseCase;
    private FindGroupUseCase mGroupUseCase;
    private FindInterestGroupUseCase mFindInterestGroupUseCase;
    private ErrorMessageFactory mMessageFactory;

    public FindUserOrGroupPresenter(Context context) {
        mContext = context;
        mUserByNumberUseCase = new FindUserByNumberUseCase(context);
        mGroupUseCase = new FindGroupUseCase(context);
        mFindInterestGroupUseCase = new FindInterestGroupUseCase(context);
        mMessageFactory = ErrorMessageFactory.getInstance(context);
    }

    @Override
    public void findUserByNumber(String searchValue, String findUserType, String pageNo, String pageSize) {
        if (!isViewAttached()) {
            return;
        }
        if (TextUtils.isEmpty(findUserType)) {
            getView().showToast(R.string.operation_failture);
            return;
        }
        getView().showProgressDialog(R.string.loading_data);
        mUserByNumberUseCase.execute(getUserNumberSub(), searchValue, findUserType, pageNo, pageSize);
    }

    private Subscriber getUserNumberSub() {
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
            public void onNext(List<FindUserResponse> response) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                if (response == null || response.size()<=0) {
                    getView().showToast(R.string.no_matcher_user);
                    return;
                }

                getView().gotoUserInfoPage(response.get(0));
            }
        };
    }


    @Override
    public void loadInterestGroup(String groupValue, String groupType, String pageNo, String pageSize) {
        if (!isViewAttached()) {
            return;
        }
        if (TextUtils.isEmpty(groupType)) {
            groupType = "1";
        }
        getView().showProgressDialog(R.string.loading_data);
        mFindInterestGroupUseCase.execute(getInterestGroupSub(), groupValue, groupType, pageNo, pageSize);
    }

    private Subscriber getInterestGroupSub() {
        return new Subscriber<List<FindGroupResponse>>() {
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
            public void onNext(List<FindGroupResponse> list) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                if (list == null || list.size()<=0) {
                    getView().showToast(R.string.no_matcher_group);
                    return;
                }
                getView().setGroupListData(list);
            }
        };
    }

    @Override
    public void findGroup(String groupValue, String groupType, String pageNumber, String pageSize) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(groupValue)) {
            getView().showToast(R.string.input_search_key);
            return;
        }

        if (TextUtils.isEmpty(groupType)) {
            groupType = "0";
        }
        getView().showProgressDialog(R.string.loading_data);
        mGroupUseCase.execute(getGroupListSub(), groupValue, groupType, pageNumber, pageSize);
    }

    private Subscriber getGroupListSub() {
        return new Subscriber<List<FindGroupResponse>>() {
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
            public void onNext(List<FindGroupResponse> list) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                if (list == null || list.size()<=0) {
                    getView().showToast(R.string.no_matcher_group);
                    return;
                }
                getView().gotoGroupListPage(list);
            }
        };
    }
}

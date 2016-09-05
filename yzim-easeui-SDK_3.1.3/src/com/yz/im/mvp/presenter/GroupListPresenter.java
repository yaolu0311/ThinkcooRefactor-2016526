package com.yz.im.mvp.presenter;

import android.content.Context;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.GroupListUseCase;
import com.yz.im.model.cache.GroupCache;
import com.yz.im.model.db.entity.Group;
import com.yz.im.mvp.mvpContract.GroupListContract;

import java.util.List;

import rx.Subscriber;

/**
 * Created by cys on 2016/7/22
 */
public class GroupListPresenter extends GroupListContract.GroupListPresenter {

    private static String TAG = "GroupListPresenter";

    private Context mContext;
    private GroupListUseCase mGroupListUseCase;
    private ErrorMessageFactory mErrorMessageFactory;
    private GroupCache mGroupCache;

    public GroupListPresenter(Context context) {
        mContext = context;
        mGroupListUseCase = new GroupListUseCase(context);
        mErrorMessageFactory = ErrorMessageFactory.getInstance(context);
        mGroupCache = GroupCache.getInstance(context);
    }

    @Override
    public void loadGroupList() {
        if (!isViewAttached()) {
            return;
        }
        getView().showProgressDialog(R.string.loading_data);
        mGroupListUseCase.execute(getGroupListSub());
    }

    private Subscriber getGroupListSub() {
        return new Subscriber<List<Group>>() {

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
            public void onNext(List<Group> list) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().setData(list);
            }
        };
    }
}

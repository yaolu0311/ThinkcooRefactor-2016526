package com.yz.im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.exception.ErrorMessageFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.domain.CreateGroupUseCase;
import com.yz.im.model.db.entity.Group;
import com.yz.im.mvp.mvpContract.CreateGroupContact;

import rx.Subscriber;

/**
 * Created by cys on 2016/8/4
 */
public class CreateGroupPresenter extends CreateGroupContact.CreatePresenter{

    private static String TAG = "CreateGroupPresenter";

    private Context mContext;
    private CreateGroupUseCase mUseCase;
    private ErrorMessageFactory mMessageFactory;

    public CreateGroupPresenter(Context context) {
        mContext = context;
        mUseCase = new CreateGroupUseCase(context);
        mMessageFactory = ErrorMessageFactory.getInstance(context);
    }

    @Override
    public void createGroup(String groupName, String isPublic, String isApproval, String circleType) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(groupName)) {
            getView().showToast(R.string.group_name_is_null);
            return;
        }

        getView().showProgressDialog(R.string.committing);
        mUseCase.execute(getCreateSub(), groupName, isPublic, isApproval, circleType);
    }

    private Subscriber getCreateSub() {
        return new Subscriber<Group>() {
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
            public void onNext(Group group) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialog();
                getView().goToGroupInfoPage(group);
            }
        };
    }
}

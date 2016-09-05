package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.model.entity.UserStatusDetail;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseDetailPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.UserStatusDetailPresenter;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseDetailActivity;
import com.thinkcoo.mobile.presentation.views.component.DataDictionaryDialog;
import com.thinkcoo.mobile.presentation.views.component.TimePickDialog;
import com.thinkcoo.mobile.presentation.views.component.viewBean.UserEducationStatusContentView;
import com.thinkcoo.mobile.presentation.views.component.viewBean.UserStatusBaseContentView;
import com.thinkcoo.mobile.presentation.views.component.viewBean.UserTrainStatusContentView;
import com.thinkcoo.mobile.presentation.views.component.viewBean.UserWorkStatusContentView;

import javax.inject.Inject;
import javax.inject.Named;

public class UserStatusDetailActivity extends BaseDetailActivity<UserStatus,UserStatusDetail>{
    private static final String TAG = "UserStatusDetailActivity";

    public static final String NO_TYPE_ERROR_MSG = "You must set activity type for userStatus";

    public static final int EDUCATION_STATUS_TYPE = 1;
    public static final int TRAIN_STATUS_TYPE = 2;
    public static final int FULL_TIME_WORK_STATUS_TYPE = 3;
    public static final int PART_TIME_WORK_STATUS_TYPE = 4;

    public static final String STATUS_TYPE = "status_type";
    private int mStatusType;

    @Inject
    UserStatusDetailPresenter mUserStatusDetailPresenter;
    @Inject
    @Named("Education")
    DataDictionaryDialog mDataDictionaryDialog;

    @Inject
    TimePickDialog mTimePickDialog;

    private UserStatusBaseContentView mContentView;

    private UserStatus mUserStatus;

    public static Intent getUserStatusDetailIntent(Context context, UserStatus userStatus, int mode) {

        Intent intent = new Intent(context, UserStatusDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(MODE_KEY, mode);
        bundle.putParcelable(HOST_OBJECT, userStatus);
        bundle.putInt(STATUS_TYPE, userStatus.getType());
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void processIntentBundle(Bundle bundle) {
        if (null == bundle || !bundle.containsKey(STATUS_TYPE)) {
            throw new IllegalArgumentException(NO_TYPE_ERROR_MSG);
        }
        mStatusType = bundle.getInt(STATUS_TYPE);
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().userModule(new UserModule()).activityModule(getActivityModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }

    @Override
    protected BaseDetailPresenter generateBaseDetailPresenter() {
        return mUserStatusDetailPresenter;
    }

    @Override
    protected View getDetailLayout() {
        switch (mStatusType){
            case EDUCATION_STATUS_TYPE:
                mContentView = new UserEducationStatusContentView(this, mStatusType, mDataDictionaryDialog, mTimePickDialog);
                break;
            case TRAIN_STATUS_TYPE:
                mContentView = new UserTrainStatusContentView(this, mStatusType, mTimePickDialog);
                break;
            case FULL_TIME_WORK_STATUS_TYPE:
            case PART_TIME_WORK_STATUS_TYPE:
                mContentView = new UserWorkStatusContentView(this, mStatusType, mTimePickDialog);
                break;
        }
        return mContentView.getRootView();
    }

    @Override
    protected void setupTitle(TextView textView) {
        int titleResourceTitle = R.string.user_status;
        switch (mStatusType){
            case EDUCATION_STATUS_TYPE:
                titleResourceTitle = R.string.user_academic_education_status;
                break;
            case TRAIN_STATUS_TYPE:
                titleResourceTitle = R.string.user_train_status;
                break;
            case FULL_TIME_WORK_STATUS_TYPE:
                titleResourceTitle = R.string.user_fulltime_work_status;
                break;
            case PART_TIME_WORK_STATUS_TYPE:
                titleResourceTitle = R.string.user_parttime_work_status;
                break;
        }
        textView.setText(titleResourceTitle);
    }

    @Override
    protected void setupRightTextView(TextView textView) {
        textView.setText(R.string.complete);
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void bindHostObjectToView(UserStatus hostObject) {
        if (null == hostObject) {
            hostObject = new UserStatus();
        }
        this.mUserStatus = hostObject;
        mContentView.showBasicInfo(hostObject);
    }

    @Override
    protected void bindDetailObjectToView(UserStatusDetail detailObject) {
        if (null==detailObject) {
            return;
        }

        if (null == mUserStatus) {
            return;
        }
        mUserStatus.setUserStatusDetail(detailObject);
        mContentView.showDetailsInfo(detailObject);
    }

    @Override
    public UserStatus getHostFromUi() {
        UserStatus userStatus = mContentView.getUserStatus();
        if (null != mUserStatus && null != userStatus) {
            userStatus.setId(mUserStatus.getId());
            userStatus.setOpen(mUserStatus.isOpen());
        }
        return userStatus;
    }

    @Override
    public void setResultCancelAndCloseSelf() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mContentView.handActivityResult(data, requestCode, resultCode);
    }

    public int getMode(){
        return  mActivityMode;
    }
}

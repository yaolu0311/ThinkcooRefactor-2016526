package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.model.entity.UserWorkExperienceEntity;
import com.thinkcoo.mobile.presentation.mvp.presenters.UserExperienceDetailPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.UserExperienceDetailView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.component.TimePickDialog;
import com.thinkcoo.mobile.presentation.views.dialog.GlobalTransparentPopupWindow;
import com.thinkcoo.mobile.utils.DateUtils;

import java.util.Date;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/17.
 */
public class UserWorkExperienceDetailActivity extends BaseActivity implements UserExperienceDetailView {

    public static final int MODE_ADD = 0x0001;
    public static final int MODE_EDIT = 0x0002;
    public static final int MODE_VIEW = 0x0003;

    public static final int REQUEST_EXPERIENCE_CODE = 0x0004;

    public static final String KEY_WORK_ID = "key_work_id";
    public static final String KEY_MODE = "kdy_mode";
    public static final String KEY_JAVA_BEAN = "kdy_java_bean";

    public static Intent getWorkExperienceDetailIntent(Context context, String workId, UserWorkExperienceEntity workExperienceListResponse, int mode) {
        if (TextUtils.isEmpty(workId)) {
            throw new IllegalArgumentException("workId can not be null when you operate WorkExperience");
        }
        Intent intent = new Intent(context, UserWorkExperienceDetailActivity.class);
        intent.putExtra(KEY_WORK_ID, workId);
        intent.putExtra(KEY_MODE, mode);
        intent.putExtra(KEY_JAVA_BEAN, workExperienceListResponse);
        return intent;
    }

    @Bind(R.id.iv_back)
    TextView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_other)
    TextView mComplete;
    @Bind(R.id.tv_experience_time)
    TextView mTime;
    @Bind(R.id.et_content)
    EditText mEditContent;
    @Bind(R.id.layout_time)
    RelativeLayout mLayoutTime;

    RelativeLayout mTitleLayout;

    private int defaultMode;
    private UserWorkExperienceEntity mResponse;
    private String mWorkId;

    @Inject
    UserExperienceDetailPresenter mDetailPresenter;
    @Inject
    TimePickDialog mTimePickDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_work_experience_detail);
        ButterKnife.bind(this);
        mTitleLayout = (RelativeLayout) findViewById(R.id.title_layout);
        getDataFromIntent();
        initVariables();
    }

    private void initVariables() {
        mTvTitle.setText(R.string.work_experience);
        mComplete.setText(R.string.complete);
        if (MODE_VIEW == defaultMode) {
            mComplete.setVisibility(View.GONE);
            showTransparentPopupWindow();
        } else {
            mComplete.setVisibility(View.VISIBLE);
        }
        showContent();
    }

    private void showTransparentPopupWindow() {
        GlobalTransparentPopupWindow popupWindow = new GlobalTransparentPopupWindow(this);
        popupWindow.showPopupWindow(mTitleLayout);
    }

    private void showContent() {
        if (null != mResponse) {
            mTime.setText(mResponse.getTime());
            mEditContent.setText(mResponse.getContent());
            mEditContent.setSelection(mResponse.getContent().length());
        }
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        mWorkId = intent.getStringExtra(KEY_WORK_ID);
        defaultMode = intent.getIntExtra(KEY_MODE, MODE_ADD);
        mResponse = intent.getParcelableExtra(KEY_JAVA_BEAN);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mDetailPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).userModule(new UserModule()).build().inject(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_other,R.id.layout_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_other:
                switch (defaultMode) {
                    case MODE_ADD:
                        mDetailPresenter.addExperience(mWorkId, mTime.getText().toString().trim(), mEditContent.getText().toString().trim());
                        break;
                    case MODE_EDIT:
                        mDetailPresenter.updateExperience(mResponse.getId(), mTime.getText().toString().trim(), mEditContent.getText().toString().trim());
                        break;
                }
                break;
            case R.id.layout_time:
                showSelectDateDialog(this, mTime);
                break;
        }
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void closeSelf() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showProgressDialog(int stringResId) {
        mBaseActivityDelegate.showProgressDialog(stringResId);
    }

    @Override
    public void hideProgressDialogIfShowing() {
        mBaseActivityDelegate.hideProgressDialogIfShowing();
    }

    @Override
    public void showToast(String errorMsg) {
        mGlobalToast.showShortToast(errorMsg);
    }

    private void showSelectDateDialog(Context context, final TextView textView) {
        mTimePickDialog.setDefaultDate(DateUtils.stringToDate(textView.getText().toString().trim(), DateUtils.YEAR_MONTH_DAY));
        mTimePickDialog.show();
        mTimePickDialog.setOnTimeSelectedListener(new TimePickDialog.OnTimeSelectedListener() {
            @Override
            public void onTimeSelected(Date date) {
                textView.setText(DateUtils.dateToString(date, DateUtils.YEAR_MONTH_DAY));
            }
        });
    }

    public UserWorkExperienceEntity getResponse() {
        return mResponse;
    }
}

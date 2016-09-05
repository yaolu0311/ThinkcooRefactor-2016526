package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerAccountComponent;
import com.thinkcoo.mobile.injector.modules.AccountModule;
import com.thinkcoo.mobile.presentation.mvp.presenters.ChangePhoneNumberPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.ChangePhoneNumberView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.component.CountDownButton;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/8/8.
 */
public class UserChangePhoneNumberActivity extends BaseActivity implements ChangePhoneNumberView {

    @Inject
    ChangePhoneNumberPresenter mChangePhoneNumberPresenter;

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_other)
    TextView mTvOther;
    @Bind(R.id.tv_old_phone_num)
    TextView mTvOldPhoneNum;
    @Bind(R.id.et_new_phone_number)
    EditText mEtNewPhoneNumber;
    @Bind(R.id.et_vcode_content)
    EditText mEtVcodeContent;
    @Bind(R.id.btn_request_vcode)
    CountDownButton mBtnRequestVcode;
    @Bind(R.id.et_user_login_password)
    EditText mEtUserLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone_number);
        ButterKnife.bind(this);

        setupTitle();
        mChangePhoneNumberPresenter.getOldPhoneNumber();
    }

    private void setupTitle() {
        mTvTitle.setVisibility(View.VISIBLE);
        mTvTitle.setText(R.string.modify_phone_number);
        mTvOther.setVisibility(View.VISIBLE);
        mTvOther.setText(R.string.commit);
    }

    public static Intent getJumpIntent(Context context) {
        return new Intent(context, UserChangePhoneNumberActivity.class);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mChangePhoneNumberPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerAccountComponent.builder().accountModule(new AccountModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }

    @Override
    public void setOldPhoneNumber(String oldPhoneNumber) {
        mTvOldPhoneNum.setText(oldPhoneNumber);
    }

    @Override
    public String getNewPhoneNumber() {
        return mEtNewPhoneNumber.getText().toString().trim();
    }

    @Override
    public String getVcodeContent() {
        return mEtVcodeContent.getText().toString().trim();
    }

    @Override
    public void startVcodeCountDown() {
        mBtnRequestVcode.startCountDown();
    }

    @Override
    public void stopVcodeCountDown() {
        mBtnRequestVcode.stopCountDown();
    }

    @Override
    public String getLoginPassword() {
        return mEtUserLoginPassword.getText().toString().trim();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void closeSelf() {
        finish();
    }

    @Override
    public void showToast(String errorMsg) {
        mGlobalToast.showShortToast(errorMsg);
    }

    @OnClick({R.id.iv_back, R.id.btn_request_vcode,R.id.tv_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_request_vcode:
                mChangePhoneNumberPresenter.requestVcode();
                break;
            case R.id.tv_other:
                mChangePhoneNumberPresenter.cummit();
        }
    }
}

package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerAccountComponent;
import com.thinkcoo.mobile.injector.modules.AccountModule;
import com.thinkcoo.mobile.model.entity.License;
import com.thinkcoo.mobile.presentation.mvp.presenters.RequestRegisterPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.RequestRegisterView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.component.CountDownButton;
import javax.inject.Inject;
import javax.inject.Named;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestRegisterActivity extends BaseActivity implements RequestRegisterView {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_account_name)
    EditText etAccountName;
    @Bind(R.id.et_vcode_content)
    EditText etVcodeContent;
    @Bind(R.id.btn_request_vcode)
    CountDownButton btnRequestVcode;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.tv_show_user_license)
    TextView tvShowUserLicense;

    @Inject
    RequestRegisterPresenter mRequestRegisterPresenter;

    @Inject
    @Named("user")
    License mLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_register);
        ButterKnife.bind(this);
        setupViews();
    }

    protected void setupViews() {
        tvTitle.setText(R.string.register);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mRequestRegisterPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerAccountComponent.builder().accountModule(new AccountModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }

    @OnClick({R.id.iv_back, R.id.btn_request_vcode, R.id.btn_next, R.id.tv_show_user_license})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                closeSelf();
                break;
            case R.id.btn_request_vcode:
                mRequestRegisterPresenter.requestObtainVcode();
                break;
            case R.id.btn_next:
                mRequestRegisterPresenter.requestRegister(mLicense);
                break;
            case R.id.tv_show_user_license:
                mNavigator.navigateToUserLicense(this, mLicense);
                break;
        }
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
    public void showToast(String stringResMsg) {
        mGlobalToast.showShortToast(stringResMsg);
    }


    @Override
    public String getAccountName() {
        return etAccountName.getText().toString().trim();
    }

    @Override
    public String getVcodeContent() {
        return etVcodeContent.getText().toString().trim();
    }

    @Override
    public void startVcodeCountDown() {
        btnRequestVcode.startCountDown();
    }

    @Override
    public void stopVcodeCountDown() {
        btnRequestVcode.stopCountDown();
    }

    @Override
    public void gotoCompleteRegisterPage(String accountName) {
        mNavigator.navigateToCompleteRegister(this, accountName);
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void closeSelf() {
        finish();
    }

    public static Intent getRequestRegisterIntent(Context context) {
        Intent intent = new Intent(context,RequestRegisterActivity.class);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btnRequestVcode.stopCountDown();
    }
}

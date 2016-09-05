package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components   .DaggerAccountComponent;
import com.thinkcoo.mobile.injector.modules.AccountModule;
import com.thinkcoo.mobile.model.entity.CheckVcode;
import com.thinkcoo.mobile.presentation.mvp.presenters.RequestFindPasswordPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.RequestFindPasswordView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.component.CountDownButton;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestFindPasswordActivity extends BaseActivity implements RequestFindPasswordView {


    @Inject
    RequestFindPasswordPresenter mRequestFindPasswordPresenter;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_other)
    TextView tvOther;
    @Bind(R.id.iv_more)
    ImageView ivMore;
    @Bind(R.id.et_account_name)
    EditText etAccountName;
    @Bind(R.id.et_vcode_content)
    EditText etVcodeContent;
    @Bind(R.id.btn_request_vcode)
    CountDownButton btnRequestVcode;
    @Bind(R.id.layout)
    LinearLayout layout;
    @Bind(R.id.btn_next)
    Button btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_find_password);
        ButterKnife.bind(this);
        setupViews();
    }

    protected void setupViews() {
        tvTitle.setText(R.string.find_password);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mRequestFindPasswordPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerAccountComponent.builder().accountModule(new AccountModule()).applicationComponent(getApplicationComponent()).build().inject(this);
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
    public void gotoCompleteFindPasswordPage(CheckVcode checkVcode) {
        mNavigator.navigateToCompletefindPassword(this, checkVcode);
    }


    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void closeSelf() {
        finish();
    }

    public static Intent getRequestFindPassWordIntent(Context context) {
        Intent intent = new Intent(context, RequestFindPasswordActivity.class);
        return intent;
    }

    @OnClick({R.id.iv_back, R.id.iv_more,  R.id.btn_request_vcode, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                closeSelf();
                break;
            case R.id.iv_more:
                break;
            case R.id.btn_request_vcode:
                mRequestFindPasswordPresenter.requestObtainVcode();
                break;
            case R.id.btn_next:
                mRequestFindPasswordPresenter.requestFindPassword();
                break;
        }
    }

}

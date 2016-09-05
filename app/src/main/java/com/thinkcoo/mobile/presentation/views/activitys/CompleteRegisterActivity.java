package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerCompleteRegisterComponent;
import com.thinkcoo.mobile.injector.modules.CompleteRegisterModule;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.presentation.mvp.presenters.CompleteRegisterPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.CompleteRegisterView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.component.ClearAndCipherEditText;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompleteRegisterActivity extends BaseActivity implements CompleteRegisterView {

    public static final String EXTRA_ACCOUNT_NAME_KEY = "extra_account_name_key";

    public static Intent getCompleteRegisterIntent(Context context, String accountName) {
        if (TextUtils.isEmpty(accountName)) {
            throw new IllegalArgumentException("accountName is NULL !");
        }

        Intent intent = new Intent(context, CompleteRegisterActivity.class);
        intent.putExtra(EXTRA_ACCOUNT_NAME_KEY, accountName);
        return intent;
    }

    @Bind(R.id.tv_title)
    TextView viewTitle;
    @Bind(R.id.ac_input_pwd_lay)
    ClearAndCipherEditText mClearAndCipherEditText;
    @Bind(R.id.et_accountname)
    EditText etAccountName;

    @Inject
    CompleteRegisterPresenter mCompleteRegisterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_register);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        viewTitle.setText(R.string.register);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mCompleteRegisterPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerCompleteRegisterComponent.builder().applicationComponent(getApplicationComponent()).completeRegisterModule(new CompleteRegisterModule()).build().inject(this);
    }

    @Override
    public String getPassWord() {
        return mClearAndCipherEditText.getPassWord();
    }

    @Override
    public String getUserName() {
        return etAccountName.getText().toString().trim();
    }

    @Override
    public void gotoHomePage() {
        mNavigator.navigateToMainActivity(this);
        finish();
    }

    @Override
    public void gotoLoginPage(Account account) {
        mNavigator.navigateToLoginFillAccount(this,account);
        finish();
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

    @OnClick({R.id.iv_back, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                closeSelf();
                break;
            case R.id.btn_commit:
                mCompleteRegisterPresenter.completeAccountRegister(getIntent().getStringExtra(EXTRA_ACCOUNT_NAME_KEY));
                break;
        }
    }

}

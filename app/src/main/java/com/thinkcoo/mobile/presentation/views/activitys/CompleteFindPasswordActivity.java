package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerAccountComponent;
import com.thinkcoo.mobile.injector.modules.AccountModule;
import com.thinkcoo.mobile.model.entity.CheckVcode;
import com.thinkcoo.mobile.presentation.mvp.presenters.CompleteFindPasswordPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.CompleteFindPasswordView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.component.ClearAndCipherEditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompleteFindPasswordActivity extends BaseActivity implements CompleteFindPasswordView {

    public static final String EXTRA_CHECK_CODE_KEY = "extra_check_code_key";
    @Inject
    CompleteFindPasswordPresenter mCompleteFindPasswordPresenter;
    @Bind(R.id.tv_title)
    TextView viewTitle;
    @Bind(R.id.ac_input_pwd_lay)
    ClearAndCipherEditText mClearAndCipherEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_find_password);
        ButterKnife.bind(this);
        setupViews();
    }
    protected void setupViews() {
        viewTitle.setText(R.string.find_password);
    }
    @Override
    protected MvpPresenter generatePresenter() {
        return mCompleteFindPasswordPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerAccountComponent.builder().accountModule(new AccountModule()).applicationComponent(getApplicationComponent()).build().inject(this);

    }

    @Override
    public String getPassword() {
        return mClearAndCipherEditText.getPassWord();
    }

    @Override
    public void gotoLoginPage() {
        mNavigator.navigateToLogin(this);
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
                mCompleteFindPasswordPresenter.commitNewPassword((CheckVcode)getIntent().getParcelableExtra(EXTRA_CHECK_CODE_KEY));
                break;
        }
    }

    public static Intent getCompleteFindPasswordIntent(Context context, CheckVcode checkVcode) {

        Intent intent = new Intent(context, CompleteFindPasswordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_CHECK_CODE_KEY, checkVcode);
        intent.putExtras(bundle);
        return intent;
    }
}

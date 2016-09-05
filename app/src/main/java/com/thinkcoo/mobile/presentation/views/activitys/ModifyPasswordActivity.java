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
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.presentation.mvp.presenters.ModifyPasswordPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.ModifyPasswordView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.Navigator;
import com.thinkcoo.mobile.presentation.views.component.ClearAndCipherEditText;
import com.thinkcoo.mobile.presentation.views.dialog.GlobalToast;
import com.yz.im.Constant;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyPasswordActivity extends BaseActivity implements ModifyPasswordView{

    public static Intent getJumpActivity(Context context) {
        return new Intent(context, ModifyPasswordActivity.class);
    }

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_other)
    TextView mTvOther;
    @Bind(R.id.et_old_password)
    EditText mOldPassword;
    @Bind(R.id.ac_input_pwd_lay)
    ClearAndCipherEditText mNewPassword;

    @Inject
    ModifyPasswordPresenter mPresenter;
    @Inject
    public GlobalToast mGlobalToast;
    @Inject
    public Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
        initViewAndEvent();
    }

    private void initViewAndEvent() {
        mIvBack.setImageResource(R.drawable.back);
        mIvBack.setVisibility(View.VISIBLE);
        mTvTitle.setText(R.string.personal_about_xgmm);
        mTvOther.setText(R.string.commit);
        mTvOther.setVisibility(View.VISIBLE);
        mNewPassword.setHint(R.string.input_new_password);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).userModule(new UserModule()).build().inject(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_other:
                mPresenter.modifyPassword(mOldPassword.getText().toString().trim(), mNewPassword.getPassWord());
                break;
        }
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

    @Override
    public void goToLoginPage() {
        Intent intent = new Intent(Constant.ACTION_FINISH_ALL_ACTIVITY);
        sendBroadcast(intent);
        Intent intentService = new Intent("yzke.action.login.activity");
        startService(intentService);
    }
}

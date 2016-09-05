package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerAccountComponent;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.AccountModule;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.presentation.mvp.presenters.UserSettingPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.UserSettingView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivityDelegate;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivityDelegateImpl;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/8/8.
 */
public class UserSettingActivity extends BaseActivity implements UserSettingView{

    public static Intent getJumpIntent(Context context) {
        return new Intent(context, UserSettingActivity.class);
    }

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_other)
    TextView mTvOther;
    @Bind(R.id.iv_more)
    ImageView mIvMore;
    @Bind(R.id.layout_num_safety)
    RelativeLayout mLayoutNumSafety;
    @Bind(R.id.view_personal_setone)
    View mViewPersonalSetone;
    @Bind(R.id.layout_grade_integral)
    RelativeLayout mLayoutGradeIntegral;
    @Bind(R.id.view_personal_settwo)
    View mViewPersonalSettwo;
    @Bind(R.id.layout_about)
    RelativeLayout mLayoutAbout;
    @Bind(R.id.view_personal_setthree)
    View mViewPersonalSetthree;
    @Bind(R.id.layout_exit_login)
    TextView mLayoutExitLogin;

    @Inject
    UserSettingPresenter mUserSettingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        ButterKnife.bind(this);
        setupTile();
    }

    private void setupTile() {
        mTvTitle.setText("设置");
    }

    @OnClick({R.id.iv_back, R.id.tv_title, R.id.layout_num_safety, R.id.layout_grade_integral, R.id.layout_about,R.id.layout_exit_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.layout_exit_login:
                mUserSettingPresenter.logout();
                break;
            case R.id.layout_num_safety:
                mNavigator.navigateToUserAccountSafeActivity(this);
                break;
            case R.id.layout_grade_integral:
                break;
            case R.id.layout_about:
                mNavigator.navigateToAboutActivity(UserSettingActivity.this);
                break;
        }
    }

    @Override
    public void gotoLogin(String account) {
        sendBroadcast(new Intent(BaseActivityDelegateImpl.ACTION_FINISH_ALL_ACTIVITY));
        mNavigator.navigateToLoginAndFillAccountName(this,account);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mUserSettingPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerAccountComponent.builder().accountModule(new AccountModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }
}

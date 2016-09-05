package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.UserBasicInfo;
import com.thinkcoo.mobile.presentation.mvp.presenters.UserMainPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.UserMainView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.utils.EasemobConstantsUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserMainActivity extends BaseActivity implements UserMainView {

    @Bind(R.id.iv_headPortrait)
    ImageView ivHeadPortrait;
    @Bind(R.id.tv_thinkcooid)
    TextView tvThinkcooid;
    @Bind(R.id.tv_userName)
    TextView tvUserName;
    @Bind(R.id.tv_signature)
    TextView tvSignature;
    @Bind(R.id.rl_my_info_but)
    RelativeLayout rlMyInfoBut;
    @Bind(R.id.vew_line_fifv)
    View vewLineFifv;
    @Bind(R.id.vew_line_six)
    View vewLineSix;
    @Bind(R.id.iv_icon1)
    ImageView ivIcon1;
    @Bind(R.id.rl_my_state)
    RelativeLayout rlMyState;
    @Bind(R.id.personal_view_zt)
    View personalViewZt;
    @Bind(R.id.iv_icon2)
    ImageView ivIcon2;
    @Bind(R.id.rl_my_harvest)
    RelativeLayout rlMyHarvest;
    @Bind(R.id.personal_view_sh)
    View personalViewSh;
    @Bind(R.id.iv_icon3)
    ImageView ivIcon3;
    @Bind(R.id.rl_my_skills)
    RelativeLayout rlMySkills;
    @Bind(R.id.personal_view_jn)
    View personalViewJn;
    @Bind(R.id.iv_icon4)
    ImageView ivIcon4;
    @Bind(R.id.rl_my_hobby)
    RelativeLayout rlMyHobby;
    @Bind(R.id.vew_line_one)
    View vewLineOne;
    @Bind(R.id.vew_line_two)
    View vewLineTwo;
    @Bind(R.id.iv_icon5)
    ImageView ivIcon5;
    @Bind(R.id.rl_setting)
    RelativeLayout rlSetting;
    @Inject
    UserMainPresenter mUserMainPresenter;
    @Inject
    EasemobConstantsUtils mEasemobConstantsUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        ButterKnife.bind(this);
        setupViews();
    }

    protected void setupViews() {
//        tvTitle.setText(R.string.personal_tittle);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mUserMainPresenter;
    }

    @Override
    protected void initDaggerInject() {
//        DaggerUserComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).userModule(new UserModule()).build().inject(this);
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

    @Override
    protected void onResume() {
        super.onResume();
    }


    @OnClick({R.id.rl_my_info_but, R.id.rl_my_state, R.id.rl_my_harvest, R.id.rl_my_skills, R.id.rl_my_hobby, R.id.rl_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_my_info_but:
                mNavigator.navigateToUserMainInfo(this);
                break;
            case R.id.rl_my_state:
                mNavigator.navigateToUserStatusList(this);
                break;
            case R.id.rl_my_harvest:
                mNavigator.navigateToUserHarvest(this);
                break;
            case R.id.rl_my_skills:
                mNavigator.navigateToUserSkill(this);
                break;
            case R.id.rl_my_hobby:
                mNavigator.navigateToUserHobby(this);
                break;
            case R.id.rl_setting:
                break;
        }
    }

    public static Intent getUserMainIntent(Context context) {
            Intent intent = new Intent(context,UserMainActivity.class);
            return intent;
    }

    @Override
    public void setUserMainInfo(UserBasicInfo userBasicInfo) {

        Glide.with(this).load(userBasicInfo.getHeadPortrait()).placeholder(R.mipmap.default_person_imagemin).into(ivHeadPortrait);
        tvUserName.setText(userBasicInfo.getFullName());
        tvThinkcooid.setText(buildThinkcooId(userBasicInfo));
        tvSignature.setText(userBasicInfo.getSignature());
    }

    private String buildThinkcooId(UserBasicInfo userBasicInfo) {
        return getString(R.string.thinkcooid_id , mEasemobConstantsUtils.getEasemobUserName(Long.parseLong(userBasicInfo.getUserId())));
    }
}

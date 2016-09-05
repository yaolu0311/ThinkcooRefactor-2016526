package com.thinkcoo.mobile.presentation.views.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.ApplicationComponent;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.ActivityModule;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.model.entity.UserBasicInfo;
import com.thinkcoo.mobile.presentation.mvp.presenters.UserMainPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.UserMainView;
import com.thinkcoo.mobile.presentation.views.activitys.MainActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.FragmentInjectHelper;
import com.thinkcoo.mobile.presentation.views.activitys.base.Navigator;
import com.thinkcoo.mobile.utils.EasemobConstantsUtils;
import com.yz.im.utils.GlideRoundTransform;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/27.
 */
public class UserMainFragment extends BaseFragment implements UserMainView {

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
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView tvBack;
    @Inject
    Navigator mNavigator;
    @Inject
    UserMainPresenter mUserMainPresenter;
    @Inject
    EasemobConstantsUtils mEasemobConstantsUtils;

    public UserMainFragment() {
    }
    public static UserMainFragment newInstance() {
        UserMainFragment fragment = new UserMainFragment();
        return fragment;
    }
    protected void setupViews() {
        tvBack.setVisibility(View.GONE);
        tvTitle.setText(R.string.personal_tittle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        setupViews();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        mUserMainPresenter.getUserBasicInfo(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_main;
    }

    @Override
    public void setUserMainInfo(UserBasicInfo userBasicInfo) {
        Glide.with(this).load(userBasicInfo.getHeadPortrait()).transform(new GlideRoundTransform(getActivity())).placeholder(R.mipmap.default_person_imagemin).error(R.mipmap.default_person_imagemin).into(ivHeadPortrait);
        tvUserName.setText(userBasicInfo.getFullName());
        tvThinkcooid.setText(buildThinkcooId(userBasicInfo));
        tvSignature.setText(userBasicInfo.getSignature());
    }

    private String buildThinkcooId(UserBasicInfo userBasicInfo) {
        return getString(R.string.thinkcooid_id , mEasemobConstantsUtils.getEasemobUserName(Long.parseLong(userBasicInfo.getUserId())));
    }

    @Override
    public Context getActivityContext() {
        return getActivity();
    }

    @Override
    public void closeSelf() {
        getActivity().finish();
    }

    @Override
    public void showProgressDialog(int stringResId) {
        getHostActivity().showProgressDialog(stringResId);
    }

    @Override
    public void hideProgressDialogIfShowing() {
        getHostActivity().hideProgressDialogIfShowing();
    }

    @Override
    public void showToast(String errorMsg) {
        getHostActivity().showToast(errorMsg);
    }

    @Override
    protected void initArguments() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected MvpBasePresenter generatePresenter() {
        return mUserMainPresenter;
    }

    @Override
    protected void initDaggerInject() {
        FragmentInjectHelper fragmentInjectHelper = getFragmentInjectHelper();
        ApplicationComponent applicationComponent = fragmentInjectHelper.getApplicationComponent();
        ActivityModule activityModule = fragmentInjectHelper.getActivityModule();
        DaggerUserComponent.builder().applicationComponent(applicationComponent).activityModule(activityModule).userModule(new UserModule()).build().inject(this);
    }

    private MainActivity getHostActivity() {
        return (MainActivity) getActivity();
    }

    @OnClick({R.id.rl_my_info_but, R.id.rl_my_state, R.id.rl_my_harvest, R.id.rl_my_skills, R.id.rl_my_hobby, R.id.rl_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_my_info_but:
                mNavigator.navigateToUserMainInfo(getActivityContext());
                break;
            case R.id.rl_my_state:
                mNavigator.navigateToUserStatusList(getActivityContext());
                break;
            case R.id.rl_my_harvest:
                mNavigator.navigateToUserHarvest(getActivityContext());
                break;
            case R.id.rl_my_skills:
                mNavigator.navigateToUserSkill(getActivityContext());
                break;
            case R.id.rl_my_hobby:
                mNavigator.navigateToUserHobby(getActivityContext());
                break;
            case R.id.rl_setting:
                mNavigator.navigateToUserSetting(getActivityContext());
                break;
        }
    }
}

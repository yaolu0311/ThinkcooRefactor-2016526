package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.layoutmanagers.ScrollSmoothLineaerLayoutManager;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.model.entity.UserWorkExperienceEntity;
import com.thinkcoo.mobile.presentation.mvp.presenters.UserWorkExperiencePresenter;
import com.thinkcoo.mobile.presentation.mvp.views.UserWorkExperienceView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.adapter.User.WorkExperiencesAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserWorkExperienceActivity extends BaseActivity implements UserWorkExperienceView, WorkExperiencesAdapter.ExperienceCallBackListener<UserWorkExperienceEntity> {

    public final static String KEY_WORK_ID = "key_work_id";

    public static Intent getWorkExperienceIntent(Context context, String workId) {
        if (TextUtils.isEmpty(workId)) {
            throw new IllegalArgumentException("workId can not be null when you operate WorkExperience");
        }
        Intent intent = new Intent(context, UserWorkExperienceActivity.class);
        intent.putExtra(KEY_WORK_ID, workId);
        return intent;
    }

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_other)
    TextView mTvOther;
    @Bind(R.id.contentView)
    UltimateRecyclerView mUltimateRecyclerView;

    @Inject
    UserWorkExperiencePresenter mExperiencePresenter;

    private String workId;
    private List<UserWorkExperienceEntity> mList;
    private WorkExperiencesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_work_experience);
        ButterKnife.bind(this);
        getDataFromIntent();
        initVariables();
        initRecyclerView();
        mExperiencePresenter.loadWorkExperienceList(workId);
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        workId = intent.getStringExtra(KEY_WORK_ID);
    }

    private void initVariables() {
        mTvOther.setVisibility(View.VISIBLE);
        mTvTitle.setText(R.string.work_experience);
        mTvOther.setText(R.string.new_add);
    }

    private void initRecyclerView() {
        ScrollSmoothLineaerLayoutManager mLayoutManager = new ScrollSmoothLineaerLayoutManager(this, LinearLayoutManager.VERTICAL, false, 300);
        mUltimateRecyclerView.setHasFixedSize(false);
        mUltimateRecyclerView.setLayoutManager(mLayoutManager);
        mUltimateRecyclerView.addItemDividerDecoration(this);

        //// TODO: 2016/6/20 没有数据时
        mUltimateRecyclerView.setEmptyView(R.layout.text_view, UltimateRecyclerView.STARTWITH_OFFLINE_ITEMS);

        mList = new ArrayList<>();
        mAdapter = new WorkExperiencesAdapter(mList);
        mUltimateRecyclerView.setAdapter(mAdapter);
        mAdapter.setExperienceCallBackListener(this);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mExperiencePresenter;
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
                mNavigator.navigateToWorkExperienceDetailPageByAddMode(this, workId);
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

    @Override
    public void setData(List<UserWorkExperienceEntity> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UserWorkExperienceDetailActivity.REQUEST_EXPERIENCE_CODE && resultCode == RESULT_OK) {
            mExperiencePresenter.loadWorkExperienceList(workId);
        }
    }

    @Override
    public void onDeleteItemListener(UserWorkExperienceEntity userWorkExperienceEntity, int position) {
        mExperiencePresenter.deleteWorkExperience(workId, userWorkExperienceEntity.getId());
    }

    @Override
    public void onItemClickListener(UserWorkExperienceEntity userWorkExperienceEntity, int position) {
        mNavigator.navigateToWorkExperienceDetailPageByEditMode(this, workId, userWorkExperienceEntity);
    }
}

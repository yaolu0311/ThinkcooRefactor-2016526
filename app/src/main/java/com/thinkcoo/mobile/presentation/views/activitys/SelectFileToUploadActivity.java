package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerResourceComponent;
import com.thinkcoo.mobile.injector.modules.ResourceModule;
import com.thinkcoo.mobile.presentation.mvp.presenters.LoadFileListPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.SelectFileToUploadView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.adapter.Schedule.ResourceFileListAdapter;
import com.thinkcoo.mobile.presentation.views.component.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectFileToUploadActivity extends BaseActivity implements SelectFileToUploadView {

    @Bind(R.id.iv_back)
    ImageView mBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.btn_upload)
    Button mBtnUpload;

    @Inject
    LoadFileListPresenter mLoadFileListPresenter;

    public static Intent getJumpInten(Context context) {
        return new Intent(context, SelectFileToUploadActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_file_to_upload);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mTvTitle.setVisibility(View.VISIBLE);
        mTvTitle.setText(R.string.select_file);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(new ResourceFileListAdapter(getMockData()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<String> getMockData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i <30 ; i++) {
            list.add("file" + i);
        }
        return list;
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mLoadFileListPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerResourceComponent.builder().resourceModule(new ResourceModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }

    @OnClick({R.id.iv_back, R.id.btn_upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_upload:
                // TODO: 2016/8/15  show upload dialog
                mNavigator.navigateToDownloadManagerActivity(this);
                break;
        }
    }

    @Override
    public void uploadFile() {

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
}

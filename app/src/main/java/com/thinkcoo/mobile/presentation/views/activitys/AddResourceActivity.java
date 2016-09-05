package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerResourceComponent;
import com.thinkcoo.mobile.injector.modules.ResourceModule;
import com.thinkcoo.mobile.presentation.mvp.presenters.LoadFileListPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.AddResourceView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.adapter.Schedule.ResourceListAdapter;
import com.thinkcoo.mobile.presentation.views.component.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Leevin
 * CreateTime: 2016/8/11  17:07
 */
public class AddResourceActivity extends BaseActivity implements AddResourceView {


    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.et_search_content)
    EditText mEtSearchContent;
    @Bind(R.id.ib_search)
    ImageButton mIbSearch;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    LoadFileListPresenter mLoadFileListPresenter;


    public static Intent getJumpInten(Context context) {
        return new Intent(context, AddResourceActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resource);
        ButterKnife.bind(this);
        mLoadFileListPresenter.loadFileList();// TODO: 2016/8/13
        init();
    }

    private void init() {
        // TODO: 2016/8/12
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(new ResourceListAdapter(getMockData()));

    }

    private List<String> getMockData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i <30 ; i++) {
            list.add("item" + i);
        }
        return list;
    }

    @OnClick({R.id.back, R.id.ib_search,R.id.btn_upload,R.id.btn_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ib_search:
                // TODO: 2016/8/11
                break;
            case R.id.btn_add:
                // TODO: 2016/8/11
                break;
            case R.id.btn_upload:
                mNavigator.navigateToSelectFileToUploadActivity(this);
                break;
        }
    }

    @Override
    public void setResourceData() {

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
    protected MvpPresenter generatePresenter() {
        return mLoadFileListPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerResourceComponent.builder().resourceModule(new ResourceModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }
}

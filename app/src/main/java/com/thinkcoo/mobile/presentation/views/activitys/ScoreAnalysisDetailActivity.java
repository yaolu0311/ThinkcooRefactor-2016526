package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerScheduleComponent;
import com.thinkcoo.mobile.injector.modules.ScheduleModule;
import com.thinkcoo.mobile.model.entity.ClassGroup;
import com.thinkcoo.mobile.presentation.mvp.presenters.ScoreAnalysisPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.ScoreAnalysisView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseScheduleDetailActivity;
import com.thinkcoo.mobile.presentation.views.adapter.Schedule.ClassScoreAdapter;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScoreAnalysisDetailActivity extends BaseActivity implements ScoreAnalysisView {
    @Inject
    ScoreAnalysisPresenter mScoreAnalysisPresenter;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    Event mEvent;
    private ClassScoreAdapter classScoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_results_analysis);
        ButterKnife.bind(this);
        setupViews();
    }

    private void setupViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(mNoticeAdapter);
        mEvent = getIntent().getParcelableExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY);
        mTvTitle.setText(mEvent.title);
        mScoreAnalysisPresenter.loadClassList(mEvent.scheduleId);

    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mScoreAnalysisPresenter;
    }

    @Override
    protected void initDaggerInject() {
//        DaggerScheduleComponent.builder().scheduleModule(new ScheduleModule()).applicationComponent(getApplicationComponent()).build().inject(this);
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




    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;


        }
    }

    public static Intent getScoreAnalysisIntent(Context context, Event event) {
        Intent intent = new Intent(context, ScoreAnalysisDetailActivity.class);
        intent.putExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY, event);
        return intent;
    }

    @Override
    public void setClassList(List<ClassGroup> classGroupList) {
        classScoreAdapter = new ClassScoreAdapter(this, classGroupList);
        recyclerView.setAdapter(classScoreAdapter);
    }
}

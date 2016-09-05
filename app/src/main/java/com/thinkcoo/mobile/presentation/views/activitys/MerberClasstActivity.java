package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerScheduleComponent;
import com.thinkcoo.mobile.injector.modules.ScheduleModule;
import com.thinkcoo.mobile.model.entity.ClassGroup;
import com.thinkcoo.mobile.presentation.mvp.presenters.MerberClassPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.MerBerClassView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseScheduleDetailActivity;
import com.thinkcoo.mobile.presentation.views.adapter.Schedule.ClassScoreAdapter;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;
import com.yz.im.model.entity.serverresponse.GroupMemberResponse;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/8/8.
 */
public class MerberClasstActivity extends BaseActivity implements MerBerClassView {

    @Inject
    MerberClassPresenter mMerberClassPresenter;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    Event mEvent;
    @Bind(R.id.tv_other)
    TextView tvOther;
    @Bind(R.id.iv_more)
    ImageView ivMore;
    @Bind(R.id.ac_lesson_group_imageone)
    ImageView acLessonGroupImageone;
    @Bind(R.id.ac_lesson_group_imagetwo)
    ImageView acLessonGroupImagetwo;
    @Bind(R.id.ac_lesson_group_imagethree)
    ImageView acLessonGroupImagethree;
    @Bind(R.id.ac_lesson_group_imagefour)
    ImageView acLessonGroupImagefour;
    @Bind(R.id.ac_lesson_group_more)
    Button acLessonGroupMore;
    @Bind(R.id.ac_lesson_group_but)
    Button acLessonGroupBut;
    private ClassScoreAdapter classScoreAdapter;
    List<GroupMemberResponse> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merber_class);
        ButterKnife.bind(this);
        setupViews();
    }

    private void setupViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEvent = getIntent().getParcelableExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY);
        mTvTitle.setText(mEvent.title);
        mMerberClassPresenter.loadClassList(mEvent.scheduleId);
        mMerberClassPresenter.loadGroupMemberList(mEvent.circleId);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mMerberClassPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerScheduleComponent.builder().scheduleModule(new ScheduleModule()).applicationComponent(getApplicationComponent()).build().inject(this);
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





    public static Intent getScoreAnalysisIntent(Context context, Event event) {
        Intent intent = new Intent(context, ScoreAnalysisActivity.class);
        intent.putExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY, event);
        return intent;
    }

    @Override
    public void setClassList(List<ClassGroup> classGroupList) {
        classScoreAdapter = new ClassScoreAdapter(this, classGroupList);
        recyclerView.setAdapter(classScoreAdapter);
    }

    @Override
    public void setData(List<GroupMemberResponse> list) {
        list = list;
    }

    @OnClick({R.id.iv_back,R.id.ac_lesson_group_imageone, R.id.ac_lesson_group_imagetwo, R.id.ac_lesson_group_imagethree, R.id.ac_lesson_group_imagefour, R.id.ac_lesson_group_more, R.id.ac_lesson_group_but})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ac_lesson_group_imageone:
                break;
            case R.id.ac_lesson_group_imagetwo:
                break;
            case R.id.ac_lesson_group_imagethree:
                break;
            case R.id.ac_lesson_group_imagefour:
                break;
            case R.id.ac_lesson_group_more:
                break;
            case R.id.ac_lesson_group_but:
                break;
        }
    }
}

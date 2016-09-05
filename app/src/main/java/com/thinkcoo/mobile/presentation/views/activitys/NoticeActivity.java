package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerScheduleComponent;
import com.thinkcoo.mobile.injector.modules.ScheduleModule;
import com.thinkcoo.mobile.model.entity.EventNoticeEntity;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.presentation.mvp.presenters.NoticePresenter;
import com.thinkcoo.mobile.presentation.mvp.views.ManualAddView;
import com.thinkcoo.mobile.presentation.mvp.views.NoticeView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseScheduleDetailActivity;
import com.thinkcoo.mobile.presentation.views.adapter.Schedule.NoticeAdapter;
import com.thinkcoo.mobile.presentation.views.adapter.Schedule.StudentListAdapter;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoticeActivity extends BaseActivity implements NoticeView {
    @Inject
    NoticePresenter mNoticePresenter;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.editText_content)
    EditText editTextContent;
    @Bind(R.id.enter)
    Button enter;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    NoticeAdapter mNoticeAdapter;
    List<EventNoticeEntity> mEventNoticeEntityList = new ArrayList<EventNoticeEntity>();
    Event mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        setupViews();
    }

    private void setupViews() {
        mEvent = getIntent().getParcelableExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY);
        mTvTitle.setText(mEvent.title);
        mNoticePresenter.getNoticeList();
        if (!mEvent.isCreateAuthor) {
            editTextContent.setVisibility(View.GONE);
            enter.setVisibility(View.GONE);
        }
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mNoticePresenter;
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


    void setData(List<EventNoticeEntity> eventNoticeEntityList) {
        mEventNoticeEntityList.clear();
        mEventNoticeEntityList.addAll(eventNoticeEntityList);
        if (mNoticeAdapter == null) {
            mNoticeAdapter = new NoticeAdapter(this, mEventNoticeEntityList);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            recyclerView.setAdapter(mNoticeAdapter);

        } else {
            mNoticeAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void setNoticeList(List<EventNoticeEntity> noticeList) {
        setData(noticeList);
    }

    @Override
    public String getContent() {
        return editTextContent.getText().toString().trim();
    }

    @Override
    public Event getEvent() {
        return mEvent;
    }

    @OnClick({R.id.enter, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.enter:
                mNoticePresenter.addNotice();
                break;

        }
    }

    public static Intent getNoticeIntent(Context context, Event event) {
        Intent intent = new Intent(context, NoticeActivity.class);
        intent.putExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY, event);
        return intent;
    }

}

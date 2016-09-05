package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hyphenate.easeui.R;
import com.thinkcoo.mobile.injector.components.DaggerScheduleComponent;
import com.thinkcoo.mobile.injector.modules.ScheduleModule;
import com.thinkcoo.mobile.presentation.mvp.presenters.ActiveMemberPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.ActivityMerberView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseScheduleDetailActivity;
import com.thinkcoo.mobile.presentation.views.component.ActiveMemberView;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import javax.inject.Inject;

public class ActiveMemberActivity extends BaseActivity implements ActivityMerberView{

    private FrameLayout mFrameLayout;
    private ActiveMemberView mContactListView;
    public Event mEvent;
    public static final String GROUPID_KEY = "groupid_key";
    public String mGroupId;
    @Inject
    public ActiveMemberPresenter mActiveMemberPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        getDataFromIntent();
        initView();
        addFragmentView();
    }

    private void getDataFromIntent() {
        mEvent = getIntent().getParcelableExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY);
        mGroupId = getIntent().getStringExtra(GROUPID_KEY);
    }

    private void initView() {
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_contact);
    }

    private void addFragmentView() {
        mContactListView = new ActiveMemberView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mFrameLayout.addView(mContactListView, params);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mActiveMemberPresenter;
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

    @Override
    public void setResult() {
        setResult(RESULT_OK);
    }
    public static Intent getActiveMemberIntent(Context context, Event event,String groupId) {
        Intent intent = new Intent(context, ActiveMemberActivity.class);
        intent.putExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY, event);
        intent.putExtra(GROUPID_KEY, groupId);
        return intent;
    }
}

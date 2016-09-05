package com.thinkcoo.mobile.presentation.views.activitys.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerScheduleComponent;
import com.thinkcoo.mobile.injector.modules.ScheduleModule;
import com.thinkcoo.mobile.model.entity.Schedule;
import com.thinkcoo.mobile.presentation.mvp.presenters.ScheduleDetailPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.BaseScheduleDetailView;
import com.thinkcoo.mobile.presentation.views.activitys.AddEditScheduleActivity;
import com.thinkcoo.mobile.presentation.views.fragment.ResourceFragment;
import com.thinkcoo.mobile.presentation.views.component.EventMenuPopupWindow;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Utils;
import com.yz.im.ui.base.IMNavigator;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseScheduleDetailActivity extends BaseActivity implements BaseScheduleDetailView {
    public static final int EDIT_SCHEDULE_REQUEST_CODE = 12 ;
    private static final String RESOURCE_FRAGMENT_FLAG = "RESOURCE_FRAGMENT_FLAG";
    public static String EXTRA_EVENT_KEY = "extra_event_key";
    @Inject
    ScheduleDetailPresenter mScheduleDetailPresenter;
    protected Event mEvent;
    @Bind(R.id.tv_activitytitle)
    protected TextView content;
    @Bind(R.id.tv_start)
    protected TextView tvStart;
    @Bind(R.id.tv_date)
    protected TextView tvDate;
    @Bind(R.id.tv_timemore)
    protected TextView tvTimemore;
    @Bind(R.id.tv_end)
    protected TextView tvEnd;
    @Bind(R.id.tv_date_after)
    protected TextView tvDateAfter;
    @Bind(R.id.tv_timemore_after)
    protected TextView tvTimemoreAfter;
    @Bind(R.id.tv_location)
    protected TextView location;
    @Bind(R.id.tv_teachername)
    protected TextView tvTeachername;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    protected TextView tvTitle;
    @Bind(R.id.iv_more)
    protected ImageView ivMore;
    @Bind(R.id.tv_member)
    protected TextView tvMember;
    @Bind(R.id.line_one)
    protected LinearLayout lineOne;
    @Bind(R.id.tv_call)
    protected TextView tvCall;
    @Bind(R.id.line_two)
    protected LinearLayout lineTwo;
    @Bind(R.id.tv_wechat)
    protected TextView tvWechat;
    @Bind(R.id.line_three)
    protected LinearLayout lineThree;
    @Bind(R.id.tv_notice)
    protected TextView tvNotice;
    @Bind(R.id.line_four)
    protected LinearLayout lineFour;
    @Bind(R.id.location_layout)
    protected RelativeLayout locationLayout;
    @Bind(R.id.middle_layout)
    protected LinearLayout middleLayout;
    @Bind(R.id.fl_resource_container)
    FrameLayout mFlResourceContainer;

    @Inject
    public Navigator mNavigator;
    private EventMenuPopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sc_activity);
        ButterKnife.bind(this);
        initView();
        initViews();
    }

    private void initView() {
        mEvent = getIntent().getParcelableExtra(EXTRA_EVENT_KEY);
        tvTitle.setText(R.string.event_detail);
        Utils.setDate(tvDate, mEvent.startMillis, this);
        Utils.setDate(tvDateAfter, mEvent.endMillis, this);
        Utils.setTime(tvTimemore, mEvent.startMillis, this);
        Utils.setTime(tvTimemoreAfter, mEvent.endMillis, this);
        tvTeachername.setText(mEvent.createdUser.getUserName());
        content.setText(mEvent.title);
        location.setText(mEvent.location);
        if (mEvent.isCreateAuthor) {
            ivMore.setVisibility(View.VISIBLE);
            tvCall.setText(R.string.sc_call);
            setPopupWindow();

        } else {
            ivMore.setVisibility(View.GONE);
            tvCall.setText(R.string.sc_sign);
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fl_resource_container, ResourceFragment.getInstance (), RESOURCE_FRAGMENT_FLAG).commit();
    }

    private void setPopupWindow() {
        mPopupWindow = new EventMenuPopupWindow(this);

        mPopupWindow.getDeleteLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScheduleDetailPresenter.Deletevent(mEvent.scheduleId);
            }
        });
        mPopupWindow.getEditeLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNavigator.navigateToEditScheduleToEdit(BaseScheduleDetailActivity.this,mEvent.scheduleId);

            }
        });
    }

    protected abstract void initViews();

    @Override
    protected MvpPresenter generatePresenter() {
        return mScheduleDetailPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerScheduleComponent.builder().scheduleModule(new ScheduleModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }

    @Override
    public void setData() {

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

    }

    @Override
    public void hideProgressDialogIfShowing() {

    }

    @Override
    public void showToast(String errorMsg) {

    }


    @OnClick({R.id.iv_back, R.id.iv_more, R.id.line_one, R.id.line_two, R.id.line_three, R.id.line_four})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                finish();
                break;
            case R.id.iv_more:
                mPopupWindow.showAsDropDown(ivMore);
            break;
            case R.id.line_one:
                mNavigator.navigateToStudentManage(this, mEvent);
                break;
            case R.id.line_two:
                mNavigator.navigateToRockCall(this, mEvent);
                break;
            case R.id.line_three:
                intentTO();
                break;
            case R.id.line_four:
                mNavigator.navigateToNotice(this, mEvent);
                break;
        }
    }

    private void intentTO() {
        if(mEvent.scheduleType==Schedule.SCHEDULE_TYPE_ACTIVITY){
            IMNavigator imNavigator = new IMNavigator();
            imNavigator.navigateToGroupChatActivity(this, mEvent.easemobGroupId);
        }else{
            mNavigator.navigateScoreAnalysis(this, mEvent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK&&data!=null) {
            boolean isdelete  = true;
            Schedule schedule= data.getParcelableExtra(AddEditScheduleActivity.UPDATED_SCHEDULE);
            for(int i = 0;i<schedule.getEventTimeList().size();i++){
                if(schedule.getEventTimeList().get(i).getId().equals(mEvent.id) ){
                    Utils.setDate(tvDate, schedule.getEventTimeList().get(i).getStartMillis(), this);
                    Utils.setDate(tvDateAfter, schedule.getEventTimeList().get(i).getEndMillis(), this);
                    Utils.setTime(tvTimemore, schedule.getEventTimeList().get(i).getStartMillis(), this);
                    Utils.setTime(tvTimemoreAfter, schedule.getEventTimeList().get(i).getEndMillis(), this);
                    tvTeachername.setText(schedule.getEventTimeList().get(i).getCreateUser().getUserName());
                    content.setText(schedule.getEventTimeList().get(i).getEventName());
                    location.setText(schedule.getEventTimeList().get(i).getLocation());
                    isdelete = false;
                }
                if(isdelete){
                   finish();
                }
            }
            return;

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

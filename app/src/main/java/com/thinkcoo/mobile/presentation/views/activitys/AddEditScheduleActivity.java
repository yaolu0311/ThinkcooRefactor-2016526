package com.thinkcoo.mobile.presentation.views.activitys;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.administrator.publicmodule.ui.widget.ActionSheetDialog;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerScheduleComponent;
import com.thinkcoo.mobile.injector.modules.ScheduleModule;
import com.thinkcoo.mobile.model.entity.EventTime;
import com.thinkcoo.mobile.model.entity.Schedule;
import com.thinkcoo.mobile.presentation.mvp.presenters.AddEditSchedulePresenter;
import com.thinkcoo.mobile.presentation.mvp.views.AddEditScheduleView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.component.MoreLayoutView;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Leevin
 * CreateTime: 2016/6/30  10:35
 */
public class AddEditScheduleActivity extends BaseActivity implements AddEditScheduleView, MoreLayoutView.OnMoreViewAddedListener {

    private static final String TAG = "AddEditScheduleActivity";
    public static final String START_MILLIS = "start_millis";
    public static final String END_MILLIS = "end_millis";
    public static final String IS_ALL_DAY = "is_all_day";
    private static final String NO_MODE_ERROR_MSG = "You must pass activity mode to this activity";
    private static final String NO_SCHEDULE_ERROR_MSG = "Your mode is edit mode,you must pass schedule id to this activity";
    public static final String MODE_KEY = "mode";
    public static final String SCHEDULE_ID = "schedule_id";
    public static final int EDIT_MODE = 0x0001;
    public static final int ADD_MODE = 0x0002;
    public static final String UPDATED_SCHEDULE = "updated_schedule";
    protected int mCurrentMode;
    private String mScheduleId;
    private Time mStartTime = new Time();
    private Time mEndTime = new Time();
    private boolean mAllDay;
    private long mStartMillis;
    private long mEndMillis;
    private int mCurrentScheduleType;
    private String[] mScheduleTypeNames;
    private boolean mIsMoreViewOpened = false;
    private MoreLayoutView mMoreLayoutView;
    private Schedule mSchedule;
    private String NO_EDIT_BACKGROUND_COLOR = "#ff666666";
    @Inject
    AddEditSchedulePresenter mAddEditSchedulePresenter;

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_other)
    TextView mTvOther;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_schedule_name)
    EditText mTvScheduleName;
    @Bind(R.id.tv_schedule_type)
    TextView mTvScheduleType;
    @Bind(R.id.ll_schedule_type)
    LinearLayout mLlScheduleType;
    @Bind(R.id.start_date)
    TextView mStartDateTextView;
    @Bind(R.id.end_date)
    TextView mEndDateTextView;
    @Bind(R.id.start_time)
    TextView mStartTimeTextView;
    @Bind(R.id.end_time)
    TextView mEndTimeTextView;
    @Bind(R.id.tv_more_options)
    TextView mMoreOptionsToggle;
    @Bind(R.id.all_day_toggle)
    CheckBox mAllDayToggleButton;
    @Bind(R.id.scroll_view)
    ScrollView mScrollView;
    @Bind(R.id.location)
    EditText etLocation;
    @Bind(R.id.more_options_content)
    LinearLayout mMoreOptionsContext;

    public static Intent getScheduleIntentWithAddMode(Context context, long startMillis, long endMillis, boolean isAllDay) {
        Intent intent = new Intent(context, AddEditScheduleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(START_MILLIS, startMillis);
        bundle.putLong(END_MILLIS, endMillis);
        bundle.putBoolean(IS_ALL_DAY, isAllDay);
        bundle.putInt(MODE_KEY, ADD_MODE);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent getScheduleIntentWithEditMode(Context context,String scheduleId) {
        Intent intent = new Intent(context, AddEditScheduleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(MODE_KEY, EDIT_MODE);
        bundle.putString(SCHEDULE_ID,scheduleId);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mAddEditSchedulePresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerScheduleComponent.builder().scheduleModule(new ScheduleModule()).applicationComponent(getApplicationComponent()).build().inject(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);
        ButterKnife.bind(this);
        getDataFromIntent();
        initViews();
        bindIntentDataOnUI();
        loadSchedule();
    }

    private void bindIntentDataOnUI() {
        // 初始化是否全天
        setAllDayStateView(mAllDay);
        // 初始化时间选择
        Utils.setDate(mStartDateTextView, mStartMillis, AddEditScheduleActivity.this);
        Utils.setDate(mEndDateTextView, mEndMillis, AddEditScheduleActivity.this);
        Utils.setTime(mStartTimeTextView, mStartMillis, AddEditScheduleActivity.this);
        Utils.setTime(mEndTimeTextView, mEndMillis, AddEditScheduleActivity.this);
    }

    private void initViews() {
        mTvTitle.setVisibility(View.VISIBLE);
        mTvOther.setVisibility(View.VISIBLE);
        mTvOther.setText(R.string.save);
        if (mCurrentMode == ADD_MODE) {
            mTvTitle.setText(R.string.new_event);
        } else if (mCurrentMode == EDIT_MODE){
            mTvTitle.setText(R.string.edit_event);
        }

        mScheduleTypeNames = getResources().getStringArray(R.array.schedule_types);
        mAllDayToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setAllDayStateView(isChecked);
            }
        });

        if (mCurrentMode == ADD_MODE) {// 事件类型不可修改
            mLlScheduleType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSelectTypeDialog();
                }
            });
        } else {
//            mLlScheduleType.setBackgroundColor(Color.parseColor(NO_EDIT_BACKGROUND_COLOR));
            mTvScheduleType.setBackgroundColor(Color.parseColor(NO_EDIT_BACKGROUND_COLOR));
        }
        populateWhen();
    }

    private void populateWhen() {
        mStartDateTextView.setOnClickListener(new DateClickListener(mStartTime));
        mEndDateTextView.setOnClickListener(new DateClickListener(mEndTime));
        mStartTimeTextView.setOnClickListener(new TimeClickListener(mStartTime));
        mEndTimeTextView.setOnClickListener(new TimeClickListener(mEndTime));
    }

    public void setAllDayStateView(boolean checkState) {
        mAllDayToggleButton.setChecked(checkState);
        setAllDayViewsVisibility(checkState);
    }

    private void showSelectTypeDialog() {
        ActionSheetDialog mActionSheetDialog = null;
        if(null != mActionSheetDialog){
            mActionSheetDialog.show();
            return;
        }
        mActionSheetDialog = new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(false)
                .setSheetItems(getSheetItemEntitys(mScheduleTypeNames), false)
                .addSheetItemClickListener(new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
                        showSelectTypeByType(sheetItemEntity);
                    }
                });
        mActionSheetDialog.show();
    }

    private void showSelectTypeByType(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
        String name = sheetItemEntity.getItemName();
        mTvScheduleType.setText(name);
        mCurrentScheduleType = getScheduleTypeFromName(name);
        refreshMoreLayout();
    }

    private LinkedList<ActionSheetDialog.SheetItemEntity> getSheetItemEntitys(String[] scheduleTypes) {
        LinkedList<ActionSheetDialog.SheetItemEntity> itemEntities = new LinkedList<>();
        if (null == scheduleTypes) {
            return itemEntities;
        }
        for(int i=0; i<scheduleTypes.length; i++){
            ActionSheetDialog.SheetItemEntity sheetItemEntity = new ActionSheetDialog.SheetItemEntity(scheduleTypes[i], i+"", null);
            itemEntities.add(sheetItemEntity);
        }
        return itemEntities;
    }

    // 根据类型不同更新更多选项布局
    private void refreshMoreLayout() {
        if (!mIsMoreViewOpened && mMoreLayoutView == null) {
            return;
        }
        mMoreLayoutView.refreshView(mCurrentScheduleType);
    }

    private void loadSchedule() {
        if (mCurrentMode == ADD_MODE) {
            return;
        }
        mAddEditSchedulePresenter.loadSchedule(mScheduleId);
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (null == bundle || !bundle.containsKey(MODE_KEY)) {
            throw new IllegalArgumentException(NO_MODE_ERROR_MSG);
        }

        mCurrentMode = bundle.getInt(MODE_KEY);
        if (mCurrentMode == ADD_MODE) {
            mAllDay = bundle.getBoolean(IS_ALL_DAY);

            mStartMillis = bundle.getLong(START_MILLIS, System.currentTimeMillis());
            mStartTime.set(mStartMillis);
            mStartTime.normalize(true);

            mEndMillis = bundle.getLong(END_MILLIS, System.currentTimeMillis() + Utils.HOUR_IN_MILLIS);
            mEndTime.set(mEndMillis);
            mEndTime.normalize(true);
        } else if (mCurrentMode == EDIT_MODE) {
            if (!bundle.containsKey(SCHEDULE_ID)) {
                throw new IllegalArgumentException(NO_SCHEDULE_ERROR_MSG);
            }
            mScheduleId = bundle.getString(SCHEDULE_ID);
        }
    }

    @Override
    public void setSchedule(Schedule schedule) {
        bindScheculeToUI(schedule);
    }

    private void bindScheculeToUI(Schedule schedule) {
        mSchedule = schedule;// 记录打开更多时向下传
        mTvScheduleName.setText(schedule.getName());
        mCurrentScheduleType = schedule.getType();
        mTvScheduleType.setText(mScheduleTypeNames[mCurrentScheduleType - 1]);
        List<EventTime> eventTimeList = schedule.getEventTimeList();
        EventTime eventTime0 = eventTimeList.get(0);

        //  0时段 展示默认时段
        mAllDay = eventTime0.isAllDay();
        setAllDayStateView(mAllDay);

        mStartMillis = eventTime0.getStartMillis();
        mEndMillis = eventTime0.getEndMillis();
        mStartTime.set(mStartMillis);// 日期日间回显
        mEndTime.set(mEndMillis);
        Utils.setDate(mStartDateTextView, mStartMillis, AddEditScheduleActivity.this);
        Utils.setDate(mEndDateTextView, mEndMillis, AddEditScheduleActivity.this);
        Utils.setTime(mStartTimeTextView,mStartMillis, AddEditScheduleActivity.this);
        Utils.setTime(mEndTimeTextView, mEndMillis, AddEditScheduleActivity.this);
        etLocation.setText(eventTime0.getLocation());

        if (!TextUtils.isEmpty(eventTime0.getRepeatType()) || !TextUtils.isEmpty(schedule.getRemindTime()) || !TextUtils.isEmpty(schedule.getRemark()) || !TextUtils.isEmpty(schedule.getOrganization()) || eventTimeList.size() > 1) {
            openMoreOptionView();
        }
    }

    @Override
    public Schedule getSchedule() {
        return getScheduleFromUI();
    }

    private Schedule getScheduleFromUI() {
        Schedule schedule;
        List<EventTime> eventTimeList;
        EventTime eventTime0 ;
        if (mMoreLayoutView != null) {
            schedule = mMoreLayoutView.getScheduleFromMoreView();
            eventTimeList = schedule.getEventTimeList();
            eventTime0 = eventTimeList.get(0);
        } else {
            if (mSchedule == null) {
                // 添加主时段
                schedule = new Schedule();
                eventTimeList = new ArrayList<>();
                eventTime0 = new EventTime();
                eventTimeList.add(eventTime0);
            } else {
                // 更新主时段
                schedule = mSchedule;
                eventTimeList = mSchedule.getEventTimeList();
                eventTime0 = eventTimeList.get(0);
            }
        }

        schedule.setScheduleId(mScheduleId);
        schedule.setName(mTvScheduleName.getText().toString().trim());
        schedule.setType(mCurrentScheduleType);
        eventTime0.setAllDay(mAllDay);
        if (mAllDay) {
            mStartTime.hour = 0;
            mStartTime.minute = 0;
            mStartTime.second = 0;
            mEndTime.hour = 23;
            mEndTime.minute = 59;
            mEndTime.second = 59;
        }

        eventTime0.setStartMillis(mStartTime.toMillis(true));
        eventTime0.setEndMillis(mEndTime.toMillis(true));
        eventTime0.setLocation(etLocation.getText().toString().trim());

        schedule.setEventTimeList(eventTimeList);
        return schedule;
    }

    @OnClick({R.id.iv_back, R.id.tv_other, R.id.tv_more_options})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_other:
                if (mCurrentMode == ADD_MODE) {
                    mAddEditSchedulePresenter.addSchedule();
                } else if (mCurrentMode == EDIT_MODE) {
                    mAddEditSchedulePresenter.updateSchedule();
                }
                break;
            case R.id.tv_more_options:
                if (mSchedule == null) {
                    mSchedule = new Schedule();
                    List<EventTime> eventTimeList = new ArrayList<>();
                    eventTimeList.add(new EventTime());
                    mSchedule.setEventTimeList(eventTimeList);
                }
                toggleMoreOtions();
                break;
        }
    }

    private void toggleMoreOtions() {
        if (mIsMoreViewOpened) {
            closeMoreOptionView();
        } else {
            openMoreOptionView();
        }
    }

    public void openMoreOptionView() {
        mIsMoreViewOpened = true;
        mMoreOptionsToggle.setText(R.string.close_more_options);
        addMoreLayoutView();
    }

    private void addMoreLayoutView() {
        if (mMoreLayoutView == null) {
            mMoreLayoutView = new MoreLayoutView(this);
            // 记录主时段的时间和位置
            EventTime eventTime0 = mSchedule.getEventTimeList().get(0);
            eventTime0.setLocation(etLocation.getText().toString().trim());
            eventTime0.setStartMillis(mStartTime.toMillis(false));
            eventTime0.setEndMillis(mEndTime.toMillis(false));
            mMoreLayoutView.setSchedule(mSchedule);
            mMoreLayoutView.setOnMoreViewAddedListener(this);
        }
        mMoreOptionsContext.addView(mMoreLayoutView);
        scrollToBottom(mScrollView, mMoreOptionsContext);
    }

    public void closeMoreOptionView() {
        mIsMoreViewOpened = false;
        mMoreOptionsToggle.setText(R.string.open_more_options);
        mMoreOptionsContext.removeView(mMoreLayoutView);
    }

    @Override
    public void onMoreViewAdded() {
        scrollToBottom(mScrollView, mMoreOptionsContext);
    }

    public void scrollToBottom(final View scroll, final View inner) {
    Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                if (scroll == null || inner == null) {
                    return;
                }
                int offset = inner.getMeasuredHeight() - scroll.getHeight();
                if (offset < 0) {
                    offset = 0;
            }
                scroll.scrollTo(0, offset);
            }
        },300);
    }

    public int getScheduleTypeFromName(String name) {
        if (mScheduleTypeNames[0].equals(name)) {
            return Schedule.SCHEDULE_TYPE_MANAGER;
        } else if (mScheduleTypeNames[1].equals(name)) {
            return Schedule.SCHEDULE_TYPE_COURSE;
        } else if (mScheduleTypeNames[2].equals(name)) {
            return Schedule.SCHEDULE_TYPE_LEARN;
        } else if (mScheduleTypeNames[3].equals(name)) {
            return Schedule.SCHEDULE_TYPE_ACTIVITY;
        } else {
            throw new IllegalArgumentException("unknow schedule name");
        }
    }

    private class TimeClickListener implements View.OnClickListener {
        private Time mTime;

        public TimeClickListener(Time time) {
            mTime = time;
        }

        @Override
        public void onClick(View v) {
            TimePickerDialog tp = new TimePickerDialog(AddEditScheduleActivity.this, new TimeListener(v), mTime.hour,
                    mTime.minute, DateFormat.is24HourFormat(AddEditScheduleActivity.this));
            tp.setCanceledOnTouchOutside(true);
            tp.show();
        }
    }

    // 更新时间listener
    private class TimeListener implements TimePickerDialog.OnTimeSetListener {
        private View mView;

        public TimeListener(View view) {
            mView = view;
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Cache the member variables locally to avoid inner class overhead.
            Time startTime = mStartTime;
            Time endTime = mEndTime;

            long startMillis;
            long endMillis;
            if (mView == mStartTimeTextView) {
                // The start time was changed.
                int hourDuration = endTime.hour - startTime.hour;
                int minuteDuration = endTime.minute - startTime.minute;

                startTime.hour = hourOfDay;
                startTime.minute = minute;
                startMillis = startTime.normalize(true);

                // Also update the end time to keep the duration constant.
                endTime.hour = hourOfDay + hourDuration;
                endTime.minute = minute + minuteDuration;
            } else {
                // The end time was changed.
                startMillis = startTime.toMillis(true);
                endTime.hour = hourOfDay;
                endTime.minute = minute;

                // Move to the start time if the end time is before the start
                // time.
                if (endTime.before(startTime)) {
                    endTime.monthDay = startTime.monthDay + 1;
                }
            }

            endMillis = endTime.normalize(true);

            Utils.setDate(mEndDateTextView, endMillis, AddEditScheduleActivity.this);
            Utils.setTime(mStartTimeTextView, startMillis, AddEditScheduleActivity.this);
            Utils.setTime(mEndTimeTextView, endMillis, AddEditScheduleActivity.this);

            Log.e(TAG, "onDateSet: " + mStartTime.toString());
            Log.e(TAG, "onDateSet: " + mEndTime.toString());
        }
    }

    private class DateClickListener implements View.OnClickListener {
        private Time mTime;

        public DateClickListener(Time time) {
            mTime = time;
        }

        public void onClick(View v) {
            DatePickerDialog dpd = new DatePickerDialog(AddEditScheduleActivity.this, new DateListener(v), mTime.year, mTime.month, mTime.monthDay);
            dpd.setCanceledOnTouchOutside(true);
            dpd.show();
        }
    }

    private class DateListener implements DatePickerDialog.OnDateSetListener {
        View mView;

        public DateListener(View view) {
            mView = view;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int monthDay) {
            Log.d(TAG, "onDateSet: " + year + " " + month + " " + monthDay);
            // Cache the member variables locally to avoid inner class overhead.
            Time startTime = mStartTime;
            Time endTime = mEndTime;

            long startMillis;
            long endMillis;
            if (mView == mStartDateTextView) {
                // The start date was changed.
                int yearDuration = endTime.year - startTime.year;
                int monthDuration = endTime.month - startTime.month;
                int monthDayDuration = endTime.monthDay - startTime.monthDay;

                startTime.year = year;
                startTime.month = month;
                startTime.monthDay = monthDay;
                startMillis = startTime.normalize(true);

                // Also update the end date to keep the duration constant.
                endTime.year = year + yearDuration;
                endTime.month = month + monthDuration;
                endTime.monthDay = monthDay + monthDayDuration;
                endMillis = endTime.normalize(true);

            } else {
                // The end date was changed.
                startMillis = startTime.toMillis(true);
                endTime.year = year;
                endTime.month = month;
                endTime.monthDay = monthDay;
                endMillis = endTime.normalize(true);

                // Do not allow an event to have an end time before the start time.
                if (endTime.before(startTime)) {
                    endTime.set(startTime);
                    endMillis = startMillis;
                }
            }

            Utils.setDate(mStartDateTextView, startMillis, AddEditScheduleActivity.this);
            Utils.setDate(mEndDateTextView, endMillis, AddEditScheduleActivity.this);
            Utils.setTime(mEndTimeTextView, endMillis, AddEditScheduleActivity.this);

            Log.e(TAG, "onDateSet: " + mStartTime.toString());
            Log.e(TAG, "onDateSet: " + mEndTime.toString());
        }

    }

    // 是否全天事件 控制timeview
    protected void setAllDayViewsVisibility(boolean isChecked) {
        if (isChecked) {
            if (mEndTime.hour == 0 && mEndTime.minute == 0) {
                if (mAllDay != isChecked) {
                    mEndTime.monthDay--;
                }

                long endMillis = mEndTime.normalize(true);

                if (mEndTime.before(mStartTime)) {
                    mEndTime.set(mStartTime);
                    endMillis = mEndTime.normalize(true);
                }
                Utils.setDate(mEndDateTextView, endMillis, AddEditScheduleActivity.this);
                Utils.setTime(mEndTimeTextView, endMillis, AddEditScheduleActivity.this);
            }

            mStartTimeTextView.setVisibility(View.GONE);
            mEndTimeTextView.setVisibility(View.GONE);
        } else {
            if (mEndTime.hour == 0 && mEndTime.minute == 0) {
                if (mAllDay != isChecked) {
                    mEndTime.monthDay++;
                }

                long endMillis = mEndTime.normalize(true);
                Utils.setDate(mEndDateTextView, endMillis, AddEditScheduleActivity.this);
                Utils.setTime(mEndTimeTextView, endMillis, AddEditScheduleActivity.this);
            }
            mStartTimeTextView.setVisibility(View.VISIBLE);
            mEndTimeTextView.setVisibility(View.VISIBLE);
        }
        mAllDay = isChecked;
    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    public void setResultOk(Schedule updatedSchedule) {
        setResult(RESULT_OK,new Intent().putExtra(UPDATED_SCHEDULE,updatedSchedule));
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
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void closeSelf() {
        finish();
    }

}





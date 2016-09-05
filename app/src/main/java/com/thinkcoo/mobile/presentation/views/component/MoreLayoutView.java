package com.thinkcoo.mobile.presentation.views.component;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.EventTime;
import com.thinkcoo.mobile.model.entity.Schedule;
import com.thinkcoo.mobile.model.entity.Weekday;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Leevin
 * CreateTime: 2016/8/3  9:46
 */
public class MoreLayoutView extends LinearLayout implements MoreTimeView.OnDeleteListener {

    private static final int MAX_MORE_TIME_COUNT = 5;
    private LayoutTransition mLayoutTransition;


    public interface OnMoreViewAddedListener {
        void onMoreViewAdded();
    }

    public void setOnMoreViewAddedListener(OnMoreViewAddedListener onMoreViewAddedListener) {
        mOnMoreViewAddedListener = onMoreViewAddedListener;
    }

    @Bind(R.id.tv_repeat_type)
    TextView mTvRepeatType;
    @Bind(R.id.et_organizition)
    EditText mEtOrganizition;
    @Bind(R.id.tv_reminder)
    TextView mTvReminder;
    @Bind(R.id.et_remark)
    EditText mEtRemark;
    @Bind(R.id.more_time_layout_content_view)
    LinearLayout mMoreTimeLayoutContentView;
    @Bind(R.id.add_more_time)
    RelativeLayout mAddMoreTime;

    private int mCurrentRemindPostinon = 0;
    List<MoreTimeView> mMoreTimeViewCache = new ArrayList<>();
    List<String> mDeletedEventTimeIds = new ArrayList<>();
    private OnMoreViewAddedListener mOnMoreViewAddedListener;
    private Schedule mSchedule;
    private int mCurrentIndex = 0;
    private Context mContext;
    private String[] mReminders;
    private String[] mReminderValues;
    private List<Weekday> mRepeatWeekdayList;
    private EventTime mEventTime0 = new EventTime();
    public MoreLayoutView(Context context) {
        super(context);
        mContext = context;
        View view = inflate(context, R.layout.item_more_layout_view, this);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        mReminders = mContext.getResources().getStringArray(R.array.reminder_time);
        mReminderValues = mContext.getResources().getStringArray(R.array.reminder_time_value);
//        setLayoutTranstion();
    }

    private void setLayoutTranstion() {
        mLayoutTransition = new LayoutTransition();
        mMoreTimeLayoutContentView.setLayoutTransition(mLayoutTransition);
        // 出现
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(null, "rotationY",90F,0F).
                setDuration(mLayoutTransition.getDuration(LayoutTransition.APPEARING));
        mLayoutTransition.setAnimator(LayoutTransition.APPEARING, animator1);
        // 消失
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(null, "rotationX", 0F, 90F).
                setDuration(mLayoutTransition.getDuration(LayoutTransition.DISAPPEARING));
        mLayoutTransition.setAnimator(LayoutTransition.DISAPPEARING, animator2);
    }

    private ScrollView getScrollView(View mMoreTimeLayoutContentView) {
        ViewParent parent = mMoreTimeLayoutContentView.getParent();
        if(parent instanceof ScrollView) {
            return (ScrollView) parent;
        } else {
            return getScrollView((View) parent);
        }
    }

    private void setupViews() {
        mEtOrganizition.setText(mSchedule.getOrganization());
        mCurrentRemindPostinon = getReminderPosition(mSchedule.getRemindTime());//记录提醒时间的位置
        mTvReminder.setText(mReminders[mCurrentRemindPostinon]);
        mEtRemark.setText(mSchedule.getRemark());

        List<EventTime> eventTimeList = mSchedule.getEventTimeList();
        mEventTime0 = eventTimeList.get(0);
        mRepeatWeekdayList = mEventTime0.getRepeatWeekdayList();//记录重复
        mTvRepeatType.setText(Utils.getRepeatResultString(mRepeatWeekdayList));
        if ( eventTimeList.size() > 1) {
            for (int i = 1; i < eventTimeList.size() ; i++) {
                ++mCurrentIndex;// 记录时段的索引
                addMoreTimeView(eventTimeList.get(i));
            }
        }
    }

    private int getReminderPosition(String remindTime) {
        int position = 0 ;
        for (int i = 0; i < mReminderValues.length; i++) {
            if (mReminderValues[i].equals(remindTime)) {
                position = i;
                break;
            }
        }
        return position;
    }

    @OnClick({R.id.tv_repeat_type, R.id.tv_reminder, R.id.add_more_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_repeat_type:
                showSelectRepeatDialog();
                break;
            case R.id.tv_reminder:
                showSelectReminderDialog();
                break;
            case R.id.add_more_time:
                ++mCurrentIndex ;
                EventTime eventTime = new EventTime();
                eventTime.setStartMillis(mEventTime0.getStartMillis());
                eventTime.setEndMillis(mEventTime0.getEndMillis());
                eventTime.setLocation(mEventTime0.getLocation());
                addMoreTimeView(eventTime);// 仅add空view传null,区别有数据展示
                break;
        }
    }

    private void showSelectReminderDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle(R.string.reminder_time)
                .setSingleChoiceItems(R.array.reminder_time, mCurrentRemindPostinon, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCurrentRemindPostinon = which;
                    }
                })
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvReminder.setText(mReminders[mCurrentRemindPostinon]);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showSelectRepeatDialog() {
        final boolean[] itemsChecked = new boolean[]{false, false, false, false, false, false, false};
        // 初始化回示
        for (int i = 0; i < mRepeatWeekdayList.size(); i++) {
            Weekday weekday = mRepeatWeekdayList.get(i);
            itemsChecked[i] = weekday.isChecked;
        }
        new AlertDialog.Builder(mContext)
                .setTitle(R.string.repeat)
                .setMultiChoiceItems(R.array.repeats, itemsChecked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        itemsChecked[which] = isChecked;
                    }
                })
                .setPositiveButton(mContext.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取选中结果
                        for (int i = 0; i < itemsChecked.length; i++) {
                            mRepeatWeekdayList.get(i).isChecked = itemsChecked[i];
                        }
                        mTvRepeatType.setText(Utils.getRepeatResultString(mRepeatWeekdayList));
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void addMoreTimeView(EventTime eventTime) {
        if ( mCurrentIndex > MAX_MORE_TIME_COUNT) {
            String text = getContext().getResources().getString(R.string.max_more_time_count);
            String resultText = String.format(text, MAX_MORE_TIME_COUNT);
            Toast.makeText(getContext(), resultText, Toast.LENGTH_SHORT).show();
            return;
        }

        MoreTimeView moreTimeView = new MoreTimeView(getContext());
        moreTimeView.setMoreTimeIndex(mCurrentIndex);
        moreTimeView.setEventTime(eventTime);
        mMoreTimeLayoutContentView.addView(moreTimeView);
        callBackOnMoreViewAdded();
        moreTimeView.setOnDeleteListener(this);
        if (!mMoreTimeViewCache.contains(moreTimeView)) {
            mMoreTimeViewCache.add(moreTimeView);
        }
    }


    private void callBackOnMoreViewAdded() {
        if (null != mOnMoreViewAddedListener){
            mOnMoreViewAddedListener.onMoreViewAdded();
        }
    }

    @Override
    public void onDelete(int index) {
        mMoreTimeLayoutContentView.removeView(mMoreTimeViewCache.get(index));
        saveDeleteTimeEventId(index);
        mMoreTimeViewCache.remove(mMoreTimeViewCache.get(index));
        refreshIndex();
    }

    private void saveDeleteTimeEventId(int index) {
        String id = mMoreTimeViewCache.get(index).getEventTimeFromUI().getId();
        if (id != null) {
            mDeletedEventTimeIds.add(id);
        }
    }

    private void refreshIndex() {
        mCurrentIndex = 0;
        for (int i = 0; i < mMoreTimeViewCache.size(); i++) {
            mMoreTimeViewCache.get(i).setMoreTimeIndex(++mCurrentIndex);
        }
    }

    public void refreshView(int selectScheduleType) {
        showRightMoreView(selectScheduleType);
    }

    private void showRightMoreView(int type) {
        switch (type) {
            case Schedule.SCHEDULE_TYPE_MANAGER:
            case Schedule.SCHEDULE_TYPE_COURSE:
            case Schedule.DEFAULT_TYPE:
                mEtOrganizition.setVisibility(View.VISIBLE);
                break;
            case Schedule.SCHEDULE_TYPE_LEARN:
            case Schedule.SCHEDULE_TYPE_ACTIVITY:
                mEtOrganizition.setVisibility(View.GONE);
                break;
            default:
                throw new IllegalArgumentException("invalide  type");
        }
    }

    public void setSchedule(Schedule schedule) {
        mSchedule = schedule;
        setupViews();
    }

    public Schedule getScheduleFromMoreView() {
        Schedule schedule = new Schedule();
        schedule.setOrganization(mEtOrganizition.getText().toString().trim());
        schedule.setRemark(mEtRemark.getText().toString().trim());
        schedule.setRemindTime(mReminderValues[mCurrentRemindPostinon]);
        // 先赋值删除的id
        schedule.setDeletedEventTimeIds(Utils.list2String(mDeletedEventTimeIds));

        mEventTime0.setRepeatType(Utils.getRepeatResult(mRepeatWeekdayList));
        List<EventTime> eventTimeList = new ArrayList<>();
        eventTimeList.add(mEventTime0);

        if (mMoreTimeViewCache.size() == 0) {
            schedule.setEventTimeList(eventTimeList);
            return schedule;
        }
        for (MoreTimeView moreTimeView : mMoreTimeViewCache) {
            EventTime moreEventTime = moreTimeView.getEventTimeFromUI();
            moreEventTime.setScheduleId(mSchedule.getScheduleId());// 绑定主事件的id
            eventTimeList.add(moreEventTime);
        }

        schedule.setEventTimeList(eventTimeList);
         return schedule;
    }

}

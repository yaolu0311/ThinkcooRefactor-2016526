package com.thinkcoo.mobile.presentation.views.component;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.EventTime;
import com.thinkcoo.mobile.model.entity.Weekday;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Utils;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Leevin
 * CreateTime: 2016/8/3  13:24
 */
public class MoreTimeView extends FrameLayout {

    private static final String TAG = "MoreTimeView" ;
    @Bind(R.id.more_time_index)
    TextView mTvMoreTimeIndex;
    @Bind(R.id.start_date)
    TextView mStartDateTextView;
    @Bind(R.id.start_time)
    TextView mStartTimeTextView;
    @Bind(R.id.end_date)
    TextView mEndDateTextView;
    @Bind(R.id.end_time)
    TextView mEndTimeTextView;
    @Bind(R.id.all_day_toggle)
    CheckBox mAllDayToggleButton;
    @Bind(R.id.location)
    EditText mLocation;
    @Bind(R.id.repeat)
    TextView mRepeat;
    @Bind(R.id.ib_delete)
    ImageButton mIbDelete;

    private Time mStartTime = new Time();
    private Time mEndTime = new Time();
    private Context mContext;
    private long mStartMillis;
    private long mEndMillis;
    private EventTime mEventTime ;
    private int mMoreTimeIndex;
    private OnDeleteListener mOnDeleteListener;
    private String mTimeText;
     boolean[] itemsChecked = new boolean[]{false, false, false, false, false, false, false};
    private List<Weekday> mRepeatWeekdayList;
    private boolean mAllDay;

    public MoreTimeView(Context context) {
        super(context);
        mContext = context;
        View view = inflate(context, R.layout.item_more_time_view, this);
        ButterKnife.bind(this,view);
        init();
    }

    private void init() {
        mTimeText = getContext().getResources().getString(R.string.time_interval_number);

        mIbDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDeleteListener.onDelete(mMoreTimeIndex - 1);
        }
        });

        mAllDayToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setAllDayStateView(isChecked);
            }
        });

        populateWhen();

        mRepeat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectRepeatDialog();
            }
        });
    }

    private void showSelectRepeatDialog() {
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
                        mRepeat.setText(Utils.getRepeatResultString(mRepeatWeekdayList));
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

    private void bindEventTimeToView() {
        getWhenFromEvent();
        Utils.setDate(mStartDateTextView, mStartMillis,mContext);
        Utils.setDate(mEndDateTextView, mEndMillis,mContext);
        Utils.setTime(mStartTimeTextView, mStartMillis,mContext);
        Utils.setTime(mEndTimeTextView, mEndMillis,mContext);

        mAllDay = mEventTime.isAllDay();
        setAllDayStateView(mAllDay);

        mLocation.setText(mEventTime.getLocation());
        mRepeat.setText(Utils.getRepeatResultString(mEventTime.getRepeatWeekdayList()));
        // 初始化multiDialog回示
       mRepeatWeekdayList = mEventTime.getRepeatWeekdayList();
    }

    private void setupIndex() {
        String index = String.format(mTimeText, mMoreTimeIndex);
        mTvMoreTimeIndex.setText(index);
    }

    private void populateWhen() {
        // mStartTime 传入做回显
        mStartDateTextView.setOnClickListener(new DateClickListener(mStartTime));
        mEndDateTextView.setOnClickListener(new DateClickListener(mEndTime));
        mStartTimeTextView.setOnClickListener(new TimeClickListener(mStartTime));
        mEndTimeTextView.setOnClickListener(new TimeClickListener(mEndTime));
    }

    private void getWhenFromEvent() {
        Time time = new Time();
        time.setToNow();
        if (time.minute > 30) {
            time.hour++;
            time.minute = 0;
        } else if (time.minute > 0 && time.minute < 30) {
            time.minute = 30;
        }

        mStartMillis = mEventTime.getStartMillis() == 0? time.toMillis(true) : mEventTime.getStartMillis();
        mEndMillis = mEventTime.getEndMillis() == 0? time.toMillis(true) + Utils.HOUR_IN_MILLIS :mEventTime.getEndMillis();

        mStartTime.set(mStartMillis);
        mStartTime.normalize(true);

        mEndTime.set(mEndMillis);
        mEndTime.normalize(true);
    }


    public void setAllDayStateView(boolean checkState) {
        mAllDayToggleButton.setChecked(checkState);
        setAllDayViewsVisibility(checkState);
    }

    public void setAllDayViewsVisibility(boolean isChecked) {
        if (isChecked) {
            if (mEndTime.hour == 0 && mEndTime.minute == 0) {
                if (mEventTime.isAllDay() != isChecked) {
                    mEndTime.monthDay--;
                }

                long endMillis = mEndTime.normalize(true);

                if (mEndTime.before(mStartTime)) {
                    mEndTime.set(mStartTime);
                    endMillis = mEndTime.normalize(true);
                }
                Utils.setDate(mEndDateTextView, endMillis,mContext);
                Utils.setTime(mEndTimeTextView, endMillis,mContext);
            }

            mStartTimeTextView.setVisibility(View.GONE);
            mEndTimeTextView.setVisibility(View.GONE);
        } else {
            if (mEndTime.hour == 0 && mEndTime.minute == 0) {
                if (mEventTime.isAllDay() != isChecked) {
                    mEndTime.monthDay++;
                }

                long endMillis = mEndTime.normalize(true);
                Utils.setDate(mEndDateTextView, endMillis,mContext);
                Utils.setTime(mEndTimeTextView, endMillis,mContext);
            }
            mStartTimeTextView.setVisibility(View.VISIBLE);
            mEndTimeTextView.setVisibility(View.VISIBLE);
        }
        mAllDay = isChecked;
    }

    private class DateClickListener implements View.OnClickListener {
        private Time mTime;

        public DateClickListener(Time time) {
            mTime = time;
        }

        public void onClick(View v) {
            DatePickerDialog dpd = new DatePickerDialog(
                    mContext, new DateListener(v), mTime.year, mTime.month, mTime.monthDay);
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

            Utils.setDate(mStartDateTextView, startMillis,mContext);
            Utils.setDate(mEndDateTextView, endMillis,mContext);
            Utils.setTime(mEndTimeTextView, endMillis,mContext);
            // TODO: 2016/7/18
            Log.e(TAG, "onDateSet: "+mStartTime.toString());
            Log.e(TAG, "onDateSet: "+mEndTime.toString());
        }
    }

    private class TimeClickListener implements View.OnClickListener {
        private Time mTime;

        public TimeClickListener(Time time) {
            mTime = time;
        }

        @Override
        public void onClick(View v) {
            TimePickerDialog tp = new TimePickerDialog(mContext, new TimeListener(v), mTime.hour,
                    mTime.minute, DateFormat.is24HourFormat(mContext));
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

            Utils.setDate(mEndDateTextView, endMillis,mContext);
            Utils.setTime(mStartTimeTextView, startMillis,mContext);
            Utils.setTime(mEndTimeTextView, endMillis,mContext);

            Log.e(TAG, "onDateSet: "+mStartTime.toString());
            Log.e(TAG, "onDateSet: "+mEndTime.toString());
        }
    }

    public void setEventTime(EventTime eventTime) {
        mEventTime = eventTime;
        bindEventTimeToView();
    }

    public void setMoreTimeIndex(int moreTimeIndex) {
        mMoreTimeIndex = moreTimeIndex;
        setupIndex();
    }

    public int getMoreTimeIndex() {
        return  mMoreTimeIndex ;
    }

    public interface OnDeleteListener{
       void onDelete(int index);
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.mOnDeleteListener = onDeleteListener;
    }

    public EventTime getEventTimeFromUI() {
        mEventTime.setStartMillis(mStartTime.toMillis(true));
        mEventTime.setEndMillis(mEndTime.toMillis(true));
        mEventTime.setAllDay(mAllDay);
        if (mAllDay) {
            mStartTime.hour = 0;
            mStartTime.minute = 0;
            mStartTime.second = 0;
            mEndTime.hour = 23;
            mEndTime.minute = 59;
            mEndTime.second = 59;
        }
        mEventTime.setLocation(mLocation.getText().toString().trim());
        mEventTime.setRepeatType(Utils.getRepeatResult(mRepeatWeekdayList));
        return mEventTime;
    }

}

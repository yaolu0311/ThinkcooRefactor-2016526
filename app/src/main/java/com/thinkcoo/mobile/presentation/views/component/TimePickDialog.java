package com.thinkcoo.mobile.presentation.views.component;


import android.app.Activity;

import com.bigkoo.pickerview.TimePickerView;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by Leevin
 * CreateTime: 2016/6/20  13:07
 */
public class TimePickDialog {

    private static final int START_YEAR = 1900;
    private static final int END_YEAR =  Calendar.getInstance().get(Calendar.YEAR);
    private Activity mActivity;
    private TimePickerView mTimePickerView;
    private OnTimeSelectedListener mSelectedListener;
    @Inject
    public TimePickDialog(Activity activity) {
        mActivity = activity;
        initTimePickView();
    }

    private void initTimePickView() {
        mTimePickerView = new TimePickerView(mActivity,TimePickerView.Type.YEAR_MONTH_DAY);
        mTimePickerView.setRange(START_YEAR,END_YEAR);
        mTimePickerView.setCyclic(false);
        mTimePickerView.setCancelable(true);
        mTimePickerView.setOnTimeSelectListener(mListener);
    }

    public void show() {
        mTimePickerView.show();
    }

    public void setDefaultDate(Date date) {
        mTimePickerView.setTime(date);
    }

    private TimePickerView.OnTimeSelectListener mListener = new TimePickerView.OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date) {
            callBackOnTimeSelected(date);
        }
    };

    private void callBackOnTimeSelected(Date date) {
        if (mSelectedListener != null) {
            mSelectedListener.onTimeSelected(date);
        }
    }

    public interface OnTimeSelectedListener {
        void onTimeSelected(Date date);
    }

    public void setOnTimeSelectedListener(OnTimeSelectedListener lisenter) {
        this.mSelectedListener = lisenter;
    }

}

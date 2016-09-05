package com.thinkcoo.mobile.presentation.views.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.Button;

import com.thinkcoo.mobile.R;

/**
 * 自定义倒计时button
 */
public class CountDownButton extends Button {


    public static final int DEFAULT_MILLIS_IN_FUTURE = 60 * 1000;
    public static final int DEFAULT_COUNT_DOWN_INTERVAL = 1000;

    private CountDownTimer mCountDownTimer;
    private boolean isCountDowning;
    private String initString;

    public CountDownButton(Context context) {
        super(context);
        init(null, 0);
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CountDownButton, defStyle, 0);
        configCountDownTimer(a);
        getInitString(a);
    }

    private void getInitString(TypedArray a) {
        initString = null;// TODO a.getString(com.android.internal.R.styleable.TextView_text);
        if (null == initString) {
            initString = "获取验证码";//TODO in string.xml
        }
    }

    private void configCountDownTimer(TypedArray a) {
        long millisInFuture = a.getInteger(R.styleable.CountDownButton_millisInFuture, DEFAULT_MILLIS_IN_FUTURE);
        long countDownInterval = a.getInteger(R.styleable.CountDownButton_countDownInterval, DEFAULT_COUNT_DOWN_INTERVAL);
        mCountDownTimer = new MyCountDownTimer(millisInFuture, countDownInterval);
    }

    public void startCountDown() {
        if (isCountDowning) {
            return;
        }
        forbidClickButton();
        mCountDownTimer.start();
        isCountDowning = true;
    }

    private void forbidClickButton() {
        setEnabled(false);
        setClickable(false);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.ac_shape_but_gray));
    }

    private void allowClickButton() {
        setEnabled(true);
        setClickable(true);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_shape_round_blue));
    }

    private void stopCountDown(boolean isMyCountDownTimerInvoke) {

        if (isCountDowning) {
            if (!isMyCountDownTimerInvoke) {
                mCountDownTimer.cancel();
            }
            setText(initString);
            allowClickButton();
        }
        isCountDowning = false;
    }

    public void stopCountDown() {
        stopCountDown(false);
    }

    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            setText(makeCountDownText(millisUntilFinished));
        }

        @Override
        public void onFinish() {
            stopCountDown(true);
        }
    }

    private String makeCountDownText(long millisUntilFinished) {
        long realCount = millisUntilFinished / 1000;
        return getContext().getString(R.string.count_down_get_vcode, realCount);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}















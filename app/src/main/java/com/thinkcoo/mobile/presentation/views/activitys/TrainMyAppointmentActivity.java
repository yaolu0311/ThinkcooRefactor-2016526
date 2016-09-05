package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.mvp.presenters.MyAppointmentPresenter;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;

import javax.inject.Inject;

public class TrainMyAppointmentActivity extends BaseSimpleActivity {


    public static Intent getJumpIntent(Context context) {
        return new Intent(context, TrainMyAppointmentActivity.class);
    }

    @Inject
    MyAppointmentPresenter mMyAppointmentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData(false);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mMyAppointmentPresenter;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void setTitle(TextView tvTitle) {
        tvTitle.setText(R.string.my_appointment_course);
    }

    @Override
    protected int getRealContentLayoutResId() {
        return R.layout.activity_train_my_appointment;
    }

    @Override
    protected void getIntentExtras() {
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {
        mMyAppointmentPresenter.loadMyAppointmentList();
    }

}

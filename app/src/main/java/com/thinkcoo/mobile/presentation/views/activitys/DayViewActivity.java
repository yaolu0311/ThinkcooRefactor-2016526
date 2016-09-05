package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;
import com.thinkcoo.mobile.presentation.views.fragment.ScheduleFragment;

/**
 * Created by Leevin
 * CreateTime: 2016/8/15  17:22
 */
public class DayViewActivity extends BaseSimpleActivity{

    private static final String TIME_MILLIS = "time_millis";
    private long mTimeMillis;


    public static Intent getJumpIntent(Context context , long timeMillis) {
        Intent intent = new Intent(context, DayViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(TIME_MILLIS, timeMillis);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);
        getDataFromIntent();
        initFragment();
    }

    private void initFragment() {
        ScheduleFragment scheduleFragment = ScheduleFragment.newInstance(1, mTimeMillis);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,scheduleFragment).commit();
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        mTimeMillis = bundle.getLong(TIME_MILLIS, System.currentTimeMillis());
    }

}

package com.thinkcoo.mobile.presentation.views.activitys;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thinkcoo.mobile.presentation.views.activitys.base.BaseScheduleDetailActivity;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

public class SelfLeanDetailActivity extends BaseScheduleDetailActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
//        locationLayout.setVisibility(View.GONE);
        middleLayout.setVisibility(View.GONE);
    }


    public static Intent getSelfLeanDetailIntent(Context context, Event event) {
        Intent intent = new Intent(context, SelfLeanDetailActivity.class);
        intent.putExtra(EXTRA_EVENT_KEY, event);
        return intent;
    }


}

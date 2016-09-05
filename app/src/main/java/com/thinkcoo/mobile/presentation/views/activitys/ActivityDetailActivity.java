package com.thinkcoo.mobile.presentation.views.activitys;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseScheduleDetailActivity;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

public class ActivityDetailActivity extends BaseScheduleDetailActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        tvWechat.setText(R.string.sc_wechat);
        Drawable topDrawable = getResources().getDrawable(R.mipmap.qunliao);
        topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
        tvWechat.setCompoundDrawables(null, topDrawable, null, null);
    }


    public static Intent getActivityDetailIntent(Context context, Event event) {
        Intent intent = new Intent(context, ActivityDetailActivity.class);
        intent.putExtra(EXTRA_EVENT_KEY, event);
        return intent;
    }


}

package com.thinkcoo.mobile.presentation.views.activitys;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseScheduleDetailActivity;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import butterknife.OnClick;

public class CourseDetailActivity extends BaseScheduleDetailActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        tvWechat.setText(R.string.score_analyse);
        Drawable topDrawable = getResources().getDrawable(R.mipmap.performance);
        topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
        tvWechat.setCompoundDrawables(null, topDrawable, null, null);

    }


    public static Intent getCourseDetailIntent(Context context, Event event) {
        Intent intent = new Intent(context, CourseDetailActivity.class);
        intent.putExtra(EXTRA_EVENT_KEY, event);
        return intent;

    }


}

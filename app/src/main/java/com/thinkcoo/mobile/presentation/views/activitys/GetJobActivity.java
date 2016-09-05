package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;

/**
 * Created by Robert.yao on 2016/7/21.
 */
public class GetJobActivity extends BaseActivity{

    public static Intent getJumpIntent(Context context) {
        return new Intent(context, GetJobActivity.class);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return null;
    }

    @Override
    protected void initDaggerInject() {

    }
}

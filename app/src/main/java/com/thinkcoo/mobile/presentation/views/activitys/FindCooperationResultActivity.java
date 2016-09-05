package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.os.Bundle;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.FindCooperationMainActivityView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;

public class FindCooperationResultActivity extends BaseActivity implements FindCooperationMainActivityView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return null;
    }

    @Override
    protected void initDaggerInject() {
        // TODO: 2016/8/2

    }

    @Override
    public String getKeyword() {
        return null;
    }


    @Override
    public Context getActivityContext() {
        return null;
    }

    @Override
    public void closeSelf() {

    }

    @Override
    public void showProgressDialog(int stringResId) {

    }

    @Override
    public void hideProgressDialogIfShowing() {

    }

    @Override
    public void showToast(String errorMsg) {

    }
}

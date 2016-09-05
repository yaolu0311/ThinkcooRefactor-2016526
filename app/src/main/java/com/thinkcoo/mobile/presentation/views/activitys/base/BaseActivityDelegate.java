package com.thinkcoo.mobile.presentation.views.activitys.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thinkcoo.mobile.injector.components.ApplicationComponent;

/**
 * Created by Robert.yao on 2016/3/24.
 */
public interface BaseActivityDelegate{

    void setupHostActivity(AppCompatActivity activity);

    /**
     * This method must be called from {@link Activity#onCreate(Bundle)}.
     * This method internally creates the presenter and attaches the view to it.
     */
     void onCreate(Bundle bundle);

    /**
     * This method must be called from {@link Activity#onDestroy()}}.
     * This method internally detaches the view from presenter
     */
     void onDestroy();

    /**
     * This method must be called from {@link Activity#onPause()}
     */
     void onPause();

    /**
     * This method must be called from {@link Activity#onResume()}
     */
     void onResume();

    /**
     * This method must be called from {@link Activity#onStart()}
     */
     void onStart();

    /**
     * This method must be called from {@link Activity#onStop()}
     */
     void onStop();

    /**
     * This method must be called from {@link Activity#onRestart()}
     */
     void onRestart();

    /**
     * This method must be called from {@link Activity#onContentChanged()}
     */
     void onContentChanged();

    /**
     * This method must be called from {@link Activity#onSaveInstanceState(Bundle)}
     */
     void onSaveInstanceState(Bundle outState);
    /**
     * 显示一个等待弹窗
     * @param stringResId
     */
    void showProgressDialog(int stringResId);
    /**
     * 关闭一个现实中的等待弹窗
     */
    void hideProgressDialogIfShowing();

}

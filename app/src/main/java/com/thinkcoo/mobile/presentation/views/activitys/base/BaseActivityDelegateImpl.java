package com.thinkcoo.mobile.presentation.views.activitys.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.administrator.publicmodule.ui.widget.BaseDialog;
import com.yz.im.Constant;
import com.yz.im.ui.widget.RemoteLoginDialog;

import java.util.List;


/**
 * Created by Robert.yao on 2016/3/24.
 */
public class BaseActivityDelegateImpl implements BaseActivityDelegate {

    public static final String ACTION_FINISH_ALL_ACTIVITY = "action_finish_all_activity";

    AppCompatActivity mActivity;
    ProgressDialog mProgressDialog;
    BaseDialog mDialog;
    Navigator mNavigator;
    PublicBroadCastReceiver mPublicBroadCastReceiver;


    public BaseActivityDelegateImpl(Navigator navigator) {
        mNavigator = navigator;
    }

    @Override
    public void setupHostActivity(AppCompatActivity appCompatActivity) {
        mActivity = appCompatActivity;
    }

    public void initSetUpProgressDialog() {
        mProgressDialog = new ProgressDialog(mActivity);
    }

    @Override
    public void onCreate(Bundle bundle) {
        registerPublicBroadCastReceiver();
    }

    @Override
    public void onDestroy() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        unRegisterBroadcastReceiver();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {}

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onContentChanged() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void showProgressDialog(int stringResId) {

        if (null == mProgressDialog) {
            initSetUpProgressDialog();
        }
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        mProgressDialog.setMessage(mActivity.getString(stringResId));
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialogIfShowing() {
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    private void registerPublicBroadCastReceiver() {

        mPublicBroadCastReceiver = new PublicBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_LOGIN_ANOTHER_DEVICE);
        filter.addAction(Constant.ACTION_USER_REMOVED);
        filter.addAction(ACTION_FINISH_ALL_ACTIVITY);
        mActivity.registerReceiver(mPublicBroadCastReceiver, filter);


    }


    private void unRegisterBroadcastReceiver() {
        mActivity.unregisterReceiver(mPublicBroadCastReceiver);
    }

    class PublicBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.ACTION_LOGIN_ANOTHER_DEVICE:
                    if (isTopActivity(mActivity.getClass().getName())){
                        showRemoteLoginDialog();
                    }
                    break;
                case Constant.ACTION_USER_REMOVED:
                    Toast.makeText(mActivity, "用户被移除", Toast.LENGTH_SHORT).show();
                    break;
                case ACTION_FINISH_ALL_ACTIVITY:
                    mActivity.finish();
                    break;
            }
        }
    }

    private void showRemoteLoginDialog() {

        if (mActivity.isFinishing()){
            return;
        }
        if (null != mDialog){
            mDialog.dismiss();
        }
        RemoteLoginDialog loginDialog = new RemoteLoginDialog(mActivity);
        loginDialog.show();
    }

    private boolean isTopActivity(String name){
        if (null == name){
            return false;
        }
        ActivityManager manager = (ActivityManager) mActivity.getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = manager.getRunningTasks(1);
        String cmpNameTemp = null;
        if(null != taskInfo){
            cmpNameTemp = taskInfo.get(0).topActivity.getClassName();
        }
        return name.equals(cmpNameTemp);
    }

}

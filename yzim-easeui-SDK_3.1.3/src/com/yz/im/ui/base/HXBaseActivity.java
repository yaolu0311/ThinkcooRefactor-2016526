package com.yz.im.ui.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.administrator.publicmodule.ui.widget.BaseDialog;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.yz.im.Constant;
import com.yz.im.model.entity.IMInitFlag;
import com.yz.im.mvp.IMBaseMvpActivity;
import com.yz.im.mvp.IMMvpView;
import com.yz.im.ui.widget.RemoteLoginDialog;
import com.yz.im.utils.ToastUtil;

import java.util.List;

public abstract class HXBaseActivity extends IMBaseMvpActivity implements IMMvpView {

    public String TAG = HXBaseActivity.class.getName();

    private BaseDialog mDialog;
    private PublicBroadCastReceiver mPublicBroadCastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (IMInitFlag.getInstance().allDone()) {
            continueOnCreate(savedInstanceState);
            registerPublicBroadCastReceiver();
        } else {
            mNavigator.navigateToExceptionActivity(this);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBroadcastReceiver();
    }

    @Override
    public void closeSelf() {
        finish();
    }

    private void registerPublicBroadCastReceiver() {
        mPublicBroadCastReceiver = new PublicBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_LOGIN_ANOTHER_DEVICE);
        filter.addAction(Constant.ACTION_USER_REMOVED);
        filter.addAction(Constant.ACTION_FINISH_ALL_ACTIVITY);
        registerReceiver(mPublicBroadCastReceiver, filter);
    }


    private void unRegisterBroadcastReceiver() {
        try {
            unregisterReceiver(mPublicBroadCastReceiver);
        } catch (Exception e) {
            ThinkcooLog.e(TAG, e.getMessage(), e);
        }
    }

    private void showRemoteLoginDialog() {
        RemoteLoginDialog loginDialog = new RemoteLoginDialog(this);
        loginDialog.show();
    }

    private boolean isTopActivity(String name) {
        if (null == name) {
            return false;
        }
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity
                .ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = manager.getRunningTasks(1);
        String cmpNameTemp = null;
        if (null != taskInfo) {
            cmpNameTemp = taskInfo.get(0).topActivity.getClassName();
        }
        return name.equals(cmpNameTemp);
    }

    protected abstract void continueOnCreate(Bundle savedInstanceState);

    class PublicBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.ACTION_LOGIN_ANOTHER_DEVICE:
                    if (isTopActivity(HXBaseActivity.this.getClass().getName())) {
                        showRemoteLoginDialog();
                    }
                    break;
                case Constant.ACTION_USER_REMOVED:
                    ToastUtil.getInstance(HXBaseActivity.this).showToast("用户被移除");
                    break;
                case Constant.ACTION_FINISH_ALL_ACTIVITY:
                    finish();
                    break;
            }
        }
    }

}

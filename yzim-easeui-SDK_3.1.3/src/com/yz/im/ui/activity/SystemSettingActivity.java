package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hyphenate.easeui.R;
import com.yz.im.Constant;
import com.yz.im.model.cache.SystemSettingCache;
import com.yz.im.model.db.entity.SystemSetting;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.SystemSettingContact;
import com.yz.im.mvp.presenter.SystemSettingPresenter;
import com.yz.im.ui.base.HXBaseActivity;

public class SystemSettingActivity extends HXBaseActivity implements View.OnClickListener, SystemSettingContact.SystemSettingView {

    public static Intent getSettingIntent(Context context) {
        Intent intent = new Intent(context, SystemSettingActivity.class);
        return intent;
    }

    private ImageView imgBack;
    private TextView mTitle;
    private RelativeLayout layoutMsgNotice;
    private ToggleButton mToggleNotice;
    private RelativeLayout layoutFriendVerify;
    private ToggleButton mToggleVerify;
    private RelativeLayout layoutStrangerMsg;
    private ToggleButton mToggleStranger;
    private RelativeLayout mBlackList;

    private SystemSettingPresenter mPresenter;
    private SystemSettingCache mSettingCache;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_system_setting);
        initViewAndEvent();
        refreshStatus();
    }

    private void initViewAndEvent() {
        imgBack = (ImageView) findViewById(R.id.image_left);
        mTitle = (TextView) findViewById(R.id.text_title);
        mTitle.setText(R.string.setting);
        imgBack.setImageResource(R.drawable.back);
        imgBack.setVisibility(View.VISIBLE);
        layoutMsgNotice = (RelativeLayout) findViewById(R.id.setting_msg_notice);
        layoutFriendVerify = (RelativeLayout) findViewById(R.id.setting_friend_verify);
        layoutStrangerMsg = (RelativeLayout) findViewById(R.id.setting_stranger_msg);
        mToggleNotice = (ToggleButton) findViewById(R.id.slipButton_switch1);
        mToggleVerify = (ToggleButton) findViewById(R.id.slipButton_switch2);
        mToggleStranger = (ToggleButton) findViewById(R.id.slipButton_switch3);
        mBlackList = (RelativeLayout) findViewById(R.id.setting_black_list);

        imgBack.setOnClickListener(this);
        layoutMsgNotice.setOnClickListener(this);
        layoutFriendVerify.setOnClickListener(this);
        layoutStrangerMsg.setOnClickListener(this);
        mBlackList.setOnClickListener(this);
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mPresenter = new SystemSettingPresenter(this);
        return mPresenter;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.image_left) {
            finish();
        } else if (id == R.id.setting_msg_notice) {
            mPresenter.updateSetting(getReverseStatus(mToggleNotice), getCurrentStatus(mToggleVerify), getCurrentStatus(mToggleStranger));
        } else if (id == R.id.setting_friend_verify) {
            mPresenter.updateSetting(getCurrentStatus(mToggleNotice), getReverseStatus(mToggleVerify), getCurrentStatus(mToggleStranger));
        } else if (id == R.id.setting_stranger_msg) {
            mPresenter.updateSetting(getCurrentStatus(mToggleNotice), getCurrentStatus(mToggleVerify), getReverseStatus(mToggleStranger));
        } else if (id == R.id.setting_black_list) {
            mNavigator.navigateToBlackUserListActivity(this);
        }
    }

    private String getCurrentStatus(ToggleButton toggle) {
        return toggle.isChecked() ? Constant.STATUS_CHECKED : Constant.STATUS_UNCHECKED;
    }

    private String getReverseStatus(ToggleButton toggle) {
        return toggle.isChecked() ? Constant.STATUS_UNCHECKED : Constant.STATUS_CHECKED;
    }

    @Override
    public void refreshStatus() {
        if (mSettingCache == null) {
            mSettingCache = SystemSettingCache.getInstance(this);
        }
        SystemSetting systemSetting = mSettingCache.getEntity(SystemSettingCache.KEY_SINGLE);
        setStatusToWidget(systemSetting);
    }

    private void setStatusToWidget(SystemSetting systemSetting) {
        if (systemSetting == null) {
            return;
        }
        mToggleNotice.setChecked(judgeStatus(systemSetting.getIsMessageRemind()));
        mToggleVerify.setChecked(judgeStatus(systemSetting.getIsVerify()));
        mToggleStranger.setChecked(judgeStatus(systemSetting.getIsReceveStranger()));
    }

    private boolean judgeStatus(String status) {
        if (TextUtils.isEmpty(status)) {
            return false;
        }
        return status.equals(Constant.STATUS_CHECKED) ? true : false;
    }
}

package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hyphenate.easeui.R;
import com.yz.im.model.entity.serverresponse.NoticeMessageResponse;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.MessageNoticeContact;
import com.yz.im.mvp.presenter.NoticeMessagePresenter;
import com.yz.im.ui.base.IMNavigator;
import com.yz.im.ui.fragment.NoticeOtherFragment;
import com.yz.im.ui.fragment.NoticeVerifyFragment;
import com.yz.im.utils.PreferenceManager;

import java.util.List;

public class NoticeMessageActivity extends FragmentContainerActivity implements MessageNoticeContact.NoticeView {

    public static String TYPE_VERIFY_MSG = "0";
    public static String TYPE_OTHER_MSG = "1";

    public static String ACTION_AGREE_VERIFY = "0";
    public static String ACTION_DELETE_VERIFY_MSG = "2";
    public static String ACTION_DELETE_OTHRE_MSG = "3";

    public static final String MSG_FROM_FRIEND = "0";
    public static final String MSG_FROM_GROUP = "2";

    public static Intent getMessageNoticeIntent(Context context) {
        Intent intent = new Intent(context, NoticeMessageActivity.class);
        return intent;
    }

    private NoticeMessagePresenter mPresenter;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        super.continueOnCreate(savedInstanceState);
    }

    @Override
    void setTitleText() {
        mTitle.setText(R.string.messages);
        tvFindGroup.setText(R.string.other_msg_notice);
        tvFindUser.setText(R.string.verify_msg);
    }

    @Override
    void initFragment() {
        leftFragment = new NoticeVerifyFragment();
        rightFragment = new NoticeOtherFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_find, leftFragment, TAG_LEFT)
                .add(R.id.frame_find, rightFragment, TAG_RIGHT)
                .hide(rightFragment)
                .commit();
        currentTag = TAG_LEFT;
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mPresenter = new NoticeMessagePresenter(this);
        return mPresenter;
    }

    @Override
    public void setVerifyMsgList(List<NoticeMessageResponse> list) {
        if (list == null) {
            return;
        }
        ((NoticeVerifyFragment) leftFragment).refreshData(list);
    }

    @Override
    public void setNoticeMsgList(List<NoticeMessageResponse> list) {
        if (list == null) {
            return;
        }
        ((NoticeOtherFragment) rightFragment).refreshData(list);
    }

    @Override
    public void refreshVerifyMessageData() {
        mPresenter.loadMessage("0");
    }

    @Override
    public void refreshOtherMessageData() {
        mPresenter.loadMessage("1");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.tag_find_user) {
            currentTag = TAG_LEFT;
            changeFragmentByTag();
        } else if (id == R.id.tag_find_group) {
            currentTag = TAG_RIGHT;
            changeFragmentByTag();
        }
    }

    public NoticeMessagePresenter getPresenter() {
        return mPresenter;
    }

    public IMNavigator getNavigate(){
        return mNavigator;
    }

    @Override
    protected void onPause() {
        super.onStop();
        PreferenceManager.getInstance().setNewNotification(false);
    }
}

package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.R;
import com.yz.im.model.entity.serverresponse.StrangerInfoResponse;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.StrangerInfoContact;
import com.yz.im.mvp.presenter.StrangerPresenter;
import com.yz.im.utils.GlideRoundTransform;

public class StrangerInfoActivity extends ProfileActivity implements StrangerInfoContact.StrangerInfoView {

    private static final String OPEN_MSG_SHIELD = "1";
    private static final String CLOSE_MSG_SHIELD = "0";

    public static Intent getStrangerInfoActivityIntent(Context context, String friendId) {
        if (TextUtils.isEmpty(friendId)) {
            throw new NullPointerException("friendId is null");
        }
        Intent intent = new Intent(context, StrangerInfoActivity.class);
        intent.putExtra(KEY_USER_ID, friendId);
        return intent;
    }

    private RelativeLayout layout_shield;
    private ToggleButton mShieldToggle;
    private Button btn_send_msg;
    private Button btn_add_friend;

    private ToggleButton currentBtn;

    private StrangerInfoResponse mInfoResponse;
    private StrangerPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.loadStrangerInfo(userId);
    }

    @Override
    protected void initViewAndEvent() {
        super.initViewAndEvent();
        title.setText(R.string.detail_info);
        mShieldToggle = (ToggleButton) findViewById(R.id.toggle_msg_shield);
        layout_shield = (RelativeLayout) findViewById(R.id.rl_msg_shield);
        btn_send_msg = (Button) findViewById(R.id.btn_send_msg);
        btn_add_friend = (Button) findViewById(R.id.btn_add_friend);

        btn_add_friend.setOnClickListener(this);
        btn_send_msg.setOnClickListener(this);
        layout_shield.setOnClickListener(this);
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mPresenter = new StrangerPresenter(this);
        return mPresenter;
    }

    @Override
    void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getStringExtra(KEY_USER_ID);
        }
    }

    @Override
    int getDetailLayoutId() {
        return R.layout.part_stranger_info;
    }

    @Override
    void refreshData() {

    }

    @Override
    protected String getPictureUri() {
        if (mInfoResponse == null) {
            return "";
        }
        return mInfoResponse.getImager();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.btn_send_msg) {
            mNavigator.navigateToSingleChatActivity(this, userId);
        } else if (id == R.id.btn_add_friend) {
            mNavigator.navigateToSendInviteUserReasonPage(this, userId);
        } else if (id == R.id.rl_msg_shield) {
            currentBtn = mShieldToggle;
            mPresenter.updateShieldStatus(userId, (!mShieldToggle.isChecked()) ? OPEN_MSG_SHIELD : CLOSE_MSG_SHIELD);
        }
    }

    @Override
    public void setData(StrangerInfoResponse response) {
        if (response == null) {
            return;
        }
        mInfoResponse = response;

        Glide.with(this).load(mInfoResponse.getImager()).transform(new GlideRoundTransform(this, 10))
                .placeholder(R.drawable.default_avatar).error(R.drawable.default_avatar).into(mHead);
        mUserRemark.setText(mInfoResponse.getName());
        mUserNum.setText(getString(R.string.thinkcoo, mInfoResponse.getUserId()));

        if (getString(R.string.man).equals(mInfoResponse.getSex())) {
            mFriendSex.setImageResource(R.drawable.man);
        } else if (getString(R.string.women).equals(mInfoResponse.getSex())) {
            mFriendSex.setImageResource(R.drawable.women);
        } else {
            mFriendSex.setImageResource(R.drawable.defaule_sex_image);
        }

        mShieldToggle.setChecked("1".equals(mInfoResponse.getShield()));
    }

    @Override
    public void toggleButtonStatus() {
        if (currentBtn == null) {
            return;
        }
        currentBtn.setChecked(!currentBtn.isChecked());
    }
}

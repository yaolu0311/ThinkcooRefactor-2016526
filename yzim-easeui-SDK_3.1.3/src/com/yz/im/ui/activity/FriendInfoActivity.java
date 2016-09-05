package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.R;
import com.yz.im.model.entity.serverresponse.FriendInfoResponse;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.ProfileContract;
import com.yz.im.mvp.presenter.FriendInfoPresenter;
import com.yz.im.utils.GlideRoundTransform;

public class FriendInfoActivity extends ProfileActivity implements ProfileContract.ProfileView {

    private static final String DELETE_FRIEND = "1";

    public static final int REQUEST_FRIEND_REMARK_NAME = 0x0001;

    private static final String STATUS_CHECKED = "1";
    private static final String STATUS_UNCHECKED = "0";

    public static Intent getFriendInfoActivityIntent(Context context, String friendId) {
        if (TextUtils.isEmpty(friendId)) {
            throw new NullPointerException("friendId is null");
        }
        Intent intent = new Intent(context, FriendInfoActivity.class);
        intent.putExtra(KEY_USER_ID, friendId);
        return intent;
    }

    private TextView mSignature;
    private RelativeLayout layoutPushMsgTop;
    private ToggleButton mPushMsgTop;
    private RelativeLayout layoutMsgDisturb;
    private ToggleButton mMsgDisturb;
    private RelativeLayout layoutAddToBlack;
    private ToggleButton mAddToBlack;
    private RelativeLayout mModifyRemark;
    private RelativeLayout mReportUser;
    private RelativeLayout mQuestionUser;
    private Button mSendMsg;
    private Button mDeleteUser;

    private ToggleButton currentBtn;

    private FriendInfoPresenter mPresenter;
    private FriendInfoResponse mInfoResponse;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
        initViewAndEvent();
        getDataFromIntent();
        mPresenter.loadFriendInfo(userId);
    }

    @Override
    protected void initViewAndEvent() {
        super.initViewAndEvent();
        mSignature = (TextView) findViewById(R.id.tv_signature);
        layoutPushMsgTop = (RelativeLayout) findViewById(R.id.rl_push_top);
        layoutMsgDisturb = (RelativeLayout) findViewById(R.id.rl_msg_disturb);
        layoutAddToBlack = (RelativeLayout) findViewById(R.id.rl_black_user);
        mPushMsgTop = (ToggleButton) findViewById(R.id.toggle_push_top);
        mMsgDisturb = (ToggleButton) findViewById(R.id.toggle_msg_disturb);
        mAddToBlack = (ToggleButton) findViewById(R.id.toggle_black_user);
        mModifyRemark = (RelativeLayout) findViewById(R.id.layout_update_nickname);
        mReportUser = (RelativeLayout) findViewById(R.id.layout_report_user);
        mQuestionUser = (RelativeLayout) findViewById(R.id.layout_question_user);
        mSendMsg = (Button) findViewById(R.id.btn_send_msg);
        mDeleteUser = (Button) findViewById(R.id.btn_delete_friend);

        title.setText(R.string.friend_setting);

        mSendMsg.setOnClickListener(this);
        mDeleteUser.setOnClickListener(this);
        mModifyRemark.setOnClickListener(this);
        mReportUser.setOnClickListener(this);
        layoutPushMsgTop.setOnClickListener(this);
        layoutMsgDisturb.setOnClickListener(this);
        layoutAddToBlack.setOnClickListener(this);
    }

    @Override
    protected void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getStringExtra(KEY_USER_ID);
        }
    }

    @Override
    protected int getDetailLayoutId() {
        return R.layout.part_friend_info;
    }

    @Override
    void refreshData() {
        mPresenter.loadFriendInfo(userId);
    }

    @Override
    protected String getPictureUri() {
        if (mInfoResponse == null) {
            return "";
        }
        return mInfoResponse.getImage();
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mPresenter = new FriendInfoPresenter(this);
        return mPresenter;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (mInfoResponse == null) {
            showToast(R.string.get_user_info_failture);
            return;
        }

        int id = v.getId();
        if (R.id.btn_send_msg == id) {
            mNavigator.navigateToSingleChatActivity(this, mInfoResponse.getUserId());
        } else if (R.id.btn_delete_friend == id) {
            mPresenter.deleteFriend(userId, DELETE_FRIEND);
        } else if (R.id.layout_update_nickname == id) {
            mNavigator.navigateToUpdateFriendRemarkActivity(this, mInfoResponse.getUserId(), mInfoResponse.getName());
        } else if (R.id.layout_report_user == id) {
            mNavigator.navigateToReportFriendActivity(this, userId);
        } else if (R.id.rl_push_top == id) {
            currentBtn = mPushMsgTop;
            mPresenter.updateToggleStatus(userId, getInvertToggleStatus(mPushMsgTop), getToggleStatus(mMsgDisturb), getToggleStatus(mAddToBlack));
        } else if (R.id.rl_msg_disturb == id) {
            currentBtn = mMsgDisturb;
            mPresenter.updateToggleStatus(userId, getToggleStatus(mPushMsgTop), getInvertToggleStatus(mMsgDisturb), getToggleStatus(mAddToBlack));
        } else if (R.id.rl_black_user == id) {
            currentBtn = mAddToBlack;
            mPresenter.updateToggleStatus(userId, getToggleStatus(mPushMsgTop), getToggleStatus(mMsgDisturb), getInvertToggleStatus(mAddToBlack));
        }
    }

    private String getToggleStatus(ToggleButton toggleButton) {
        return toggleButton.isChecked() ? STATUS_CHECKED : STATUS_UNCHECKED;
    }

    private String getInvertToggleStatus(ToggleButton toggleButton) {
        return toggleButton.isChecked() ? STATUS_UNCHECKED : STATUS_CHECKED;
    }

    @Override
    public void setData(FriendInfoResponse response) {
        if (response == null) {
            return;
        }
        mInfoResponse = response;
        showData();
    }

    private void showData() {
        Glide.with(this).load(mInfoResponse.getImage()).transform(new GlideRoundTransform(this, 10)).placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar).into(mHead);
        String nickName = mInfoResponse.getName();
        if (TextUtils.isEmpty(nickName)) {
            mUserRemark.setText(mInfoResponse.getNickName());
        } else {
            mUserRemark.setText(mInfoResponse.getName());
            mUserName.setText(getString(R.string.name, mInfoResponse.getNickName()));
        }
        mUserNum.setText(getString(R.string.thinkcoo, mInfoResponse.getUserId()));
        mSignature.setText(mInfoResponse.getSignature());

        if (getString(R.string.man).equals(mInfoResponse.getSex())) {
            mFriendSex.setImageResource(R.drawable.man);
        } else if (getString(R.string.women).equals(mInfoResponse.getSex())) {
            mFriendSex.setImageResource(R.drawable.women);
        } else {
            mFriendSex.setImageResource(R.drawable.defaule_sex_image);
        }

        mPushMsgTop.setChecked(1 == Integer.valueOf(mInfoResponse.getStick()));
        mAddToBlack.setChecked(1 == Integer.valueOf(mInfoResponse.getBlacklist()));
        mMsgDisturb.setChecked(1 == Integer.valueOf(mInfoResponse.getDisturb()));

    }

    @Override
    public void refreshToggleStatus() {
        if (currentBtn == null) {
            return;
        }
        currentBtn.setChecked(!currentBtn.isChecked());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

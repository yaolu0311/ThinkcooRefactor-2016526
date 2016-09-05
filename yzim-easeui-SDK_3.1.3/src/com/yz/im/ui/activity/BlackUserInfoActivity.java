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
import com.yz.im.mvp.mvpContract.BlackInfoContact;
import com.yz.im.mvp.presenter.BlackUserInfoPresenter;
import com.yz.im.utils.GlideRoundTransform;

public class BlackUserInfoActivity extends ProfileActivity implements BlackInfoContact.BlackInfoView {

    private static String ADD_TO_BLACK = "1";

    public static final int REQUEST_FRIEND_REMARK_NAME = 0x0001;
    public static int REQUEST_CODE = 0X0002;

    public static Intent getBlackInfoActivityIntent(Context context, String userId) {
        if (TextUtils.isEmpty(userId)) {
            throw new NullPointerException("userId is null");
        }
        Intent intent = new Intent(context, BlackUserInfoActivity.class);
        intent.putExtra(KEY_USER_ID, userId);
        return intent;
    }

    private TextView mSignature;
    private RelativeLayout mModifyRemark;
    private RelativeLayout layout_add_black;
    private ToggleButton mToggleButton;
    private Button mDeleteFriend;

    private BlackUserInfoPresenter mPresenter;
    private FriendInfoResponse mInfoResponse;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        super.continueOnCreate(savedInstanceState);
        mPresenter.loadBlackFriendInfo(userId);
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mPresenter = new BlackUserInfoPresenter(this);
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
    protected void initViewAndEvent() {
        super.initViewAndEvent();
        mSignature = (TextView) findViewById(R.id.tv_signature);
        mModifyRemark = (RelativeLayout) findViewById(R.id.layout_update_nickname);
        layout_add_black = (RelativeLayout) findViewById(R.id.rl_black_user);
        mToggleButton = (ToggleButton) findViewById(R.id.toggle_black_user);
        mDeleteFriend = (Button) findViewById(R.id.btn_delete_friend);
        title.setText(R.string.detail_info);
        mToggleButton.setChecked(true);

        mModifyRemark.setOnClickListener(this);
        mDeleteFriend.setOnClickListener(this);
        layout_add_black.setOnClickListener(this);
    }

    @Override
    int getDetailLayoutId() {
        return R.layout.part_black_user_info;
    }

    @Override
    public void refreshData() {
        mPresenter.loadBlackFriendInfo(userId);
    }

    @Override
    protected String getPictureUri() {
        if (mInfoResponse == null) {
            return "";
        }
        return mInfoResponse.getImage();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.image_left) {
            if (isNeedRefresh) {
                closeSelf();
            }
            finish();
        }else if (id == R.id.layout_update_nickname) {
            checkEntityBeforeClick();
            mNavigator.navigateToUpdateBlackFriendRemarkActivity(this, userId, mInfoResponse.getName());
        } else if (id == R.id.rl_black_user) {
            checkEntityBeforeClick();
            if (mToggleButton.isChecked()) {
                mPresenter.reliefBlack(userId);
            }else {
                mPresenter.addFriendToBlack(userId, mInfoResponse.getStick(), mInfoResponse.getDisturb(), ADD_TO_BLACK);
            }
        } else if (id == R.id.btn_delete_friend) {
            checkEntityBeforeClick();
            mPresenter.deleteFriend(userId);
        }

        super.onClick(v);
    }

    private void checkEntityBeforeClick(){
        if (mInfoResponse == null) {
            showToast(R.string.load_user_info_failure);
            return;
        }
    }

    @Override
    public void closeSelf() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setData(FriendInfoResponse response) {
        if (response == null) {
            return;
        }
        mInfoResponse = response;
        showData();
    }

    @Override
    public void changeToggleStatus() {
        isNeedRefresh = !isNeedRefresh;
        mToggleButton.setChecked(!mToggleButton.isChecked());
    }

    private void showData() {
        if (mInfoResponse != null) {
            mSignature.setText(mInfoResponse.getSignature());
            Glide.with(this).load(mInfoResponse.getImage()).transform(new GlideRoundTransform(this, 10))
                    .placeholder(R.drawable.default_avatar).error(R.drawable.default_avatar).into(mHead);

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
        }
    }
}

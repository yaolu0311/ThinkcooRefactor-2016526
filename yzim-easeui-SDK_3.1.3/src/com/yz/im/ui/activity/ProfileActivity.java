package com.yz.im.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.yz.im.ui.base.HXBaseActivity;

public abstract class ProfileActivity extends HXBaseActivity implements View.OnClickListener{

    public static String KEY_USER_ID = "key_user_id";

    protected ImageView back;
    protected TextView title;

    protected ImageView mHead;
    protected ImageView mFriendSex;
    protected TextView mUserRemark;
    protected TextView mUserName;
    protected TextView mUserNum;

    protected ScrollView mScrollView;

    protected String userId;

    protected View mDetailView;

    protected boolean isNeedRefresh = false;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
        getDataFromIntent();
        initViewAndEvent();
    }

    protected void initViewAndEvent() {
        title = (TextView) findViewById(R.id.text_title);
        mUserRemark = (TextView) findViewById(R.id.tv_user_remark);
        mUserName = (TextView) findViewById(R.id.tv_user_name);
        mUserNum = (TextView) findViewById(R.id.tv_thinkcoo_num);
        mScrollView = (ScrollView) findViewById(R.id.layout_scroll_profile);
        mHead = (ImageView) findViewById(R.id.iv_headPortrait);
        mFriendSex = (ImageView) findViewById(R.id.iv_user_sex);
        back = (ImageView) findViewById(R.id.image_left);

        addView();

        back.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.back);
        back.setOnClickListener(this);
        mHead.setOnClickListener(this);
    }

    private void addView() {
        mDetailView = LayoutInflater.from(this).inflate(getDetailLayoutId(), null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mScrollView.addView(mDetailView, params);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.image_left == id) {
            finish();
        }else if (R.id.iv_headPortrait == id) {
            String path = getPictureUri();
            if (path == null) {
                path = "";
            }
            mNavigator.navigateToShowBIgImageActivity(this, path);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            isNeedRefresh = true;
            refreshData();
        }
    }

    abstract void getDataFromIntent();
    abstract int getDetailLayoutId();
    abstract void refreshData();
    protected abstract String getPictureUri();
}
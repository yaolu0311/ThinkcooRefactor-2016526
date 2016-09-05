package com.yz.im.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.yz.im.ui.base.HXBaseActivity;

public abstract class FragmentContainerActivity extends HXBaseActivity implements View.OnClickListener{

    private final static String KEY_CURRENT_TAG = "key_current_tag";

    protected final static String TAG_LEFT = "tag_left";
    protected final static String TAG_RIGHT = "tag_right";

    private ImageView imgBack;
    protected TextView mTitle;
    private LinearLayout mFindUser;
    private LinearLayout mFindGroup;
    private View mUserLine;
    private View mGroupLine;
    protected TextView tvFindUser;
    protected TextView tvFindGroup;
    protected LinearLayout mLinearLayout;
    protected FrameLayout mFrameLayout;

    protected String currentTag;
    protected FragmentTransaction mTransaction;

    protected Fragment leftFragment;
    protected Fragment rightFragment;


    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fragment_contain);
        initViewAndEvent();
        mTransaction = getSupportFragmentManager().beginTransaction();
        if (null != savedInstanceState) {
            getFragmentFromStack();
            currentTag = savedInstanceState.getString(KEY_CURRENT_TAG);
            showFragment(currentTag);
        } else {
            initFragment();
            currentTag = TAG_LEFT;
        }
    }

    protected void initViewAndEvent() {
        imgBack = (ImageView) findViewById(R.id.image_left);
        imgBack.setImageResource(R.drawable.back);
        imgBack.setVisibility(View.VISIBLE);
        mTitle = (TextView) findViewById(R.id.text_title);
        mFindUser = (LinearLayout) findViewById(R.id.tag_find_user);
        mFindGroup = (LinearLayout) findViewById(R.id.tag_find_group);
        tvFindUser = (TextView) findViewById(R.id.ac_find_user);
        tvFindGroup = (TextView) findViewById(R.id.ac_find_group);
        mUserLine = findViewById(R.id.find_person);
        mGroupLine = findViewById(R.id.find_group);
        mLinearLayout = (LinearLayout) findViewById(R.id.fragment_container);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_find);
        setTitleText();


        imgBack.setOnClickListener(this);
        mFindUser.setOnClickListener(this);
        mFindGroup.setOnClickListener(this);
    }

    private void getFragmentFromStack(){
        leftFragment = getSupportFragmentManager().findFragmentByTag(TAG_LEFT);
        rightFragment = getSupportFragmentManager().findFragmentByTag(TAG_RIGHT);
    }

    private void showFragment(String currentTag){
        if (TextUtils.isEmpty(currentTag)) {
            currentTag = TAG_LEFT;
        }
        switch (currentTag) {
            case TAG_LEFT:
                getSupportFragmentManager().beginTransaction().show(leftFragment).hide(rightFragment).commit();
                break;
            case TAG_RIGHT:
                getSupportFragmentManager().beginTransaction().show(rightFragment).hide(leftFragment).commit();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.image_left) {
            finish();
        }
    }

    protected void changeFragmentByTag(){
        if (currentTag.equals(TAG_RIGHT)) {
            changeToCheckedStatus(mGroupLine, tvFindGroup);
            changeToUncheckedStatus(mUserLine, tvFindUser);
        }else {
            changeToCheckedStatus(mUserLine, tvFindUser);
            changeToUncheckedStatus(mGroupLine, tvFindGroup);
        }
        showFragment(currentTag);
    }

    private void changeToUncheckedStatus(View v, TextView tag) {
        tag.setTextColor(getResources().getColor(R.color.font1));
        v.setVisibility(View.INVISIBLE);
    }

    private void changeToCheckedStatus(View v, TextView tag) {
        tag.setTextColor(getResources().getColor(R.color.blue_title));
        v.setVisibility(View.VISIBLE);
    }

    protected void setCurrentTag(String tag){
        currentTag = tag;
    }

    abstract void setTitleText();
    abstract void initFragment();
}

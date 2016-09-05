package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.IMHelper;
import com.yz.im.model.entity.serverresponse.GroupInfoResponse;
import com.yz.im.model.entity.serverresponse.GroupMemberResponse;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.GroupMemberContact;
import com.yz.im.mvp.presenter.GroupMemberListPresenter;
import com.yz.im.ui.base.HXBaseActivity;
import com.yz.im.ui.widget.BaseContactListView;
import com.yz.im.ui.widget.GroupMemberListView;
import com.yz.im.ui.widget.GroupTransferListView;

import java.util.List;

public class GroupMemberListActivity extends HXBaseActivity implements GroupMemberContact.GroupMemberView{

    private static String TAG = "GroupMemberListActivity";

    public static int GROUP_MEMBERS = 0X0001;
    public static int GROUP_TRANSFER = 0X0002;

    public static final String KEY_TYPE =  "key_type";
    public static final String KEY_GROUP_INFO_ENTITY =  "key_group_info_entity";

    private GroupMemberListPresenter mPresenter;

    public static Intent getGroupMemberListActivityIntent(Context context, int type, GroupInfoResponse groupInfoResponse) {
        if (groupInfoResponse == null) {
            ThinkcooLog.e(TAG, "group is null");
        }
        Intent intent = new Intent(context, GroupMemberListActivity.class);
        intent.putExtra(KEY_TYPE, type);
        intent.putExtra(KEY_GROUP_INFO_ENTITY, groupInfoResponse);
        return intent;
    }

    private int type = GROUP_MEMBERS;
    private FrameLayout mFrameLayout;
    private BaseContactListView mContactListView;
    private GroupInfoResponse mGroupInfoResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mPresenter = new GroupMemberListPresenter(this);
        return mPresenter;
    }

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_contact_list);
        getDataFromIntent();
        initView();
        addFragmentView();
        mPresenter.loadGroupMemberList(mGroupInfoResponse.getGroupId());
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra(KEY_TYPE, GROUP_MEMBERS);
            mGroupInfoResponse = intent.getParcelableExtra(KEY_GROUP_INFO_ENTITY);
        }
    }

    private void initView() {
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_contact);
    }

    private void addFragmentView() {
        if (type == GROUP_MEMBERS) {
            mContactListView = new GroupMemberListView(this, mGroupInfoResponse, isOwner());
        }else if(type == GROUP_TRANSFER){
            mContactListView = new GroupTransferListView(this, mGroupInfoResponse);
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mFrameLayout.addView(mContactListView, params);
    }

    private boolean isOwner(){
        return IMHelper.getInstance().getInfoResponse().getUserId().equals(mGroupInfoResponse.getOldUserId());
    }

    @Override
    public void setData(List<GroupMemberResponse> list) {
        if (list == null) {
            return;
        }
        mContactListView.refreshData(list);
    }

    @Override
    public void closeSelf() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==RESULT_OK) {
            mPresenter.loadGroupMemberList(mGroupInfoResponse.getGroupId());
        }
    }
}

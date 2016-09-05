package com.yz.im.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.yz.im.Constant;
import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.ui.adapter.UserListAdapter;
import com.yz.im.ui.base.HXBaseActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFindUserListActivity extends HXBaseActivity implements View.OnClickListener, UserListAdapter.UserCallBack {

    private ImageView imgBack;
    protected TextView tvTitle;
    private RecyclerView mRecyclerView;

    protected List<FindUserResponse> mList;
    protected UserListAdapter mAdapter;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_find_user_list);
        initViewAndEvent();
        initRecyclerView();
    }

    private void initViewAndEvent() {
        imgBack = (ImageView) findViewById(R.id.image_left);
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setImageResource(R.drawable.back);
        tvTitle = (TextView) findViewById(R.id.text_title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_exact_user_result);

        imgBack.setOnClickListener(this);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        setAdapter();
    }

    private void setAdapter() {
        mList = new ArrayList<>();
        mAdapter = new UserListAdapter(this, mList);
        mAdapter.setUserCallBack(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int id =v.getId();
        if(id == R.id.image_left){
            finish();
        }
    }

    @Override
    public void addUser(int position, FindUserResponse response) {
        if (response == null) {
            return;
        }
        mNavigator.navigateToSendInviteUserReasonPage(this, response.getUserId());
    }

    @Override
    public void onItemClick(int position, FindUserResponse response) {
        if (response == null) {
            return;
        }
        mNavigator.navigateToUserInfoPage(this, response.getUserId());
    }

    protected void refreshData(List<FindUserResponse> list){
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

}

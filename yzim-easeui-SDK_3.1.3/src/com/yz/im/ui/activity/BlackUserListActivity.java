package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.BlackListContact;
import com.yz.im.mvp.presenter.BlackListPresenter;
import com.yz.im.ui.adapter.BlackListAdapter;
import com.yz.im.ui.base.HXBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class BlackUserListActivity extends HXBaseActivity implements BlackListContact.BlackListView, View.OnClickListener, BlackListAdapter.OnItemCallBack {

    private static final String BLACK_TYPE = "1";

    public static Intent getBlackUserListIntent(Context context) {
        Intent intent = new Intent(context, BlackUserListActivity.class);
        return intent;
    }

    private ImageView imgBack;
    private TextView tvTitle;
    private RecyclerView mRecyclerView;

    private List<Friend> mList;
    private BlackListAdapter mAdapter;

    private BlackListPresenter mPresenter;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_black_user_list);
        initViewAndEvent();
        initRecyclerView();
        mPresenter.loadBlackUserList(BLACK_TYPE);
    }

    private void initViewAndEvent() {
        imgBack = (ImageView) findViewById(R.id.image_left);
        tvTitle  = (TextView) findViewById(R.id.text_title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_black_user_list);

        imgBack.setImageResource(R.drawable.back);
        imgBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.black_user);

        imgBack.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new BlackListAdapter(this, mList);
        mAdapter.setCallBack(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mPresenter = new BlackListPresenter(this);
        return mPresenter;
    }

    @Override
    public void setData(List<Friend> list) {
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.image_left) {
            finish();
        }
    }

    @Override
    public void onItemClick(int position, Friend friend) {
        if (friend == null) {
            return;
        }
        mNavigator.navigateToBlackInfoPageFromBlackList(this, friend.getUserId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==RESULT_OK) {
            mPresenter.loadBlackUserList(BLACK_TYPE);
        }
    }
}

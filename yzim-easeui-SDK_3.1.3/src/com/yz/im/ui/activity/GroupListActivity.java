package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.yz.im.model.db.entity.Group;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.GroupListContract;
import com.yz.im.mvp.presenter.GroupListPresenter;
import com.yz.im.ui.adapter.GroupAdapter;
import com.yz.im.ui.base.HXBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class GroupListActivity extends HXBaseActivity implements GroupListContract.GroupListView, View.OnClickListener,
        GroupAdapter.OnItemClickListener, TextWatcher {

    public static Intent getGroupActivityIntent(Context context) {
        Intent intent = new Intent(context, GroupListActivity.class);
        return intent;
    }

    private List<Group> mList;

    private GroupListPresenter mListPresenter;

    private ImageView mLeftImage;
    private TextView mTitle;
    private RecyclerView mRecyclerView;
    protected ImageButton mImageClear;
    protected EditText mEditTextFilter;
    private GroupAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mListPresenter = new GroupListPresenter(this);
        return mListPresenter;
    }

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_group_list);
        getDataFromIntent();
        initViewAndEvent();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListPresenter.loadGroupList();
    }

    protected void getDataFromIntent() {

    }

    private void initViewAndEvent() {
        mLeftImage = (ImageView) findViewById(R.id.image_left);
        mLeftImage.setVisibility(View.VISIBLE);
        mLeftImage.setImageResource(R.drawable.back);
        mTitle = (TextView) findViewById(R.id.text_title);
        mEditTextFilter = (EditText) findViewById(R.id.query);
        mImageClear = (ImageButton) findViewById(R.id.search_clear);

        mTitle.setText(R.string.Group);
        mLeftImage.setOnClickListener(this);
        mImageClear.setOnClickListener(this);
        mEditTextFilter.addTextChangedListener(this);
    }

    private void initData() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_groups);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mList = new ArrayList<>();
        mAdapter = new GroupAdapter(this, mList);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.image_left == id) {
            finish();
        } else if (R.id.search_clear == id) {
            mEditTextFilter.getText().clear();
            hideSoftKeyboard();
        }
    }

    @Override
    public void setData(List<Group> data) {
        mAdapter.refreshData(data);
    }

    @Override
    public void onClick(Group group, int position) {
        mNavigator.navigateToGroupInfoPage(this, group.getEasemobGroupId(), true);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mAdapter.filter(s);
        if (s.length() > 0) {
            mImageClear.setVisibility(View.VISIBLE);
        } else {
            mImageClear.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

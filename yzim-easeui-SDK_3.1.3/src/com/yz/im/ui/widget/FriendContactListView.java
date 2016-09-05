package com.yz.im.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.hyphenate.easeui.R;
import com.yz.im.model.cache.FriendCache;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.model.strategy.FriendListStrategy;
import com.yz.im.ui.adapter.BaseContactListAdapter;
import com.yz.im.ui.adapter.FriendListAdapter;

import java.util.List;

/**
 * Created by cys on 2016/7/7
 * 通讯录列表(好友)
 */
public class FriendContactListView extends BaseContactListView implements View
        .OnClickListener {


    private FrameLayout headView;

    private FriendCache mFriendCache;
    protected FriendListStrategy mStrategy;

    public FriendContactListView(Context context) {
        this(context, null);
    }

    public FriendContactListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeadView();
    }

    @Override
    protected void setTitleLayout() {
        title.setText(context.getString(R.string.contact_list));
        leftImage.setVisibility(View.VISIBLE);
        leftImage.setImageResource(R.drawable.back);
    }

    @Override
    public void setAdapter() {
        adapter = new FriendListAdapter(getContext(), contactList, BaseContactListAdapter
                .NONE_CHECK_BOX, false);
    }

    @Override
    public void getData() {
        mFriendCache = FriendCache.getInstance(context);
        List<Friend> friendList = mFriendCache.getFriendList();
        refreshData(friendList);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.layout_group) {
            mNavigator.navigateToGroupListActivity(context);
        }
    }

    public void initHeadView() {
        addHeadView();
        addHeadViewListener();
    }

    private void addHeadView() {
        headView = (FrameLayout) mInflater.inflate(R.layout.contact_head_view, null);
        listView.addHeaderView(headView);
    }

    private void addHeadViewListener() {
        headView.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            return;
        }
        Friend friend = (Friend) contactList.get(position - 1);
        mNavigator.navigateToUserInfoPage(context, friend.getUserId());
    }

    public FriendListStrategy getStrategy() {
        return mStrategy;
    }

    public FriendContactListView setStrategy(FriendListStrategy strategy) {
        mStrategy = strategy;
        return this;
    }
}

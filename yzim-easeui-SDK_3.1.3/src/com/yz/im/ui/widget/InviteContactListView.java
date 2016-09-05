package com.yz.im.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.yz.im.model.cache.FriendCache;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.model.entity.serverresponse.GroupMemberResponse;
import com.yz.im.ui.adapter.BaseContactListAdapter;
import com.yz.im.ui.adapter.InviteListAdapter;
import com.yz.im.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cys on 2016/7/29
 * 邀请好友加入群组  列表页面
 */
public class InviteContactListView extends BaseContactListView<Friend> {

    private TextView tvSave;

    private FriendCache mFriendCache;
    private List<GroupMemberResponse> mGroupMemberResponses;
    private List<Friend> bridge;
    private String groupId;

    public InviteContactListView(Context context, List<GroupMemberResponse> list, String groupId) {
        super(context, null);
        this.groupId = groupId;
        mGroupMemberResponses = new ArrayList<>();
        mGroupMemberResponses.addAll(list);
        getFriendsData();
    }

    @Override
    protected void setTitleLayout() {
        title.setText(context.getString(R.string.contact_list));
        leftImage.setVisibility(View.VISIBLE);
        leftImage.setImageResource(R.drawable.back);

        tvSave = (TextView) findViewById(R.id.text_right);
        tvSave.setText(R.string.save);
        tvSave.setVisibility(VISIBLE);
        tvSave.setOnClickListener(this);
    }

    @Override
    protected void setAdapter() {
        adapter = new InviteListAdapter(getContext(), contactList, BaseContactListAdapter
                .LEFT_CHECK_BOX, false);
    }

    @Override
    protected void getData() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        int id = v.getId();
        if (id == R.id.text_right) {
            List<Friend> friends = adapter.getCheckList();
            if (friends == null || friends.size() <= 0) {
                ToastUtil.getInstance(context).showToastById(R.string.choose_least_one_friend);
                return;
            }
            String userIds = getInvitedUserIds(friends);
            mNavigator.navigateToSendInviteFriendToGroupReasonPage(context, groupId, userIds);
        }
    }

    private String getInvitedUserIds(List<Friend> friends) {
        StringBuilder builder = new StringBuilder();
        int count = friends.size();
        for (int i = 0; i < count; i++) {
            Friend friend = friends.get(i);
            builder.append(friend.getUserId());
            if(i != count-1){
                builder.append(",");
            }
        }
        return builder.toString().trim();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //// TODO: 2016/7/29 很扯淡，but ...
        Friend friend = contactList.get(position);
        if (!friend.isCanToggle()) {
            return;
        }
        friend.setCheck(!friend.isCheck());
        contactList.remove(position);
        contactList.add(position, friend);
        adapter.notifyDataSetChanged();
    }

    private void getFriendsData() {
        mFriendCache = FriendCache.getInstance(context);
        List<Friend> friendList = mFriendCache.getEntityList();
        if (bridge == null) {
            bridge = new ArrayList<>();
        }
        bridge.clear();
        bridge.addAll(friendList);
        setInitFriendStatus(bridge);
        refreshData(bridge);
    }

    private void setInitFriendStatus(List<Friend> friendList) {
        if (mGroupMemberResponses == null) {
            return;
        }
        for (int i = 0; i < friendList.size(); i++) {
            Friend friend = friendList.get(i);
            String uid = friend.getUserId();
            for (int j = 0; j < mGroupMemberResponses.size(); j++) {
                GroupMemberResponse memberResponse = mGroupMemberResponses.get(j);
                if (uid.equals(memberResponse.getUserId())) {
                    friend.setCanToggle(false);
                    friendList.remove(i);
                    friendList.add(i, friend);
                    break;
                }
            }
        }
    }

}

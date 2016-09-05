package com.yz.im.ui.fragment;

import android.text.TextUtils;

import com.yz.im.model.cache.FriendCache;
import com.yz.im.model.cache.GroupCache;
import com.yz.im.model.db.entity.Group;
import com.yz.im.model.entity.serverresponse.NoticeMessageResponse;
import com.yz.im.ui.activity.NoticeMessageActivity;
import com.yz.im.ui.adapter.NoticeMessageAdapter;

/**
 * Created by cys on 2016/8/5
 */
public class NoticeVerifyFragment extends BaseNoticeMessageFragment implements NoticeMessageAdapter.NoticeActionListener {

    private static final String MSG_UN_VERIFY = "-1";
    private static final String MSG_VERIFY = "0";

    private FriendCache mFriendCache;
    private GroupCache mGroupCache;

    @Override
    protected String setMessageType() {
        return NoticeMessageActivity.TYPE_VERIFY_MSG;
    }

    @Override
    protected void lazyLoad() {
        ((NoticeMessageActivity) getContext()).refreshVerifyMessageData();
    }

    @Override
    protected String getDeleteMsgType() {
        return NoticeMessageActivity.ACTION_DELETE_VERIFY_MSG;
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter.setListener(this);
        mFriendCache = FriendCache.getInstance(getContext());
        mGroupCache = GroupCache.getInstance(getContext());
    }

    @Override
    public void agreeMessage(int position, NoticeMessageResponse response) {
        ((NoticeMessageActivity) getContext()).getPresenter().doWithMessage(response, NoticeMessageActivity.ACTION_AGREE_VERIFY);
    }

    @Override
    public void onItemClick(int position, NoticeMessageResponse response) {
        String msgType = response.getMessageType();
        if (NoticeMessageActivity.MSG_FROM_FRIEND.equals(msgType)) {
            showUserInfo(response);
        } else if (NoticeMessageActivity.MSG_FROM_GROUP.equals(msgType)) {
            showGroupInfo(response);
        }
    }

    private void showUserInfo(NoticeMessageResponse response) {
        if (response == null) {
            return;
        }

        String userId = response.getUserId();
        if (TextUtils.isEmpty(userId)) {
            return;
        }

        ((NoticeMessageActivity) getContext()).mNavigator.navigateToUserInfoPage(getContext(), userId);
    }

    private void showGroupInfo(NoticeMessageResponse response) {
        if (response == null) {
            return;
        }

        String msgStatus = response.getMessageState();
        String userId = response.getUserId();
        String circleId = response.getCircleId();

        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(msgStatus) || TextUtils.isEmpty(circleId)) {
            return;
        }

        Group group = mGroupCache.getEntity(circleId);
        if (group == null) {
            group = new Group();
            group.setGroupId(circleId);
            group.setOldUserId(userId);
            group.setGroupType("1");    //// TODO: 2016/8/6
        }
        ((NoticeMessageActivity) getContext()).getNavigate().navigateToGroupInfoPage(getContext(), group.getEasemobGroupId(), true);
    }
}

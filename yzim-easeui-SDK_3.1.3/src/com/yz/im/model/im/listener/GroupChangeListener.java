package com.yz.im.model.im.listener;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.EMGroupChangeListener;
import com.yz.im.Constant;
import com.yz.im.IMHelper;
import com.yz.im.domain.IMBaseCase;
import com.yz.im.domain.RefreshGroupListUseCase;
import com.yz.im.model.cache.GroupCache;
import com.yz.im.model.db.entity.Group;
import com.yz.im.model.entity.serverresponse.GroupInfoResponse;
import com.yz.im.utils.PreferenceManager;

import java.util.List;

import rx.Subscriber;

/**
 * Created by cys on 2016/7/1
 * in com.yz.im.model.im.listener
 */
public class GroupChangeListener implements EMGroupChangeListener {

    private static final String TAG = "GroupChangeListener";

    private Context mContext;
    private IMHelper mIMHelper;
    private GroupCache mGroupCache;
    private RefreshGroupListUseCase mGroupListUseCase;

    public GroupChangeListener(Context context) {
        mContext = context;
        mIMHelper = IMHelper.getInstance().getInstance();
    }

    @Override
    public void onInvitationReceived(String groupId, String groupName, String inviter, String
            reason) {
        PreferenceManager.getInstance().setNewNotification(true);
        mIMHelper.broadcastManager.sendBroadcast(new Intent(Constant.ACTION_NEW_NOTICE_RECEIVE));
        mIMHelper.getNotifier().variableAndPlayTone(null);  //// TODO: 2016/8/10 声音提醒
    }

    @Override
    public void onInvitationAccpted(String groupId, String invitee, String reason) {
    }

    @Override
    public void onInvitationDeclined(String groupId, String invitee, String reason) {
    }

    @Override
    public void onUserRemoved(String groupId, String groupName) {
        removeGroupFromDb(groupId);
    }

    private void removeGroupFromDb(String groupId) {
        try {
            mGroupCache = GroupCache.getInstance(mContext);
            Group group = mIMHelper.getGroupInfo(groupId, true);
            if (group == null) {
                return;
            }
            mGroupCache.delete(group);
        } catch (Exception e) {
            ThinkcooLog.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onGroupDestroy(String groupId, String groupName) {
        mIMHelper.broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANGE));
        removeGroupFromDb(groupId);
    }

    @Override
    public void onApplicationReceived(String groupId, String groupName, String applyer, String
            reason) {
        PreferenceManager.getInstance().setNewNotification(true);
    }

    @Override
    public void onApplicationAccept(String groupId, String groupName, String accepter) {
        PreferenceManager.getInstance().setNewNotification(true);
        mIMHelper.broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANGE));
        refreshGroupList(groupId);
    }

    private void refreshGroupList(String groupId) {
        if (mGroupListUseCase == null) {
            mGroupListUseCase = new RefreshGroupListUseCase(mContext, IMBaseCase.EXECUTOR_THREAD, IMBaseCase.EXECUTOR_THREAD);
        }
        Group group = mIMHelper.getGroupInfo(groupId, true);
        if (group != null) {
            return;
        }
        mGroupListUseCase.execute(getGroupListSub(), mIMHelper.getInfoResponse().getUserId());
    }

    private Subscriber getGroupListSub() {
        return new Subscriber<List<GroupInfoResponse>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(List<GroupInfoResponse> o) {
                mIMHelper.broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANGE));
            }
        };
    }

    @Override
    public void onApplicationDeclined(String groupId, String groupName, String decliner, String
            reason) {
    }

    @Override
    public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String
            inviteMessage) {
        refreshGroupList(groupId);
        mIMHelper.broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANGE));
    }

}

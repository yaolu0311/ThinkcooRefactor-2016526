package com.yz.im.model.im.listener;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.publicmodule.util.IdOffsetUtil;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.EMContactListener;
import com.yz.im.Constant;
import com.yz.im.IMHelper;
import com.yz.im.domain.IMBaseCase;
import com.yz.im.domain.RefreshFriendListUseCase;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.services.BackgroundInitService;
import com.yz.im.utils.PreferenceManager;

import java.util.List;

import rx.Subscriber;

/**
 * Created by cys on 2016/7/1
 * in com.yz.im.model.im.listener
 */
public class ContactListener implements EMContactListener {

    private static final String TAG = "ContactListener";

    private Context mContext;
    private IMHelper mIMHelper;
    private RefreshFriendListUseCase mListUseCase;

    public ContactListener(Context context) {
        mContext = context;
        mIMHelper = IMHelper.getInstance().getInstance();
    }

    @Override
    public void onContactAdded(String username) {
        refreshFriendList(username);
    }

    private Subscriber getFriendListSub() {
        return new Subscriber<List<Friend>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(List<Friend> o) {
                mIMHelper.broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANGE));
            }
        };
    }

    @Override
    public void onContactDeleted(String username) {
    }

    @Override
    public void onContactInvited(String username, String reason) {
        //// TODO: 2016/8/19 nothing
    }

    @Override
    public void onContactAgreed(String username) {
        PreferenceManager.getInstance().setNewNotification(true);
        mIMHelper.broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANGE));
    }

    private void refreshFriendList(String username) {
        if (mListUseCase == null) {
            mListUseCase = new RefreshFriendListUseCase(mContext, IMBaseCase.EXECUTOR_THREAD, IMBaseCase.EXECUTOR_THREAD);
        }
        String userId = IdOffsetUtil.minusOffset(username);
        Friend friend = mIMHelper.getFriendInfo(userId);
        if (friend == null) {
            mListUseCase.execute(getFriendListSub(), IMHelper.getInstance().getInfoResponse().getUserId(), BackgroundInitService.FRIEND_ALL);
        }
    }

    @Override
    public void onContactRefused(String username) {
    }

}

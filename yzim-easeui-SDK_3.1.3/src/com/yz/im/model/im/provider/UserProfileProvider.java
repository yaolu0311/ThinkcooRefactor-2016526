package com.yz.im.model.im.provider;

import android.content.Context;

import com.hyphenate.easeui.controller.EaseUI;
import com.yz.im.IMHelper;
import com.yz.im.model.db.entity.Friend;

/**
 * Created by cys on 2016/7/2
 */
public class UserProfileProvider implements EaseUI.EaseUserProfileProvider {

    private Context mContext;
    private IMHelper mHelper;

    public UserProfileProvider(Context context) {
        mContext = context;
        mHelper = IMHelper.getInstance();
    }

    @Override
    public Friend getUser(String username) {
        return mHelper.getFriendInfo(username);
    }

}

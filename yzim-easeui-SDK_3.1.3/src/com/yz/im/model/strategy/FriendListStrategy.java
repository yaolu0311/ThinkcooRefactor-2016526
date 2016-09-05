package com.yz.im.model.strategy;

import android.content.Context;

import com.yz.im.ui.widget.FriendContactListView;

/**
 * Created by cys on 2016/8/13
 */
public interface FriendListStrategy {

    FriendContactListView getView(Context context);

}

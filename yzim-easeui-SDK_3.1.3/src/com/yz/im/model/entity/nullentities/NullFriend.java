package com.yz.im.model.entity.nullentities;

import com.yz.im.model.db.entity.Friend;

/**
 * Created by cys on 2016/7/19
 */
public class NullFriend extends Friend {
    @Override
    public boolean isEmpty() {
        return true;
    }
}

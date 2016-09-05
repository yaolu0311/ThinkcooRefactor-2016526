package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.UserStatus;

/**
 * Created by Robert.yao on 2016/5/30.
 */
public class NullUserStatus extends UserStatus{

    @Override
    public boolean isEmpty() {
        return true;
    }
}

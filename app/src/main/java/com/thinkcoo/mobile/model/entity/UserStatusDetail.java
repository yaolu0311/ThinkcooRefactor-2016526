package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.model.entity.nullentities.NullUserStatusDetail;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;

/**
 * Created by Robert.yao on 2016/5/30.
 */
public class UserStatusDetail implements Nullable {

    public static  final UserStatusDetail NULL_USER_STATUS_DETAIL = new NullUserStatusDetail();

    @Override
    public boolean isEmpty() {
        return false;
    }

    public String id;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}

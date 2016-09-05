package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.UserStatusDetail;

/**
 * Created by Robert.yao on 2016/5/30.
 */
public class NullUserStatusDetail extends UserStatusDetail {

    @Override
    public boolean isEmpty() {
        return true;
    }
}

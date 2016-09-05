package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.UserBasicInfo;

/**
 * Created by Robert.yao on 2016/3/21.
 */
public class NullUserBasicInfo extends UserBasicInfo {

    @Override
    public boolean isEmpty() {
        return true;
    }
}

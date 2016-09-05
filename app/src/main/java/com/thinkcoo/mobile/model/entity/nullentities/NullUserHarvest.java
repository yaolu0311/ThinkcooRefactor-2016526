package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.UserHarvest;

/**
 * Created by Robert.yao on 2016/5/30.
 */
public class NullUserHarvest extends UserHarvest {

    @Override
    public boolean isEmpty() {
        return true;
    }
}

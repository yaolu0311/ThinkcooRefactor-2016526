package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.LocationBean;

/**
 * Created by Robert.yao on 2016/3/30.
 */
public class NullLocationBean extends LocationBean{

    @Override
    public boolean isEmpty() {
        return true;
    }
}

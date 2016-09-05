package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.Province;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  9:53
 */
public class NullProvince extends Province {

    @Override
    public boolean isEmpty() {
        return true;
    }
}

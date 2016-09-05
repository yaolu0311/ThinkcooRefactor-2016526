package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.Address;

/**
 * Created by Leevin
 * CreateTime: 2016/6/17  15:58
 */
public class NullAddress extends Address {
    @Override
    public boolean isEmpty() {
        return true;
    }
}

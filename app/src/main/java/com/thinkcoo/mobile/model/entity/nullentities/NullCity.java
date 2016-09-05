package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.City;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  19:03
 */
public class NullCity extends City{
    @Override
    public boolean isEmpty() {
        return true;
    }
}

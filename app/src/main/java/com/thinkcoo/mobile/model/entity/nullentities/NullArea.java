package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.Area;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  18:59
 */
public class NullArea extends Area{

    @Override
    public boolean isEmpty() {
        return true;
    }
}

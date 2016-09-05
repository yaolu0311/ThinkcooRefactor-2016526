package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.License;

/**
 * Created by Administrator on 2016/5/19.
 */
public class NullLicense extends License{
    @Override
    public boolean isEmpty() {
        return true;
    }
}

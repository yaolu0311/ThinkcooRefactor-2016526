package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.DataDictionary;

/**
 * Created by Robert.yao on 2016/6/15.
 */
public class NullDataDictionary extends DataDictionary {
    @Override
    public boolean isEmpty() {
        return true;
    }
}

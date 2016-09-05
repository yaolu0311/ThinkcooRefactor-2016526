package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.model.entity.nullentities.Nullable;

/**
 * author ：ml on 2016/7/25
 */
public class CooperationCollection implements Nullable {
    public static final CooperationCollection NULL_COOPERATIONCOLLECTION= new CooperationCollection();

    @Override
    public boolean isEmpty() {
        return false;
    }

}

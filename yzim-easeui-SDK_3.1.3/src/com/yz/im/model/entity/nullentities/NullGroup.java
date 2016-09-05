package com.yz.im.model.entity.nullentities;

import com.yz.im.model.db.entity.Group;

/**
 * Created by cys on 2016/7/19
 */
public class NullGroup extends Group{

    @Override
    public boolean isEmpty() {
        return true;
    }
}

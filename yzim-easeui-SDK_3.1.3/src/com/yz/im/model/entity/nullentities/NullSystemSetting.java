package com.yz.im.model.entity.nullentities;

import com.yz.im.model.db.entity.SystemSetting;

/**
 * Created by cys on 2016/7/19
 */
public class NullSystemSetting extends SystemSetting {

    @Override
    public boolean isEmpty() {
        return true;
    }
}

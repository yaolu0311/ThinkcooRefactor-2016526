package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.UserSkill;

/**
 * Created by Robert.yao on 2016/5/30.
 */
public class NullUserSkill extends UserSkill {
    @Override
    public boolean isEmpty() {
        return true;
    }
}

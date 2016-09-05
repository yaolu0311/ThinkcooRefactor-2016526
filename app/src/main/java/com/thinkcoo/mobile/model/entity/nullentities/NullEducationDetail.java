package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.EducationDetail;

/**
 * Created by Robert.yao on 2016/5/30.
 */
public class NullEducationDetail extends EducationDetail {

    @Override
    public boolean isEmpty() {
        return true;
    }
}

package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.TrainDetail;

/**
 * Created by Robert.yao on 2016/5/30.
 */
public class NullTrainDetail extends TrainDetail{

    @Override
    public boolean isEmpty() {
        return true;
    }
}

package com.thinkcoo.mobile.presentation.mvp.views;

import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.model.entity.Location;

/**
 * Created by Robert.yao on 2016/7/21.
 */
public interface GoodsRecommendView extends BaseLceView<Goods>{
    Location getLocation();
}

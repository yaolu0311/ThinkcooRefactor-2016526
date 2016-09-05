package com.thinkcoo.mobile.presentation.mvp.views;

import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.model.entity.GoodsFilter;
import com.thinkcoo.mobile.model.entity.Location;

/**
 * Created by Robert.yao on 2016/8/3.
 */
public interface GoodsSearchResultView extends BaseLceView<Goods>{

    Location getLocation();
    GoodsFilter getGoodsFilter();
    int getGoodsType();
    String getSearchKey();

}

package com.thinkcoo.mobile.presentation.mvp.views;

import com.thinkcoo.mobile.model.entity.MyGoods;

/**
 * Created by Robert.yao on 2016/8/9.
 */
public interface MyGoodsView<D> extends BaseLceView<D> , BaseHintView {

    String getMyGoodsType();
    void refreshItem(MyGoods myGoods);
    void deleteItem(MyGoods myGoods);

}

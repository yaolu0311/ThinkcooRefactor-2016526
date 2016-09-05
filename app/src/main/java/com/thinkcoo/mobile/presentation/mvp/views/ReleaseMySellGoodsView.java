package com.thinkcoo.mobile.presentation.mvp.views;



/**
 * Created by Robert.yao on 2016/8/11.
 */
public interface ReleaseMySellGoodsView<MyGoods,MyGoodsDetail> extends BaseDetailView<MyGoods,MyGoodsDetail>{
    void deletePhotoByPosition(int position);
}

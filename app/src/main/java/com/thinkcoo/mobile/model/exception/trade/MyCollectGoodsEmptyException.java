package com.thinkcoo.mobile.model.exception.trade;

/**
 * Created by Robert.yao on 2016/8/17.
 */
public class MyCollectGoodsEmptyException extends Throwable {
    private int mGoodsType;
    public MyCollectGoodsEmptyException(int goodsType) {
        mGoodsType = goodsType;
    }
}

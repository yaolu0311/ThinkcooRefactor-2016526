package com.thinkcoo.mobile.model.exception.trade;

/**
 * Created by Robert.yao on 2016/8/9.
 */
public class MyGoodsEmptyException extends Throwable {
    String type;
    public MyGoodsEmptyException(String myGoodsType) {
        type = myGoodsType;
    }
}

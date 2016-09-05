package com.thinkcoo.mobile.model.exception.trade;

import android.text.TextUtils;

/**
 * Created by Robert.yao on 2016/8/4.
 */
public class GoodsSearchResultEmptyException extends Throwable {

    public GoodsSearchResultEmptyException(String keyWord) {
        super(createMsg(keyWord));
    }
    private static String createMsg(String keyWord) {
        if (TextUtils.isEmpty(keyWord)){
            return "没有搜索到相关商品";
        }
        return ("没有搜索到与\"" + keyWord + "\"相关的商品");
    }
}

package com.thinkcoo.mobile.presentation.views.component;

/**
 * Created by Robert.yao on 2016/6/15.
 * 对于一个数据字典单行滚轮来说，区别主要在于左中右title文字以及查询数据的表不一样而已
 */
public interface DataDictionaryDialogProvide {

    int getTitleTextResId();
    String getDataDictionaryTableName();
}

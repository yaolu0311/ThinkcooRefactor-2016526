package com.thinkcoo.mobile.presentation.mvp.views;


import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

/**
 * Created by Robert.yao on 2016/8/17.
 */
public interface GoodsDetailView<T> extends MvpLceView<T> ,BaseHintView,BaseActivityHelpView {
    String getGoodsId();
}

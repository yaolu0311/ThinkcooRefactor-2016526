package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

/**
 * Created by Robert.yao on 2016/7/22.
 */
public interface BaseLceView<D> extends MvpLceView{
    void setDataList(List<D> dataList);
}

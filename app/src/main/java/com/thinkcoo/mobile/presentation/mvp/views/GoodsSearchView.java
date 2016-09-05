package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by Robert.yao on 2016/8/2.
 */
public interface GoodsSearchView extends MvpView{
    void setSearchHistoryList(List<String> historyList);
}

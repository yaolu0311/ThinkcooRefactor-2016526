package com.thinkcoo.mobile.presentation.mvp.views;

import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 */
public interface BaseSearchView extends MvpView, BaseHintView , BaseActivityHelpView{
    void handleData(String content);
    void setData(List<Object> objects);
    View inflateSearchView();
    View inflateListView();
    String getSearchContent();
}

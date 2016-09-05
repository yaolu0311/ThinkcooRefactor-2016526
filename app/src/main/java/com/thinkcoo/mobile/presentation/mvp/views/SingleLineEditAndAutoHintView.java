package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.DataDictionary;

import java.util.List;

/**
 * Created by Administrator on 2016/6/15.
 */
public interface SingleLineEditAndAutoHintView extends MvpView{

    void setSearchResult(List<DataDictionary> searchResult);
}

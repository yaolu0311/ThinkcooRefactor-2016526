package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.DataDictionary;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16.
 */
public interface IndustryDirectionView extends BaseHintView, BaseActivityHelpView, MvpView {
    void setListData(List<DataDictionary> listData);  //// TODO: 2016/6/16 javabean
}

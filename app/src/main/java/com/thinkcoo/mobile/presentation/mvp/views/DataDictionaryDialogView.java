package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.DataDictionary;

import java.util.List;

/**
 * Created by Robert.yao on 2016/6/15.
 */
public interface DataDictionaryDialogView extends MvpView{
    void showDialogByDataDictionaryList(List<DataDictionary> dataDictionaries);
    void selectDefaultDataDictionaryIfNeed();
}

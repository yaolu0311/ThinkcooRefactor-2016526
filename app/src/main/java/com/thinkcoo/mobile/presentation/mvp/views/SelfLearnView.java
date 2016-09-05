package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/6/28.
 */
public interface SelfLearnView extends MvpView,BaseHintView,BaseActivityHelpView {
    void setData();
}

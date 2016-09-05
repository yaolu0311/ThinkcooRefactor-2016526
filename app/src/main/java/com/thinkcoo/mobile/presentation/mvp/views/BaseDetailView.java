package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Robert.yao on 2016/6/1.
 */
public interface BaseDetailView<H extends Object , D extends Object> extends MvpView, BaseHintView,BaseActivityHelpView {

    void setDetail(D detail);
    H getHostFromUi();
    void setResultOkAndCloseSelf();
    void setResultCancelAndCloseSelf();

}

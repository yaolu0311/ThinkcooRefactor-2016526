package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/6/30.
 */
public interface RollCallView extends MvpView, BaseHintView, BaseActivityHelpView {
    void gotoRollCallResultPage(String uuid);
    void reOpenShake();
}

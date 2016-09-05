package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by admin on 2016/5/26.
 */
public interface RequestRegisterView extends MvpView , BaseHintView ,  BaseActivityHelpView{

    String getAccountName();
    String getVcodeContent();
    void startVcodeCountDown();
    void stopVcodeCountDown();
    void gotoCompleteRegisterPage(String accountName);
}

package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Leevin
 * CreateTime: 2016/8/8  9:58
 */
public interface ChangePhoneNumberView extends MvpView ,BaseHintView ,  BaseActivityHelpView {

    String getNewPhoneNumber();
    String getVcodeContent();
    void startVcodeCountDown();
    void stopVcodeCountDown();
    void setOldPhoneNumber(String oldPhoneNumber);
    String getLoginPassword();
}

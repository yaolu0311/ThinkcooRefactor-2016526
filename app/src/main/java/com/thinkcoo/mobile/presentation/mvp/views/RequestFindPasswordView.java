package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.CheckVcode;

/**
 * Created by Administrator on 2016/5/27.
 */
public interface RequestFindPasswordView extends MvpView, BaseHintView ,  BaseActivityHelpView{
    String getAccountName();
    String getVcodeContent();
    void startVcodeCountDown();
    void stopVcodeCountDown();
    void gotoCompleteFindPasswordPage(CheckVcode checkVcode);
}

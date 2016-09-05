package com.thinkcoo.mobile.presentation.mvp.views;

import com.thinkcoo.mobile.model.entity.Account;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface CompleteRegisterView extends BaseHintView, BaseActivityHelpView {

    String getPassWord();
    String getUserName();
    void gotoHomePage();
    void gotoLoginPage(Account account);

}

package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Robert.yao on 2016/8/17.
 */
public interface UserSettingView extends MvpView , BaseHintView ,BaseActivityHelpView{
   void  gotoLogin(String accountName);
}

package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/8/15.
 */
public interface FeedBackView extends MvpView, BaseHintView , BaseActivityHelpView{
    String getSuggestion();
    String getContact();
}

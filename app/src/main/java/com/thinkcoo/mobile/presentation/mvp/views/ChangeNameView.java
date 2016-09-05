package com.thinkcoo.mobile.presentation.mvp.views;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Robert.yao on 2016/3/22.
 */
public interface ChangeNameView extends MvpView , BaseHintView , BaseActivityHelpView{

    String getName();
    void resultToUserMainInfoActivity();
}

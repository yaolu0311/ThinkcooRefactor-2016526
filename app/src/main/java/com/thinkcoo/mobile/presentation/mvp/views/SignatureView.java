package com.thinkcoo.mobile.presentation.mvp.views;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Robert.yao on 2016/3/22.
 */
public interface SignatureView extends MvpView , BaseHintView , BaseActivityHelpView{

    String getSignature();
    void resultToUserMainInfoActivity();
}

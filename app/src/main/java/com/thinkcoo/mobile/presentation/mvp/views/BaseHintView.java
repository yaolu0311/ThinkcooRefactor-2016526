package com.thinkcoo.mobile.presentation.mvp.views;

/**
 * Created by Robert.yao on 2016/3/28.
 */
public interface BaseHintView {

    void showProgressDialog(int stringResId);
    void hideProgressDialogIfShowing();
    void showToast(String errorMsg);
}

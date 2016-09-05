package com.thinkcoo.mobile.presentation.views.activitys.base;

/**
 * Created by Robert.yao on 2016/8/2.
 */
public interface FragmentHintHelper {

    void showLongToast(String msg);
    void showShortToast(int msgId);

    void showProgressDialog(int msgId);
    void hideProgressDialogIfShowing();

}

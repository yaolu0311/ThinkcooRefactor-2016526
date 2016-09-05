package com.yz.im.mvp;

/**
 * Created by cys on 2016/7/11
 * in com.yz.im.mvp
 */
public interface IMMvpView {

    void showProgressDialog(int resId);
    void showProgressDialog(String content);
    void hideProgressDialog();
    void showToast(int resId);
    void showToast(String content);

    void closeSelf();
}

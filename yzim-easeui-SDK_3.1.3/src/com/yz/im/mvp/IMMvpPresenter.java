package com.yz.im.mvp;

/**
 * Created by cys on 2016/7/11
 * in com.yz.im.mvp
 */
public interface IMMvpPresenter<V extends IMMvpView> {

    void attachView(V mView);

    void detachView();
}

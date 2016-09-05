package com.yz.im.mvp;

import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * Created by cys on 2016/7/11
 */
public class IMBasePresenter<V extends IMMvpView> implements IMMvpPresenter<V> {

    private WeakReference<V> viewRef;

    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
    }

    @Nullable
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @Override
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }
}

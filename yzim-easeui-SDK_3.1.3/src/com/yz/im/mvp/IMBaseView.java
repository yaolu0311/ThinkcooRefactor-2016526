package com.yz.im.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.yz.im.ui.base.IMNavigator;
import com.yz.im.utils.ToastUtil;

/**
 * Created by cys on 2016/7/28
 */
public abstract class IMBaseView<V extends IMMvpView, T extends IMMvpPresenter<V>> implements IMMvpView{

    private Context mContext;
    public T presenter;
    private ProgressDialog mProgressDialog;
    private ToastUtil mToastUtil;
    public IMNavigator mNavigator;

    public IMBaseView(Context context) {
        mContext = context;
        presenter = createPresenter();
        presenter.attachView((V)this);
        mNavigator = new IMNavigator();
    }

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(@NonNull T presenter) {
        this.presenter = presenter;
    }

    public V getMvpView() {
        return (V) this;
    }

    @Override
    public void showProgressDialog(int resId) {
        checkDialog();
        mProgressDialog.setTitle(resId);
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    @Override
    public void showProgressDialog(String content) {
        if (TextUtils.isEmpty(content)) {
            throw new NullPointerException("=== can't find String resource ===");
        }
        checkDialog();
        mProgressDialog.setTitle(content);
        mProgressDialog.show();
    }

    private void checkDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
        }
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void showToast(int resId) {
        checkToast();
        mToastUtil.showToastById(resId);
    }

    @Override
    public void showToast(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        checkToast();
        mToastUtil.showToast(content);
    }

    private void checkToast() {
        if (mToastUtil == null) {
            mToastUtil = ToastUtil.getInstance(mContext);
        }
    }

    public abstract T createPresenter();
}

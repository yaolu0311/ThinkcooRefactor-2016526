package com.yz.im.mvp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.yz.im.ui.base.IMNavigator;
import com.yz.im.utils.ToastUtil;

public abstract class IMBaseMvpActivity<V extends IMMvpView, T extends IMMvpPresenter<V>>
        extends EaseBaseActivity implements IMMvpView{

    public T presenter;

    private ProgressDialog mProgressDialog;
    private ToastUtil mToastUtil;
    public IMNavigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView((V)this);
        mNavigator = new IMNavigator();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        destroyDialog();
        destroyToast();
    }

    private void destroyDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    private void destroyToast() {
        if (mToastUtil != null) {
            mToastUtil.cancelToast();
            mToastUtil = null;
        }
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
        if (!isFinishing() && mProgressDialog != null) {
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
            mProgressDialog = new ProgressDialog(this);
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
            mToastUtil = ToastUtil.getInstance(this);
        }
    }

    public abstract T createPresenter();

}

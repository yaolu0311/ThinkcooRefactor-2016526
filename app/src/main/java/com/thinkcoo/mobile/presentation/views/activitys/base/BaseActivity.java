package com.thinkcoo.mobile.presentation.views.activitys.base;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.ThinkcooApp;
import com.thinkcoo.mobile.injector.components.ApplicationComponent;
import com.thinkcoo.mobile.injector.modules.ActivityModule;
import com.thinkcoo.mobile.presentation.mvp.views.BaseActivityHelpView;
import com.thinkcoo.mobile.presentation.mvp.views.BaseHintView;
import com.thinkcoo.mobile.presentation.views.dialog.GlobalToast;
import javax.inject.Inject;


/**
 * Created by Administrator on 2016/3/16.
 */
public abstract class BaseActivity extends MvpActivity<MvpView, MvpPresenter<MvpView>> implements BaseHintView,BaseActivityHelpView, FragmentInjectHelper,FragmentHintHelper,FragmentNavigatorHelper{


    @Inject
    public Navigator mNavigator;
    @Inject
    public GlobalToast mGlobalToast;

    protected BaseActivityDelegate mBaseActivityDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectAppComponent();
        mBaseActivityDelegate = new BaseActivityDelegateImpl(mNavigator);
        mBaseActivityDelegate.setupHostActivity(this);
        mBaseActivityDelegate.onCreate(savedInstanceState);
    }

    private void injectAppComponent() {
        getApplicationComponent().inject(this);
    }
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }
    protected  FragmentTransaction getFragmentTransaction(){
        return this.getSupportFragmentManager().beginTransaction();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaseActivityDelegate.onDestroy();
    }

    @Override
    public ApplicationComponent getApplicationComponent() {
        return ((ThinkcooApp) this.getApplication()).getApplicationComponent();
    }
    @Override
    public ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        initDaggerInject();
        return generatePresenter();
    }
    @Override
    public void showLongToast(String msg) {
        mGlobalToast.showLongToast(msg);
    }
    @Override
    public void showShortToast(int msgId) {
        mGlobalToast.showShortToast(msgId);
    }
    @Override
    public void showProgressDialog(int msgId) {
        mBaseActivityDelegate.showProgressDialog(msgId);
    }
    @Override
    public void hideProgressDialogIfShowing() {
        mBaseActivityDelegate.hideProgressDialogIfShowing();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void closeSelf() {
        finish();
    }
    @Override
    public void showToast(String errorMsg) {
        mGlobalToast.showShortToast(errorMsg);
    }

    @Override
    public Navigator getNavigator() {
        return mNavigator;
    }

    protected abstract MvpPresenter generatePresenter();
    protected abstract void initDaggerInject();

}

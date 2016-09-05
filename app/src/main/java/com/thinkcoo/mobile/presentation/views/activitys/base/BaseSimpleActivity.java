package com.thinkcoo.mobile.presentation.views.activitys.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.thinkcoo.mobile.ThinkcooApp;
import com.thinkcoo.mobile.injector.components.ApplicationComponent;
import com.thinkcoo.mobile.injector.modules.ActivityModule;
import com.thinkcoo.mobile.presentation.views.dialog.GlobalToast;
import javax.inject.Inject;

/**
 * Created by admin on 2016/5/26.
 */
public abstract class BaseSimpleActivity extends AppCompatActivity implements FragmentInjectHelper ,FragmentHintHelper,FragmentNavigatorHelper{

    @Inject
    public Navigator mNavigator;
    @Inject
    public GlobalToast mGlobalToast;

    public BaseActivityDelegate mBaseActivityDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectAppComponent();
        initDaggerInject();
        mBaseActivityDelegate = new BaseActivityDelegateImpl(mNavigator);
        mBaseActivityDelegate.setupHostActivity(this);
        mBaseActivityDelegate.onCreate(savedInstanceState);
    }
    private void injectAppComponent() {
        getApplicationComponent().inject(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mBaseActivityDelegate.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaseActivityDelegate.onDestroy();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mBaseActivityDelegate.onStop();
    }
    @Override
    public ApplicationComponent getApplicationComponent(){
        return ((ThinkcooApp)this.getApplication()).getApplicationComponent();
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
    public Navigator getNavigator() {
        return mNavigator;
    }

    @Override
    public ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
    protected void initDaggerInject(){}

    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }
    protected  FragmentTransaction getFragmentTransaction(){
        return this.getSupportFragmentManager().beginTransaction();
    }

}

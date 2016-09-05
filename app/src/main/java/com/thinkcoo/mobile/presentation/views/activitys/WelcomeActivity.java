package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.os.Bundle;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerAppLaunchComponent;
import com.thinkcoo.mobile.injector.modules.AccountModule;
import com.thinkcoo.mobile.injector.modules.AppLaunchModule;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.presentation.mvp.presenters.AppLaunchPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.WelcomeView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import javax.inject.Inject;

/**
 * Created by robert on 2016/5/22.
 */
public class WelcomeActivity extends BaseActivity implements WelcomeView {

    public static final String TAG = "WelcomeActivity";

    @Inject
    AppLaunchPresenter appLaunchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.welcomeStyle);
        setContentView(R.layout.activity_welcome);
        appLaunchPresenter.appLaunch();
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return appLaunchPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerAppLaunchComponent.builder()
                .accountModule(new AccountModule())
                .appLaunchModule(new AppLaunchModule())
                .applicationComponent(getApplicationComponent()).build().inject(this);
    }

    @Override
    public void gotoNextPageByAccount(Account account) {
        if (account.isEmpty()) {
            gotoLoginPage();
        } else if (!account.isLogin()){
            gotoLoginAndFillAccountName(account);
        }else {
            doAutoLogin(account);
        }
    }

    private void gotoLoginAndFillAccountName(Account account) {
        mNavigator.navigateToLoginAndFillAccountName(this,account.getAccountName());
        closeSelf();
    }

    private void doAutoLogin(Account account) {
        appLaunchPresenter.autoLogin(account);
    }

    private void gotoLoginPage() {
        mNavigator.navigateToLogin(this);
        closeSelf();
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
    public void showProgressDialog(int stringResId) {
        mBaseActivityDelegate.showProgressDialog(stringResId);
    }

    @Override
    public void hideProgressDialogIfShowing() {
        mBaseActivityDelegate.hideProgressDialogIfShowing();
    }

    @Override
    public void showToast(String stringResMsg) {
        mGlobalToast.showLongToast(stringResMsg);
    }

    @Override
    public void gotoLoginPageTryLogin(Account account) {
        mNavigator.navigateToLoginFillAccount(this,account);
        closeSelf();
    }

    @Override
    public void gotoHomePage() {
        mNavigator.navigateToMainActivity(this);
        closeSelf();
    }
}

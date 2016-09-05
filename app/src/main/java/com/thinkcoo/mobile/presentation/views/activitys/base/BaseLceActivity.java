package com.thinkcoo.mobile.presentation.views.activitys.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.ThinkcooApp;
import com.thinkcoo.mobile.injector.components.ApplicationComponent;
import com.thinkcoo.mobile.injector.modules.ActivityModule;
import com.thinkcoo.mobile.presentation.ErrorProcessIntent;
import com.thinkcoo.mobile.presentation.LceErrorBundle;
import com.thinkcoo.mobile.presentation.LceErrorBundleFactory;
import com.thinkcoo.mobile.presentation.mvp.views.BaseActivityHelpView;
import com.thinkcoo.mobile.presentation.mvp.views.BaseHintView;
import com.thinkcoo.mobile.presentation.mvp.views.BaseLceActivityView;
import com.thinkcoo.mobile.presentation.views.dialog.GlobalToast;
import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/5/31.
 */
public abstract class BaseLceActivity<T> extends MvpLceActivity<View, T, BaseLceActivityView<T>, MvpPresenter<BaseLceActivityView<T>>> implements BaseHintView,BaseActivityHelpView,FragmentInjectHelper, FragmentHintHelper, FragmentNavigatorHelper {


    TextView mTvTitle;
    FrameLayout mContentView;

    @Inject
    public Navigator mNavigator;
    @Inject
    public GlobalToast mGlobalToast;
    @Inject
    public LceErrorBundleFactory mLceErrorBundleFactory;

    protected BaseActivityDelegate mBaseActivityDelegate;

    private ErrorProcessIntent mLceErrorProcessIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        int realContentLayoutResId = getRealContentLayoutResId();
        if (realContentLayoutResId != 0){
            setContentView(R.layout.activity_base_lce);
            initViews();
            addRealContent(realContentLayoutResId);
            setTitle(mTvTitle);
        }
        mBaseActivityDelegate = new BaseActivityDelegateImpl(mNavigator);
        mBaseActivityDelegate.setupHostActivity(this);
        mBaseActivityDelegate.onCreate(savedInstanceState);
        getIntentExtras();
        loadData(false);
    }

    private void initViews() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mContentView = (FrameLayout) findViewById(R.id.contentView);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void addRealContent(int realContentLayoutResId) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.addView(getLayoutInflater().inflate(realContentLayoutResId, mContentView, false), layoutParams);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return "";
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
    public void showError(Throwable e, boolean pullToRefresh) {
        LceErrorBundle lceErrorBundle = mLceErrorBundleFactory.create(e, this);
        String errorMsg = getResources().getString(lceErrorBundle.getErrorMsg());
        mLceErrorProcessIntent = lceErrorBundle.getErrorProcessIntent();
        if (pullToRefresh) {
            showLightError(errorMsg);
        } else {
            buildErrorImg(errorView, lceErrorBundle.getErrorImg());
            errorView.setText(errorMsg);
            animateErrorViewIn();
        }
    }

    @Override
    protected void onErrorViewClicked() {
        if (null != mLceErrorProcessIntent) {
            startActivity(mLceErrorProcessIntent.getRealIntent());
        } else {
            super.onErrorViewClicked();
        }
    }

    private void buildErrorImg(TextView errorView, int errorImg) {
        if (errorImg == LceErrorBundle.NON_IMG) {
            return;
        }
        Drawable drawable = getResources().getDrawable(errorImg);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        errorView.setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.px_20));
        errorView.setCompoundDrawables(null, drawable, null, null);
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
    protected abstract void setTitle(TextView tvTitle);
    protected abstract int getRealContentLayoutResId();
    protected abstract void getIntentExtras();


}

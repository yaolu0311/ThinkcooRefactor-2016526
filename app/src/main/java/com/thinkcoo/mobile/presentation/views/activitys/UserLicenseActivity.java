package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.License;
import com.thinkcoo.mobile.presentation.mvp.views.BaseActivityHelpView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserLicenseActivity extends BaseSimpleActivity implements BaseActivityHelpView {


    private static final String USER_LICENSE = "userlicense" ;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.wv_user_license)
    WebView wvUserLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_license);
        ButterKnife.bind(this);
        setupViews();
        showUserLicense();
    }


    private void  setupViews() {
        tvTitle.setText(R.string.user_register_license);
    }

    private void showUserLicense() {
        License userLicense = (License) getIntent().getParcelableExtra(USER_LICENSE);
        wvUserLicense.getSettings().setJavaScriptEnabled(true);
        wvUserLicense.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        wvUserLicense.loadUrl(userLicense.getUrl());
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
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

    public static Intent getUserLicenseIntent(Context context,License license) {
        if (null == context || null == license) {
            throw new NullPointerException();
        }

        if (TextUtils.isEmpty(license.getUrl())) {
            throw new IllegalArgumentException("license url cannot be empty");
        }
        Intent intent = new Intent(context,UserLicenseActivity.class);
        intent.putExtra(USER_LICENSE, license);
        return intent;
    }
}

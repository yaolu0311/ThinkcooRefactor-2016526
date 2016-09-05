package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.presentation.mvp.presenters.SignaturePresenter;
import com.thinkcoo.mobile.presentation.mvp.views.SignatureView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignatureActivity extends BaseActivity implements SignatureView {
    @Inject
    SignaturePresenter mSignaturePresenter;
    @Bind(R.id.iv_back)
    TextView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.ac_edittext_update_signature)
    EditText acEdittextUpdateSignature;
    @Bind(R.id.ac_text_update_signature_wordcounts)
    TextView acTextUpdateSignatureWordcounts;
    @Bind(R.id.tv_other)
    TextView tvOther;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        ButterKnife.bind(this);
        setupViews();
    }

    protected void setupViews() {
        tvTitle.setText(R.string.gexingqianming);
        tvOther.setVisibility(View.VISIBLE);
        acEdittextUpdateSignature.setText(getIntent().getStringExtra(UserMainInfoActivity.EXTRA_USER_SIGNATURE_KEY));
        acTextUpdateSignatureWordcounts.setText(String.valueOf(30-acEdittextUpdateSignature.getText().length()));
        acEdittextUpdateSignature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                acTextUpdateSignatureWordcounts.setText(String.valueOf(30-s.length()));
            }
        });
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mSignaturePresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().applicationComponent(getApplicationComponent()).userModule(new UserModule()).activityModule(getActivityModule()).build().inject(this);
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
        mGlobalToast.showShortToast(stringResMsg);
    }

    @Override
    public String getSignature() {
        return acEdittextUpdateSignature.getText().toString();
    }

    @Override
    public void resultToUserMainInfoActivity() {
        Intent intent = new Intent();
        intent.putExtra(UserMainInfoActivity.EXTRA_USER_SIGNATURE_KEY, acEdittextUpdateSignature.getText().toString().trim());
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick({R.id.iv_back, R.id.tv_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                closeSelf();
                break;
            case R.id.tv_other:
                mSignaturePresenter.updateSignature();
                break;
        }
    }

    public static Intent getSignatureIntent(Context context, String signature) {
        Intent intent = new Intent(context, SignatureActivity.class);
        intent.putExtra(UserMainInfoActivity.EXTRA_USER_SIGNATURE_KEY, signature);
        return intent;

    }


}

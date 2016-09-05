package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CertificateNumberActivity extends BaseSimpleActivity {

    @Bind(R.id.iv_back)
    TextView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_other)
    TextView tvOther;
    @Bind(R.id.edit_certificate_number)
    EditText editCertificateNumber;

    @Inject
    InputCheckUtil mInputCheckUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_number);
        ButterKnife.bind(this);
        setupViews();
    }

    protected void setupViews() {
        tvOther.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.zhengjainhaoma);
        editCertificateNumber.setText(getIntent().getStringExtra(UserBasicInfoActivity.EXTRA_USER_CERTIFICATE_NUMBER_KEY));
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().activityModule(getActivityModule()).userModule(new UserModule()).applicationComponent(getApplicationComponent()).build().inject(this);

    }

    public static Intent getCertificateNumberInfoIntent(Context context, String certificateNumber) {
        Intent intent = new Intent(context, CertificateNumberActivity.class);
        intent.putExtra(UserBasicInfoActivity.EXTRA_USER_CERTIFICATE_NUMBER_KEY, certificateNumber);
        return intent;
    }

    @OnClick({R.id.iv_back, R.id.tv_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_other:
                Intent intent = new Intent();
                intent.putExtra(UserBasicInfoActivity.EXTRA_USER_CERTIFICATE_NUMBER_KEY, editCertificateNumber.getText().toString().trim());
                setResult(RESULT_OK, intent);
                this.finish();
                break;
        }
   }

}

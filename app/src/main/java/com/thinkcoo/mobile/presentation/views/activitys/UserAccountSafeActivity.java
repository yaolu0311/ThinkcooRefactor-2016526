package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/8/8.
 */
public class UserAccountSafeActivity extends BaseSimpleActivity {

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_other)
    TextView mTvOther;
    @Bind(R.id.iv_more)
    ImageView mIvMore;
    @Bind(R.id.layout_update_moblienum)
    RelativeLayout mLayoutUpdateMoblienum;
    @Bind(R.id.view_personal_inforone)
    View mViewPersonalInforone;
    @Bind(R.id.layout_update_pwd)
    RelativeLayout mLayoutUpdatePwd;
    @Bind(R.id.view_personal_mima)
    View mViewPersonalMima;
    @Bind(R.id.layout_update_emile)
    RelativeLayout mLayoutUpdateEmile;

    public static Intent getJumpActivity(Context context) {
        return new Intent(context, UserAccountSafeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_safe);
        ButterKnife.bind(this);

        setupViews();
    }

    private void setupViews() {
        mTvTitle.setVisibility(View.VISIBLE);
        mTvTitle.setText(R.string.account_safe);
    }

    @OnClick({R.id.iv_back,R.id.layout_update_moblienum, R.id.layout_update_pwd, R.id.layout_update_emile})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.layout_update_moblienum:
                mNavigator.navigateToChangePhoneNumberActivity(this);
                break;
            case R.id.layout_update_pwd:
                mNavigator.navigateToChangePasswordActivity(this);
                break;
            case R.id.layout_update_emile:
                break;
        }
    }
}

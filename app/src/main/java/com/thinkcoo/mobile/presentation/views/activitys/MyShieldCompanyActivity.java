package com.thinkcoo.mobile.presentation.views.activitys;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;
import com.thinkcoo.mobile.presentation.views.fragment.MyShieldCompanyFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/8/22.
 */
public class MyShieldCompanyActivity extends BaseSimpleActivity {

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    private MyShieldCompanyFragment mMyShieldCompanyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_shield_company);
        ButterKnife.bind(this);
        setupFragment();
    }

    private void setupFragment() {
        mMyShieldCompanyFragment = new MyShieldCompanyFragment();
        addFragment(R.id.fragment_container, mMyShieldCompanyFragment);
    }

    @OnClick(R.id.add_shield_company)
    public void onClick() {
        mNavigator.navigatorToAddShieldCompanyActivity(this);
    }
}

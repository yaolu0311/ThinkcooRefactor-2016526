package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;
import com.thinkcoo.mobile.presentation.views.fragment.ShieldCompanySearchResultFragment;

/**
 * Created by Robert.yao on 2016/8/23.
 */
public class AddShieldCompanyActivity extends BaseSimpleActivity{

    public static Intent getJumpActivity(Context activityContext) {
        Intent intent = new Intent(activityContext,AddShieldCompanyActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFragment();
    }
    private void setupFragment() {
        ShieldCompanySearchResultFragment shieldCompanySearchResultFragment = new ShieldCompanySearchResultFragment();
        addFragment(R.id.fragment_container, shieldCompanySearchResultFragment);
    }

}

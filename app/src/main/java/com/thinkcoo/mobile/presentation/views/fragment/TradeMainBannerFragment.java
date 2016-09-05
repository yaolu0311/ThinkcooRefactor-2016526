package com.thinkcoo.mobile.presentation.views.fragment;

import com.thinkcoo.mobile.injector.components.DaggerBannerComponent;
import com.thinkcoo.mobile.injector.modules.BannerModule;

/**
 * Created by Robert.yao on 2016/7/20.
 */
public class TradeMainBannerFragment extends BaseBannerFragment{

    @Override
    protected int provideBannerType() {
        return 0;
    }
    @Override
    protected void initDaggerInject() {
        DaggerBannerComponent.builder().applicationComponent(getFragmentInjectHelper().getApplicationComponent()).bannerModule(new BannerModule()).build().inject(this);
    }
}

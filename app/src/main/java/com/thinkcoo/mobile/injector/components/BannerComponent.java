package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.BannerModule;
import com.thinkcoo.mobile.presentation.views.fragment.TradeMainBannerFragment;

import dagger.Component;

/**
 * Created by Robert.yao on 2016/7/27.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = {BannerModule.class})
public interface BannerComponent {
    void inject(TradeMainBannerFragment tradeMainBannerFragment);
}

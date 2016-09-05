package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.BaiduModule;
import com.thinkcoo.mobile.injector.modules.BannerModule;
import com.thinkcoo.mobile.presentation.views.activitys.LoadSchoolBaiduAddressActivity;
import com.thinkcoo.mobile.presentation.views.fragment.LoadSchoolAddressFragment;

import dagger.Component;

/**
 * Created by Robert.yao on 2016/8/11.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = {BaiduModule.class})
public interface BaiduComponent {
    void inject(LoadSchoolAddressFragment loadSchoolAddressFragment);

}

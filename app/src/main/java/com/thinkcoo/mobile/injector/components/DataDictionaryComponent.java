package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.DataDictionaryModule;
import com.thinkcoo.mobile.presentation.views.fragment.CityListFragment;
import dagger.Component;

/**
 * Created by Robert.yao on 2016/7/28.
 */
@Component(dependencies = ApplicationComponent.class, modules = {DataDictionaryModule.class})
@ActivityScope
public interface DataDictionaryComponent {
    void inject(CityListFragment cityListFragment);
}

package com.thinkcoo.mobile.presentation.views.activitys.base;

import com.thinkcoo.mobile.injector.components.ApplicationComponent;
import com.thinkcoo.mobile.injector.modules.ActivityModule;

/**
 * Created by Robert.yao on 2016/7/26.
 */
public interface FragmentInjectHelper {
    ApplicationComponent getApplicationComponent();
    ActivityModule getActivityModule();
}

package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.AccountModule;
import com.thinkcoo.mobile.injector.modules.CompleteRegisterModule;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.presentation.views.activitys.CompleteRegisterActivity;

import dagger.Component;

/**
 * Created by Robert.yao on 2016/6/3.
 */
@Component(dependencies = ApplicationComponent.class , modules = {CompleteRegisterModule.class})
@ActivityScope
public interface CompleteRegisterComponent {
    void inject(CompleteRegisterActivity completeRegisterActivity);
}

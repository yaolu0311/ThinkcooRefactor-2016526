package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.AccountModule;
import com.thinkcoo.mobile.injector.modules.AppLaunchModule;
import com.thinkcoo.mobile.presentation.views.activitys.MainActivity;
import com.thinkcoo.mobile.presentation.views.activitys.WelcomeActivity;
import dagger.Component;

/**
 * Created by robert on 2016/5/22.
 */
@Component(dependencies = ApplicationComponent.class , modules = {AccountModule.class, AppLaunchModule.class})
@ActivityScope
public interface AppLaunchComponent {
    void inject(WelcomeActivity welcomeActivity);
}

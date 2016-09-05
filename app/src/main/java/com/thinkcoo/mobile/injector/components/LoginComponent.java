package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.LoginModule;
import com.thinkcoo.mobile.presentation.views.activitys.LoginActivity;
import com.thinkcoo.mobile.presentation.views.activitys.MainActivity;

import dagger.Component;

/**
 * Created by Robert.yao on 2016/6/3.
 */
@Component(dependencies = ApplicationComponent.class, modules = {LoginModule.class})
@ActivityScope
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
    void inject(MainActivity mainActivity);
}

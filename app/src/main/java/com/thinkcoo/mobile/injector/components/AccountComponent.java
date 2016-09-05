package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.AccountModule;
import com.thinkcoo.mobile.presentation.views.activitys.CompleteFindPasswordActivity;
import com.thinkcoo.mobile.presentation.views.activitys.RequestFindPasswordActivity;
import com.thinkcoo.mobile.presentation.views.activitys.RequestRegisterActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserChangePhoneNumberActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserSettingActivity;

import dagger.Component;

/**
 * Created by Administrator on 2016/5/20.
 */
@Component(dependencies = ApplicationComponent.class , modules = {AccountModule.class})
@ActivityScope
public interface AccountComponent {

    void inject(RequestRegisterActivity requestRegisterActivity);
    void inject(RequestFindPasswordActivity findPasswordActivity);
    void inject(CompleteFindPasswordActivity completeFindPasswordActivity);
    void inject(UserChangePhoneNumberActivity userChangePhoneNumberActivity);
    void inject(UserSettingActivity userSettingActivity);
}

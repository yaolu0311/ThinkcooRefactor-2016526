package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.ScheduleMainModule;
import com.thinkcoo.mobile.presentation.views.fragment.ScheduleFragment;
import dagger.Component;

/**
 * Created by Robert.yao on 2016/7/29.
 */
@Component(dependencies = ApplicationComponent.class , modules ={ScheduleMainModule.class })
@ActivityScope
public interface ScheduleMainComponent {
    void inject(ScheduleFragment scheduleFragment);
}

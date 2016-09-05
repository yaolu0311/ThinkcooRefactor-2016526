package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.ActivityModule;
import com.thinkcoo.mobile.injector.modules.GetJobModule;
import com.thinkcoo.mobile.presentation.views.activitys.GetJobMainActivity;
import com.thinkcoo.mobile.presentation.views.fragment.JobResultFragment;

import dagger.Component;

/**
 * Created by Robert.yao on 2016/8/19.
 */
@Component(dependencies = ApplicationComponent.class , modules ={GetJobModule.class , ActivityModule.class})
@ActivityScope
public interface GetJobComponent {
    void inject(GetJobMainActivity getJobMainActivity);
    void inject(JobResultFragment jobResultFragment);
}

package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.ActivityModule;
import com.thinkcoo.mobile.injector.modules.ResourceModule;
import com.thinkcoo.mobile.presentation.views.activitys.AddResourceActivity;
import com.thinkcoo.mobile.presentation.views.activitys.DownloadManagerActivity;
import com.thinkcoo.mobile.presentation.views.activitys.SelectFileToUploadActivity;
import com.thinkcoo.mobile.presentation.views.fragment.ResourceFragment;

import dagger.Component;

/**
 * Created by Robert.yao on 2016/7/4.
 */
@Component(dependencies = ApplicationComponent.class , modules ={ResourceModule.class , ActivityModule.class})
@ActivityScope
public interface ResourceComponent {

    void inject(AddResourceActivity addResourceActivity);
    void inject(ResourceFragment resourceFragment);
    void inject(SelectFileToUploadActivity selectFileToUploadActivity);
    void inject(DownloadManagerActivity downloadManagerActivity);
}

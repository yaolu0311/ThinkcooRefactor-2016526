package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.injector.modules.TrainModule;

import dagger.Component;

/**
 * Created by Leevin
 * CreateTime: 2016/8/18  9:31
 */
@Component(dependencies = ApplicationModule.class, modules = TrainModule.class)
@ActivityScope
public interface TrainComponent {
//        void  inject(TrainHomeActivity trainHomeActivity);
}


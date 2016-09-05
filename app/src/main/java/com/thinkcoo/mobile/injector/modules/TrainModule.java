package com.thinkcoo.mobile.injector.modules;

import com.thinkcoo.mobile.domain.location.GetLocationUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.repository.LocationRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;


/**
 * Created by Leevin
 * CreateTime: 2016/8/18  9:37
 */
@Module
public class TrainModule {

    @Provides
    @ActivityScope
    GetLocationUseCase provideGetLocationUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler workThread, LocationRepository locationRepository){
        return new GetLocationUseCase(uiThread,workThread,locationRepository);
    }

}

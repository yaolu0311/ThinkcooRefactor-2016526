package com.thinkcoo.mobile.injector.modules;

import com.thinkcoo.mobile.domain.resource.LoadResourceListUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.repository.ResourceRepository;

import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/7/4.
 */
@Module
public class ResourceModule {

    @Provides
    @ActivityScope
    LoadResourceListUseCase provideLoadResourceListUseCase(ResourceRepository scheduleRepository, @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread){
        return new LoadResourceListUseCase(mUiThread,mExecutorThread,scheduleRepository);
    }

}

package com.thinkcoo.mobile.injector.modules;

import com.thinkcoo.mobile.domain.get_job.LoadJobResultUseCase;
import com.thinkcoo.mobile.domain.location.GetLocationUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.repository.GetJobRepository;
import com.thinkcoo.mobile.model.repository.LocationRepository;
import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/19.
 */
@Module
public class GetJobModule {

    @Provides
    @ActivityScope
    GetLocationUseCase provideGetLocationUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work, LocationRepository locationRepository){
        return new GetLocationUseCase(ui,work,locationRepository);
    }
    @Provides
    @ActivityScope
    LoadJobResultUseCase provideLoadJobResultUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work, GetJobRepository getJobRepository){
        return new LoadJobResultUseCase(ui,work,getJobRepository);
    }

}

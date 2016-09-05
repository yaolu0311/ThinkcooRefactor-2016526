package com.thinkcoo.mobile.injector.modules;

import com.thinkcoo.mobile.domain.schedule.AddScheduleUseCase;
import com.thinkcoo.mobile.domain.schedule.LoadClassListCase;
import com.thinkcoo.mobile.domain.schedule.LoadRockResultStudentListUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;
import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/6/29.
 */
@Module
public class ScheduleModule {

    @ActivityScope
    @Provides
    AddScheduleUseCase provideAddScheduleUseCase(ScheduleRepository scheduleRepository, @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread){
        return new AddScheduleUseCase(mUiThread,mExecutorThread,scheduleRepository);
    }
    @ActivityScope
    @Provides
    LoadRockResultStudentListUseCase provideLoadRockResultStudentListUseCase(ScheduleRepository scheduleRepository, @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread){
        return new LoadRockResultStudentListUseCase(mUiThread,mExecutorThread,scheduleRepository);
    }
    @ActivityScope
    @Provides
    LoadClassListCase provideLoadClassListCase(ScheduleRepository scheduleRepository, @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread){
        return new LoadClassListCase(mUiThread,mExecutorThread,scheduleRepository);
    }
}

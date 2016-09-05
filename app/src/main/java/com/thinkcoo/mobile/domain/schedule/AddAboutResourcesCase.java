package com.thinkcoo.mobile.domain.schedule;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;

import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/6/28.
 */
public class AddAboutResourcesCase extends UseCase<Object>{

    ScheduleRepository mScheduleRepository;
    protected AddAboutResourcesCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                    @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,ScheduleRepository scheduleRepository) {
        super(mUiThread, mExecutorThread);
        this.mScheduleRepository=scheduleRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object[] q) {
        // TODO: 2016/6/28
        return null;
    }
}

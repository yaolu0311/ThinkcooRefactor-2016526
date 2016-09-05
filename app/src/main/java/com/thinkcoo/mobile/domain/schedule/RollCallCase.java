package com.thinkcoo.mobile.domain.schedule;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/6/30.
 */
public class RollCallCase extends UseCase<Object>{
    ScheduleRepository scheduleRepository;
    @Inject
    protected RollCallCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                           @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,ScheduleRepository scheduleRepository) {
        super(mUiThread, mExecutorThread);
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        // TODO: 2016/6/30
        return null;
    }
}

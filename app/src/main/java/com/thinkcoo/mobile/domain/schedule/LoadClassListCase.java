package com.thinkcoo.mobile.domain.schedule;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * author ：ml on 2016/7/12
 */
public class LoadClassListCase extends UseCase<Object>{

    ScheduleRepository mScheduleRepository;
    @Inject
    public LoadClassListCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,ScheduleRepository scheduleRepository) {
        super(mUiThread, mExecutorThread);
        this.mScheduleRepository = scheduleRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mScheduleRepository.loadClassList((String)q[0]);
    }
}

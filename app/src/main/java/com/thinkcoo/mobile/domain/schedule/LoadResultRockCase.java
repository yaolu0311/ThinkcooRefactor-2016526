package com.thinkcoo.mobile.domain.schedule;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * author ：ml on 2016/8/9
 */
public class LoadResultRockCase extends UseCase<String>{
    ScheduleRepository mScheduleRepository;

    @Inject
    protected LoadResultRockCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                 @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,
                                 ScheduleRepository scheduleRepository) {
        super(mUiThread, mExecutorThread);
        this.mScheduleRepository = scheduleRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(String... q) {
        // TODO: 2016/8/12    return mScheduleRepository.loadResultData(q[0], q[1], q[2], q[3]);
        return null;

    }
}

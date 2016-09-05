package com.thinkcoo.mobile.domain.schedule;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Leevin
 * CreateTime: 2016/6/28  15:58
 */
public class LoadEventListUseCase extends UseCase<String> {


    ScheduleRepository mScheduleRepository;

    @Inject
    public LoadEventListUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, ScheduleRepository scheduleRepository) {
        super(mUiThread, mExecutorThread);
        this.mScheduleRepository = scheduleRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(String... q) {
        return mScheduleRepository.loadEventList(q[0],q[1]);
    }
}

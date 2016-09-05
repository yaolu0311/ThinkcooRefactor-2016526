package com.thinkcoo.mobile.domain.schedule;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by wyy on 2016/8/5.
 */
public class GetNoticeListCase   extends UseCase<String> {
    ScheduleRepository mScheduleRepository;

    @Inject
    public GetNoticeListCase(
            @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
            @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,
            ScheduleRepository scheduleRepository) {
        super(mUiThread, mExecutorThread);
        this.mScheduleRepository = scheduleRepository;
    }



    @Override
    protected Observable buildUseCaseObservable(String... q) {
        return mScheduleRepository.loadNoticeList(q[0]);
    }
}
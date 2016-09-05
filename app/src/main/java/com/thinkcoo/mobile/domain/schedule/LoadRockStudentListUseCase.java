package com.thinkcoo.mobile.domain.schedule;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;
import com.thinkcoo.mobile.presentation.views.PageMachine;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * author ：ml on 2016/8/12
 */
public class LoadRockStudentListUseCase extends UseCase<Object>{

    ScheduleRepository mScheduleRepository;
    @Inject
    public LoadRockStudentListUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                      @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,
                                      ScheduleRepository scheduleRepository) {
        super(mUiThread, mExecutorThread);
        this.mScheduleRepository = scheduleRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mScheduleRepository.loadRockStudentListData((String)q[0],(String)q[1],(PageMachine) q[2]);
    }
}

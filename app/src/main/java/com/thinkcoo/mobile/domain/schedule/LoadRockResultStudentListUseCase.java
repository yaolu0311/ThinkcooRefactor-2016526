package com.thinkcoo.mobile.domain.schedule;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.entity.RequestParam.LoadRockResultStudentListParam;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * author ：ml on 2016/8/12
 */
public class LoadRockResultStudentListUseCase extends UseCase<Object>{

    ScheduleRepository mScheduleRepository;
    @Inject
    public LoadRockResultStudentListUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                            @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,
                                            ScheduleRepository scheduleRepository) {
        super(mUiThread, mExecutorThread);
        this.mScheduleRepository = scheduleRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        LoadRockResultStudentListParam param  = (LoadRockResultStudentListParam) q[0];
        return mScheduleRepository.loadResultData(param.getUuid(),param.getGroupId(),param.getPageMachine());
    }
}

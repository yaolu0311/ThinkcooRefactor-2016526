package com.thinkcoo.mobile.domain.schedule;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.entity.RequestParam.SelectStudentParam;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/7/4.
 */
public class MnualAddStudentsCase extends UseCase<Object> {
    ScheduleRepository mScheduleRepository;

    @Inject
    public MnualAddStudentsCase(
            @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
            @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,
            ScheduleRepository scheduleRepository) {
        super(mUiThread, mExecutorThread);
        this.mScheduleRepository = scheduleRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        SelectStudentParam selectStudentParam = (SelectStudentParam)q[0];
        return mScheduleRepository.maualSerchStudents(selectStudentParam.getEventId(),selectStudentParam.getSchool(),selectStudentParam.getClassProfession(),selectStudentParam.getKeyWord(),selectStudentParam.getPageMachine());
    }
}
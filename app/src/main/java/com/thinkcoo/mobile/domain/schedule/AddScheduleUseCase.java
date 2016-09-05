package com.thinkcoo.mobile.domain.schedule;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.Schedule;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Leevin
 * CreateTime: 2016/6/28  14:24
 */
public class AddScheduleUseCase extends UseCase<Schedule> {

    ScheduleRepository mScheduleRepository;

    @Inject
    public AddScheduleUseCase(Scheduler mUiThread, Scheduler mExecutorThread,
            ScheduleRepository scheduleRepository) {
        super(mUiThread, mExecutorThread);
        this.mScheduleRepository = scheduleRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Schedule... q) {
        return mScheduleRepository.AddSchedule(q[0]);
    }
}

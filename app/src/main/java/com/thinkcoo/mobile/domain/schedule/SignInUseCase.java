package com.thinkcoo.mobile.domain.schedule;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * author ：ml on 2016/8/4
 */
public class SignInUseCase extends UseCase<Object> {
    ScheduleRepository mScheduleRepository;

    @Inject
    protected SignInUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                            @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,
                            ScheduleRepository scheduleRepository) {
        super(mUiThread, mExecutorThread);
        this.mScheduleRepository = scheduleRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mScheduleRepository.signIn((Event) q[0],(Location) q[1]);
    }
}

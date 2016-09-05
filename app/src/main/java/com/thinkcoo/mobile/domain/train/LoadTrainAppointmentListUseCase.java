package com.thinkcoo.mobile.domain.train;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.TrainRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * author ：ml on 2016/7/27
 */
public class LoadTrainAppointmentListUseCase extends UseCase<Void>{

    TrainRepository mTrainRepository;

    @Inject
    protected LoadTrainAppointmentListUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                              @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,TrainRepository trainRepository) {
        super(mUiThread, mExecutorThread);
        mTrainRepository = trainRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Void... q) {
        return null;
        //return mTrainRepository.loadMyAppointmentList();
    }
}

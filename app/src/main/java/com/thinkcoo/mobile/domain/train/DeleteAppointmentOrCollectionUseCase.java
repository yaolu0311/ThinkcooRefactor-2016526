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
public class DeleteAppointmentOrCollectionUseCase extends UseCase<String>{


    TrainRepository mTrainRepository;

    @Inject
    protected DeleteAppointmentOrCollectionUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                                   @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, TrainRepository trainRepository) {
        super(mUiThread, mExecutorThread);
        mTrainRepository = trainRepository;
    }


    @Override
    protected Observable buildUseCaseObservable(String... q) {
        return mTrainRepository.deleteMyAppointmentOrCollection((String)q[0]);
    }
}

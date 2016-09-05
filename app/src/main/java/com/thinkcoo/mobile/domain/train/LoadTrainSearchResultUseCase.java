package com.thinkcoo.mobile.domain.train;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.entity.TrainCourseFilter;
import com.thinkcoo.mobile.model.repository.TrainRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * author ：ml on 2016/8/1
 */
public class LoadTrainSearchResultUseCase extends UseCase<TrainCourseFilter> {

    TrainRepository mTrainRepository;

    @Inject
    protected LoadTrainSearchResultUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                           @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,TrainRepository trainRepository) {
        super(mUiThread, mExecutorThread);
        mTrainRepository = trainRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(TrainCourseFilter... q) {
        return mTrainRepository.loadTrainCourseList((TrainCourseFilter)q[0]);
    }
}

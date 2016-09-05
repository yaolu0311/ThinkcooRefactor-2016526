package com.thinkcoo.mobile.domain.train;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.repository.TrainRepository;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Leevin
 * CreateTime: 2016/8/19  17:14
 */
public class LoadAreaUseCase extends UseCase<String> {

    TrainRepository mTrainRepository;

    public LoadAreaUseCase(Scheduler mUiThread, Scheduler mExecutorThread, TrainRepository trainRepository) {
        super(mUiThread, mExecutorThread);
        mTrainRepository = trainRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(String... q) {
        return mTrainRepository.loadAreaByParentCode(q[0]);
    }


}

package com.thinkcoo.mobile.domain.get_job;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.SearchJobParams;
import com.thinkcoo.mobile.model.repository.GetJobRepository;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class LoadJobResultUseCase extends UseCase<Object>{

    GetJobRepository mGetJobRepository;

    public LoadJobResultUseCase(Scheduler mUiThread, Scheduler mExecutorThread, GetJobRepository getJobRepository) {
        super(mUiThread, mExecutorThread);
        mGetJobRepository = getJobRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mGetJobRepository.searchJob((SearchJobParams) q[0]);
    }
}

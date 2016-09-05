package com.thinkcoo.mobile.domain.get_job;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.repository.GetJobRepository;
import com.thinkcoo.mobile.presentation.views.PageMachine;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class MyCollectJobUseCase extends UseCase<PageMachine> {

    GetJobRepository mGetJobRepository;

    public MyCollectJobUseCase(Scheduler mUiThread, Scheduler mExecutorThread, GetJobRepository getJobRepository) {
        super(mUiThread, mExecutorThread);
        mGetJobRepository = getJobRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(PageMachine... q) {
        return mGetJobRepository.loadMyCollect(q[0]);
    }
}

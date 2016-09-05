package com.thinkcoo.mobile.domain.get_job;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.repository.GetJobRepository;
import com.thinkcoo.mobile.presentation.views.PageMachine;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class LoadMyShieldCompanyUseCase extends UseCase<PageMachine>{

    GetJobRepository mGetJobRepository;

    public LoadMyShieldCompanyUseCase(Scheduler mUiThread, Scheduler mExecutorThread, GetJobRepository getJobRepository) {
        super(mUiThread, mExecutorThread);
        mGetJobRepository = getJobRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(PageMachine... q) {
        return mGetJobRepository.loadMyShieldCompany(q[0]);
    }
}

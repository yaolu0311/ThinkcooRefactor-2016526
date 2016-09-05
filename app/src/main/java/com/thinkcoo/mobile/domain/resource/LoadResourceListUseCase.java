package com.thinkcoo.mobile.domain.resource;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.repository.ResourceRepository;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/7/4.
 */
public class LoadResourceListUseCase extends UseCase<String>{


    ResourceRepository mResourceRepository;

    public LoadResourceListUseCase(Scheduler mUiThread, Scheduler mExecutorThread, ResourceRepository resourceRepository) {
        super(mUiThread, mExecutorThread);
        mResourceRepository = resourceRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(String... q) {
        return null;
    }


}

package com.thinkcoo.mobile.domain.user;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.entity.UserHarvest;
import com.thinkcoo.mobile.model.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Leevin
 * CreateTime: 2016/5/30  17:39
 */
public class LoadUserHarvestDetailUseCase extends UseCase<Object> {

    UserRepository mUserRepository;

    @Inject
    public LoadUserHarvestDetailUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                        @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, UserRepository userRepository) {
        super(mUiThread, mExecutorThread);
        mUserRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mUserRepository.loadUserHarvestDetail((UserHarvest)q[0]);
    }
}

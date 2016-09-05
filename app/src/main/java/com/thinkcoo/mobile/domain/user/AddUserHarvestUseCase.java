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
 * CreateTime: 2016/5/31  14:16
 */
public class AddUserHarvestUseCase extends UseCase<Object>{

    private UserRepository mUserRepository;

    @Inject
    public AddUserHarvestUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                 @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, UserRepository userRepository) {
        super(mUiThread, mExecutorThread);
        mUserRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mUserRepository.addUserHarvest((UserHarvest) q[0]);
    }
}

package com.thinkcoo.mobile.domain.user;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.UserHarvest;
import com.thinkcoo.mobile.model.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Leevin
 * CreateTime: 2016/5/31  15:31
 */
public class UpdateUserHarvestUseCase extends UseCase<Object>{

    private UserRepository mUserRepository;

    @Inject
    public UpdateUserHarvestUseCase(Scheduler mUiThread, Scheduler mExecutorThread, UserRepository userRepository) {
        super(mUiThread, mExecutorThread);
        mUserRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mUserRepository.updateUserHarvest((UserHarvest) q[0]);
    }
}

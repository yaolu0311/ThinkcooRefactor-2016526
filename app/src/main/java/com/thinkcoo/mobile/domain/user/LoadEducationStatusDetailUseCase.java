package com.thinkcoo.mobile.domain.user;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.model.repository.UserStatusRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/6/2.
 */
public class LoadEducationStatusDetailUseCase extends UseCase<Object>{

    UserStatusRepository mUserRepository;

    @Inject
    public LoadEducationStatusDetailUseCase(
            @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
            @Named(ApplicationModule.EXECUTOR_THREAD_NAMED)  Scheduler mExecutorThread,
            UserStatusRepository mUserRepository) {
        super(mUiThread, mExecutorThread);
        this.mUserRepository = mUserRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mUserRepository.loadUserEducationStatusDetail((UserStatus) q[0]);
    }
}

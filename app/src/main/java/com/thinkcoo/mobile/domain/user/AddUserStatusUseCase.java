package com.thinkcoo.mobile.domain.user;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.model.repository.UserRepository;
import com.thinkcoo.mobile.model.repository.UserStatusRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/6/2.
 */
public class AddUserStatusUseCase extends UseCase<Object>{

    UserStatusRepository mUserStatusRepository;

    @Inject
    public AddUserStatusUseCase(
            @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
            @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,
            UserStatusRepository userStatusRepository) {
        super(mUiThread, mExecutorThread);
        this.mUserStatusRepository = userStatusRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mUserStatusRepository.addUserStatus((UserStatus) q[0]);
    }
}

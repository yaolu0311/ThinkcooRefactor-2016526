package com.thinkcoo.mobile.domain.user;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.UserRepository;
import com.thinkcoo.mobile.model.repository.UserStatusRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/5/31.
 */
public class ToggleUserStatusUseCase extends UseCase<String>{

    UserStatusRepository mUserStatusRepository;

    @Inject
    public ToggleUserStatusUseCase(
            @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
            @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,
            UserStatusRepository userStatusRepository) {
        super(mUiThread, mExecutorThread);
        this.mUserStatusRepository = userStatusRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(String... q) {
        return mUserStatusRepository.toggleUserStatusOpenStatus(q[0], q[1]);
    }
}

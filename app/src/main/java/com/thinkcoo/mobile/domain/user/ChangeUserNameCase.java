package com.thinkcoo.mobile.domain.user;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/5/31.
 */
public class ChangeUserNameCase extends UseCase<Object> {
    UserRepository mUserRepository;

    @Inject
    protected ChangeUserNameCase(
            @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread,
            @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler executorThread,
            UserRepository userRepository) {
        super(uiThread, executorThread);
        this.mUserRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mUserRepository.changeUserName((String) q[0]);
    }
}

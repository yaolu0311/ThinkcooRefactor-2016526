package com.thinkcoo.mobile.domain.user;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/6/17.
 */
public class UserDeleteWorkExperienceUseCase extends UseCase<String>{

    UserRepository mUserRepository;

    @Inject
    protected UserDeleteWorkExperienceUseCase(
            @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
            @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,
            UserRepository userRepository) {
        super(mUiThread, mExecutorThread);
        this.mUserRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(String... q) {
        return mUserRepository.deleteWorkExperience(q[0]);
    }
}

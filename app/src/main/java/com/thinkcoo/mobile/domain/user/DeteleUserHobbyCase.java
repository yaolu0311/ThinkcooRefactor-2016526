package com.thinkcoo.mobile.domain.user;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/6/1.
 */
public class DeteleUserHobbyCase extends UseCase<Object>{

    UserRepository mUserRepository;
    @Inject
    protected DeteleUserHobbyCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                  @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,  UserRepository mUserRepository) {
        super(mUiThread, mExecutorThread);
        this.mUserRepository = mUserRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mUserRepository.deleteUserHobby((String) q[0]);
    }
}

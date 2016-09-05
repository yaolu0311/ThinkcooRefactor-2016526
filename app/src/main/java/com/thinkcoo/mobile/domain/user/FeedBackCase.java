package com.thinkcoo.mobile.domain.user;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/8/16.
 */
public class FeedBackCase extends UseCase<String> {


    UserRepository mUserRepository;

    @Inject
    protected FeedBackCase(
            @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
            @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,
            UserRepository mUserRepository) {
        super(mUiThread, mExecutorThread);
        this.mUserRepository = mUserRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(String... q) {
        return mUserRepository.feedBack(q[0],q[1],q[2]);
    }
}

package com.thinkcoo.mobile.domain.user;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/8/18.
 */
public class InviteFriendUseCase extends UseCase<String> {

    UserRepository userRepository;

    @Inject
    protected InviteFriendUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                            @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, UserRepository userRepository) {
        super(mUiThread, mExecutorThread);
        this.userRepository=userRepository;
    }


    @Override
    protected Observable buildUseCaseObservable(String...q) {
        return userRepository.getInviteFriend(q[0]);
    }
}


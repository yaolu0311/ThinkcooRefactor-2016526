package com.thinkcoo.mobile.domain.user;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.AccountRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by cys on 2016/8/8
 */
public class ModifyPasswordUseCase extends UseCase<String> {

    AccountRepository mRepository;

    @Inject
    protected ModifyPasswordUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                    @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, AccountRepository userRepository) {
        super(mUiThread, mExecutorThread);
        this.mRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(String... q) {
        return mRepository.modifyPassword(q[0], q[1]);
    }
}

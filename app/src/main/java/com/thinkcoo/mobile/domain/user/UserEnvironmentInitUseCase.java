package com.thinkcoo.mobile.domain.user;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.model.repository.AccountRepository;
import com.thinkcoo.mobile.model.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/6/3.
 */
public class UserEnvironmentInitUseCase extends UseCase<User>{

    AccountRepository mAccountRepository;

    @Inject
    public UserEnvironmentInitUseCase(@Named(
        ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, AccountRepository accountRepository) {
        super(mUiThread, mExecutorThread);
        this.mAccountRepository = accountRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(User... q) {
        return mAccountRepository.userEnvironmentInit(q[0]);
    }
}

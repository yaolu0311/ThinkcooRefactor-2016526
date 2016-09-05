package com.thinkcoo.mobile.domain.account;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.repository.AccountRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by admin on 2016/5/24.
 */
public class RequestVcodeUseCase extends UseCase<Account> {

    AccountRepository mAccountRepository;


    @Inject
    public RequestVcodeUseCase(
            @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
            @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, AccountRepository mAccountRepository) {
        super(mUiThread, mExecutorThread);
        this.mAccountRepository = mAccountRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Account... q) {
        return mAccountRepository.requestRegisterVcode((Account)q[0]);
    }
}

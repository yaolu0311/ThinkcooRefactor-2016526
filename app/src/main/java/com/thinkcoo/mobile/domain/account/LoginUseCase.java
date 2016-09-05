package com.thinkcoo.mobile.domain.account;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.repository.AccountRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/5/20.
 */
public class LoginUseCase extends UseCase<Account> {

    AccountRepository accountRepository;

    @Inject
    public LoginUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, AccountRepository accountRepository) {
        super(mUiThread, mExecutorThread);
        this.accountRepository = accountRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Account... accounts) {
        ThinkcooLog.e("luc","=== buildUseCaseObservable ===");
        return accountRepository.login(accounts[0]);
    }

}

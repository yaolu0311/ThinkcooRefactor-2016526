package com.thinkcoo.mobile.domain.account;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.repository.AccountRepository;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/17.
 */
public class AutoLoginUseCase extends UseCase<Account> {

    AccountRepository mAccountRepository;

    public AutoLoginUseCase(Scheduler mUiThread, Scheduler mExecutorThread, AccountRepository accountRepository) {
        super(mUiThread, mExecutorThread);
        mAccountRepository = accountRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Account... q) {
        return mAccountRepository.autoLogin(q[0]);
    }
}

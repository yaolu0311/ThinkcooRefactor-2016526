package com.thinkcoo.mobile.domain.account;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.repository.AccountRepository;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by robert on 2016/5/22.
 */
public class GetLoggedAccountUseCase extends UseCase<Long>{

    AccountRepository accountRepository;

    @Inject
    public GetLoggedAccountUseCase(@Named(ApplicationModule.UI_THREAD_NAMED)Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED)Scheduler mExecutorThread, AccountRepository accountRepository) {
        super(mUiThread, mExecutorThread);
        this.accountRepository = accountRepository;
    }
    @Override
    protected Observable<Account> buildUseCaseObservable(Long... q) {
        return accountRepository.getLoggedAccount().delay(q[0], TimeUnit.MILLISECONDS);
    }

}

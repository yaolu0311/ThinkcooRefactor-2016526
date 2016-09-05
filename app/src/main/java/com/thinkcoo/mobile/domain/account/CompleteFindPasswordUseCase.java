package com.thinkcoo.mobile.domain.account;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.CheckVcode;
import com.thinkcoo.mobile.model.repository.AccountRepository;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/5/24.
 */
public class CompleteFindPasswordUseCase extends UseCase<Object> {

    @Inject
    AccountRepository mAccountRepository;

    @Inject
    public CompleteFindPasswordUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED)Scheduler executorThread, AccountRepository accountRepository) {
        super(uiThread, executorThread);
        this.mAccountRepository = accountRepository;
    }



    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mAccountRepository.completeFindPassword((CheckVcode) q[0],(String) q[1]);
    }
}

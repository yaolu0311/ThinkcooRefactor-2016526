package com.thinkcoo.mobile.domain.account;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.AccountRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Leevin
 * CreateTime: 2016/8/8  10:48
 */
public class GetOldPhoneNumberUseCase extends UseCase<Void> {

    AccountRepository mAccountRepository;

    @Inject
    public GetOldPhoneNumberUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                    @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, AccountRepository accountRepository) {
        super(mUiThread, mExecutorThread);
        mAccountRepository = accountRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Void... q) {
        return mAccountRepository.getLoggedAccount();
    }

}

package com.thinkcoo.mobile.injector.modules;

import com.thinkcoo.mobile.domain.account.AutoLoginUseCase;
import com.thinkcoo.mobile.domain.account.CompleteRegisterUseCase;
import com.thinkcoo.mobile.domain.account.GetLoggedAccountUseCase;
import com.thinkcoo.mobile.domain.account.LoginUseCase;
import com.thinkcoo.mobile.domain.account.LogoutUseCase;
import com.thinkcoo.mobile.domain.account.RequestRegisterUseCase;
import com.thinkcoo.mobile.domain.account.RequestVcodeUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.repository.AccountRepository;
import com.thinkcoo.mobile.model.repository.impl.AccountRepositoryImpl;
import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/5/20.
 */
@Module
public class AccountModule {


    @Provides
    @ActivityScope
    RequestRegisterUseCase provideRequestRegisterUseCase(AccountRepository repository, @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler executorThread){
        return new RequestRegisterUseCase(uiThread,executorThread,repository);
    }
    @Provides
    @ActivityScope
    GetLoggedAccountUseCase provideGetLoggedAccountUseCase(AccountRepository repository, @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler executorThread){
        return new GetLoggedAccountUseCase(uiThread,executorThread,repository);
    }
    @Provides
    @ActivityScope
    RequestVcodeUseCase provideRequestVcodeUseCase(AccountRepository repository , @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler executorThread){
        return new RequestVcodeUseCase(uiThread,executorThread,repository);
    }
    @Provides
    @ActivityScope
    CompleteRegisterUseCase  provideCompleteRegisterUseCase(AccountRepository repository , @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler executorThread){
        return new CompleteRegisterUseCase(uiThread,executorThread,repository);
    }
    @Provides
    @ActivityScope
    AutoLoginUseCase provideAutoLoginUseCase(AccountRepository repository , @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler executorThread){
        return new AutoLoginUseCase(uiThread,executorThread,repository);
    }
    @Provides
    @ActivityScope
    LogoutUseCase provideLogoutUseCase(AccountRepository repository , @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler executorThread){
        return new LogoutUseCase(uiThread,executorThread,repository);
    }


}

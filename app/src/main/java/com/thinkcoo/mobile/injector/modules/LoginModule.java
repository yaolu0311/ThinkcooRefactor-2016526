package com.thinkcoo.mobile.injector.modules;

import com.thinkcoo.mobile.domain.account.AutoLoginUseCase;
import com.thinkcoo.mobile.domain.account.LoginUseCase;
import com.thinkcoo.mobile.domain.user.UserEnvironmentInitUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.repository.AccountRepository;
import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/6/3.
 */
@Module
public class LoginModule {

    @Provides
    @ActivityScope
    LoginUseCase provideLoginUseCase(AccountRepository repository, @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler executorThread){
        return new LoginUseCase(uiThread,executorThread,repository);
    }

    @Provides
    @ActivityScope
    UserEnvironmentInitUseCase provideUserEnvironmentInitUseCase(AccountRepository accountRepository, @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler executorThread){
        return new UserEnvironmentInitUseCase(uiThread,executorThread,accountRepository);
    }

    @Provides
    @ActivityScope
    AutoLoginUseCase provideAutoLoginUseCase(AccountRepository repository, @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler executorThread){
        return new AutoLoginUseCase(uiThread,executorThread,repository);
    }

}

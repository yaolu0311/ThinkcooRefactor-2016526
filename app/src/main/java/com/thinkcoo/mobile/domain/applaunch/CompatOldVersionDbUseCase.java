package com.thinkcoo.mobile.domain.applaunch;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.AppLaunchRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by robert on 2016/5/22.
 */
public class CompatOldVersionDbUseCase extends UseCase<Void>{

    AppLaunchRepository appLaunchRepository;

    @Inject
    public CompatOldVersionDbUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                     @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, AppLaunchRepository appLaunchRepository) {
        super(mUiThread, mExecutorThread);
        this.appLaunchRepository = appLaunchRepository;
    }
    @Override
    protected Observable buildUseCaseObservable(Void... q) {
        return appLaunchRepository.compatOldVersionDb();
    }


}

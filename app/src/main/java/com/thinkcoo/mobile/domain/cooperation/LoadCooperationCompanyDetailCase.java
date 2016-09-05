package com.thinkcoo.mobile.domain.cooperation;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;

import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * author ：ml on 2016/7/27
 */
public class LoadCooperationCompanyDetailCase extends UseCase<Object>{
    // TODO: 2016/7/27  仓储
    protected LoadCooperationCompanyDetailCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                               @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        // TODO: 2016/7/27
        return null;
    }
}

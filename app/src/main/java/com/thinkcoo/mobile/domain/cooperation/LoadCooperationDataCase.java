package com.thinkcoo.mobile.domain.cooperation;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;

import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * author ：ml on 2016/7/22
 */
public class LoadCooperationDataCase extends UseCase<Object>{
    // TODO: 2016/7/22 仓储  
    protected LoadCooperationDataCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                      @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
    }

    @Override
    protected Observable buildUseCaseObservable(Object[] q) {
        // TODO: 2016/7/22
        return null;
    }
}

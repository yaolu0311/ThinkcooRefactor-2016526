package com.thinkcoo.mobile.domain.train;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * author ：ml on 2016/7/27
 */
public class CancelTrainCollectionUseCase extends UseCase<Object> {
    // TODO: 2016/7/27
    @Inject
    protected CancelTrainCollectionUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                           @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return null;
    }
}

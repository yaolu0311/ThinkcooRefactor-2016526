package com.thinkcoo.mobile.domain.train;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;

import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Leevin
 * CreateTime: 2016/8/18  18:27
 */
public class LoadTrainCollectionListUseCase extends UseCase<Object> {


    public LoadTrainCollectionListUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                        @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return null;
    }

}

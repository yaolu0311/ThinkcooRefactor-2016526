package com.thinkcoo.mobile.domain;

import java.util.List;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/7/22.
 */
public abstract class BaseLceDataLoadCase<D> extends UseCase<Object>{

    public BaseLceDataLoadCase(Scheduler mUiThread, Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
    }
    @Override
    protected Observable<List<D>> buildUseCaseObservable(Object... q) {
        return null;
    }
}

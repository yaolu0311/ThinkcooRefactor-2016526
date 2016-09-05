package com.thinkcoo.mobile.domain.im;

import com.thinkcoo.mobile.domain.UseCase;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/6/27.
 */
public class ImLoginUseCase extends UseCase {


    protected ImLoginUseCase(Scheduler mUiThread, Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
    }

    @Override
    protected Observable buildUseCaseObservable(Object[] q) {
        return null;
    }
}

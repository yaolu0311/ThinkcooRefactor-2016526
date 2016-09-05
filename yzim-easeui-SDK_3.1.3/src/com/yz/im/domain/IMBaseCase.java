package com.yz.im.domain;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cys on 2016/7/12
 */
public abstract class IMBaseCase<P extends Object> {

    public static final Scheduler MAIN_THREAD = AndroidSchedulers.mainThread();
    public static final Scheduler EXECUTOR_THREAD = Schedulers.io();
    public static final Scheduler CURRENT_THREAD = Schedulers.immediate();
    public static final Scheduler EXECUTOR_SINGLE_THREAD = Schedulers.from(Executors.newSingleThreadExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }));

    private Scheduler mUiThread;
    private Scheduler mExecutorThread;

    private Subscription mSubscription;

    public IMBaseCase(Scheduler uiThread, Scheduler executorThread) {
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public abstract Observable createObservable(P... parameter);

    public void execute(Subscriber subscriber, P... parameter){
        this.mSubscription = createObservable(parameter)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread)
                .subscribe(subscriber);
    }

    public void unSubscribe() {
        if (!mSubscription.isUnsubscribed() && mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}

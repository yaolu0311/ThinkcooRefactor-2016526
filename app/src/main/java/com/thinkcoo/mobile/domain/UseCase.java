package com.thinkcoo.mobile.domain;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class UseCase<Q extends Object> {

    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    private Subscription subscription = Subscriptions.empty();

    protected UseCase(Scheduler mUiThread, Scheduler mExecutorThread) {
        this.mUiThread = mUiThread;
        this.mExecutorThread = mExecutorThread;
    }

    protected abstract Observable buildUseCaseObservable(Q ... q);

    public void unSubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void execute(Subscriber UseCaseSubscriber , Q ... q) {
        this.subscription = this.buildUseCaseObservable(q)
                .subscribeOn(mExecutorThread)
                .observeOn(mUiThread)
                .subscribe(UseCaseSubscriber);
    }

}

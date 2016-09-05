package com.yz.im.domain;

import rx.Observable;

/**
 * Created by cys on 2016/7/12
 */
public class ProfileCase extends IMBaseCase{

    public ProfileCase() {
        super(MAIN_THREAD, EXECUTOR_THREAD);
    }

    @Override
    public Observable createObservable(Object[] parameter) {
        return null;
    }
}

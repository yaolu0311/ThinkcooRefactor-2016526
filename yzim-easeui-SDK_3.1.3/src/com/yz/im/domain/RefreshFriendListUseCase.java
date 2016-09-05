package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.repository.IMRepository;
import com.yz.im.model.repository.impl.IMRepositoryImpl;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by cys on 2016/7/15
 */
public class RefreshFriendListUseCase extends IMBaseCase<String>{

    private IMRepository mRepository;

    public RefreshFriendListUseCase(Context context, Scheduler uiThread, Scheduler executorThread) {
        super(uiThread, executorThread);
        mRepository = new IMRepositoryImpl(context);
    }

    @Override
    public Observable createObservable(String... parameter) {
        return mRepository.loadFriendList(parameter[0],parameter[1]);
    }
}

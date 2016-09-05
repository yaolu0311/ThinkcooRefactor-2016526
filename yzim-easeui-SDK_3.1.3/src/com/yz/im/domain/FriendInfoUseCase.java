package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.repository.IMUserRepository;
import com.yz.im.model.repository.impl.IMUserRepositoryImpl;

import rx.Observable;

/**
 * Created by cys on 2016/7/22
 */
public class FriendInfoUseCase extends IMBaseCase<String> {

    private IMUserRepository mRepository;

    public FriendInfoUseCase(Context context) {
        super(MAIN_THREAD, EXECUTOR_THREAD);
        mRepository = new IMUserRepositoryImpl(context);
    }

    @Override
    public Observable createObservable(String... parameter) {
        return mRepository.loadFriendInfo(parameter[0]);
    }
}

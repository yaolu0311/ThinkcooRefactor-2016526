package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.entity.serverresponse.UserInfoResponse;
import com.yz.im.model.repository.IMUserRepository;
import com.yz.im.model.repository.impl.IMUserRepositoryImpl;

import rx.Observable;

/**
 * Created by cys on 2016/7/15
 */
public class LoadUserInfoUseCase extends IMBaseCase<String>{

    private IMUserRepository mRepository;

    public LoadUserInfoUseCase(Context context) {
        super(CURRENT_THREAD, CURRENT_THREAD);
        mRepository = new IMUserRepositoryImpl(context);
    }

    @Override
    public Observable<UserInfoResponse> createObservable(String... parameter) {
        return mRepository.loadUserInfo();
    }
}

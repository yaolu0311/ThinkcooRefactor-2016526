package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.repository.IMUserRepository;
import com.yz.im.model.repository.impl.IMUserRepositoryImpl;

import rx.Observable;

/**
 * Created by cys on 2016/7/28
 */
public class UpdateShieldStatusUseCase extends IMBaseCase<String> {

    private IMUserRepository mRepository;

    public UpdateShieldStatusUseCase(Context context) {
        super(MAIN_THREAD, EXECUTOR_THREAD);
        mRepository = new IMUserRepositoryImpl(context);
    }

    @Override
    public Observable createObservable(String... parameter) {
        return mRepository.updateStrangerShieldStatus(parameter[0], parameter[1]);
    }
}

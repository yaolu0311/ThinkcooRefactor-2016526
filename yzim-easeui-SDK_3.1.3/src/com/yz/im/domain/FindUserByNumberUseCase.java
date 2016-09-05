package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.repository.IMUserRepository;
import com.yz.im.model.repository.impl.IMUserRepositoryImpl;

import rx.Observable;

/**
 * Created by cys on 2016/8/1
 */
public class FindUserByNumberUseCase extends IMBaseCase<String>{

    private IMUserRepository mRepository;

    public FindUserByNumberUseCase(Context context) {
        super(MAIN_THREAD, EXECUTOR_THREAD);
        mRepository = new IMUserRepositoryImpl(context);
    }

    @Override
    public Observable createObservable(String... parameter) {
        return mRepository.findUserByNumber(parameter[0],parameter[1],parameter[2],parameter[3]);
    }
}

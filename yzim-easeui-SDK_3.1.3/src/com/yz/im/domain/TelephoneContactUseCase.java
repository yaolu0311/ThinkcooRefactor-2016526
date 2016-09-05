package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.repository.IMUserRepository;
import com.yz.im.model.repository.impl.IMUserRepositoryImpl;

import rx.Observable;

/**
 * Created by cys on 2016/8/2
 */
public class TelephoneContactUseCase extends IMBaseCase<Void>{

    private IMUserRepository mRepository;

    public TelephoneContactUseCase(Context context) {
        super(MAIN_THREAD, EXECUTOR_THREAD);
        mRepository = new IMUserRepositoryImpl(context);
    }

    @Override
    public Observable createObservable(Void... parameter) {
        return mRepository.getContactList();
    }
}

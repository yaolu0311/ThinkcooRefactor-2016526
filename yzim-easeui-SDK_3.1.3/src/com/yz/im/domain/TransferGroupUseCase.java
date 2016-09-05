package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.repository.IMGroupRepository;
import com.yz.im.model.repository.impl.IMGroupRepositoryImpl;

import rx.Observable;

/**
 * Created by cys on 2016/7/27
 */
public class TransferGroupUseCase extends IMBaseCase<String>{

    private IMGroupRepository mRepository;

    public TransferGroupUseCase( Context context) {
        super(MAIN_THREAD, EXECUTOR_THREAD);
        mRepository = new IMGroupRepositoryImpl(context);
    }

    @Override
    public Observable createObservable(String... parameter) {
        return mRepository.transferGroup(parameter[0], parameter[1]);
    }
}

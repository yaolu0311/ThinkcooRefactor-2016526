package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.repository.IMGroupRepository;
import com.yz.im.model.repository.impl.IMGroupRepositoryImpl;

import rx.Observable;

/**
 * Created by cys on 2016/7/25
 */
public class QuitGroupUseCase extends IMBaseCase<String> {

    private IMGroupRepository mGroupRepository;

    public QuitGroupUseCase(Context context) {
        super(MAIN_THREAD, EXECUTOR_THREAD);
        mGroupRepository = new IMGroupRepositoryImpl(context);
    }

    @Override
    public Observable createObservable(String... parameter) {
        return mGroupRepository.quitGroup(parameter[0], parameter[1], parameter[2]);
    }
}

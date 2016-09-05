package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.repository.IMGroupRepository;
import com.yz.im.model.repository.impl.IMGroupRepositoryImpl;

import rx.Observable;

/**
 * Created by cys on 2016/8/2
 */
public class SendJoinGroupReasonUseCase extends IMBaseCase<String>{

    private IMGroupRepository mRepository;

    public SendJoinGroupReasonUseCase(Context context) {
        super(MAIN_THREAD, EXECUTOR_THREAD);
        mRepository = new IMGroupRepositoryImpl(context);
    }

    @Override
    public Observable createObservable(String... parameter) {
        return mRepository.sendInviteReason(parameter[0], parameter[1]);
    }
}

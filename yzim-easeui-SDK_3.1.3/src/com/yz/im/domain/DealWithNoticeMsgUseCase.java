package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.repository.IMRepository;
import com.yz.im.model.repository.impl.IMRepositoryImpl;

import rx.Observable;

/**
 * Created by cys on 2016/8/5
 */
public class DealWithNoticeMsgUseCase extends IMBaseCase<String>{

    private IMRepository mRepository;

    public DealWithNoticeMsgUseCase(Context context) {
        super(MAIN_THREAD, EXECUTOR_THREAD);
        mRepository = new IMRepositoryImpl(context);
    }

    @Override
    public Observable createObservable(String... parameter) {
        return mRepository.dealWithNoticeMessage(parameter[0], parameter[1]);
    }
}

package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.repository.IMGroupRepository;
import com.yz.im.model.repository.impl.IMGroupRepositoryImpl;

import rx.Observable;

/**
 * Created by cys on 2016/8/1
 */
public class FindInterestGroupUseCase extends IMBaseCase<String> {

    private IMGroupRepository mRepository;

    public FindInterestGroupUseCase(Context context) {
        super(MAIN_THREAD, EXECUTOR_THREAD);
        mRepository = new IMGroupRepositoryImpl(context);
    }

    @Override
    public Observable createObservable(String... parameter) {
        return mRepository.findInterestGroup(parameter[0], parameter[1], parameter[2], parameter[3]);
    }
}

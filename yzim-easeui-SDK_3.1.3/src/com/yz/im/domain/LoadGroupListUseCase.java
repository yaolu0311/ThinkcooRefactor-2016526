package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.repository.IMRepository;
import com.yz.im.model.repository.impl.IMRepositoryImpl;

import rx.Observable;

/**
 * Created by cys on 2016/7/15
 */
public class LoadGroupListUseCase extends IMBaseCase<String>{
    private IMRepository mRepository;

    public LoadGroupListUseCase(Context context) {
        super(CURRENT_THREAD, CURRENT_THREAD);
        mRepository = new IMRepositoryImpl(context);
    }

    @Override
    public Observable createObservable(String... parameter) {
        return mRepository.loadGroupList(parameter[0]);
    }
}

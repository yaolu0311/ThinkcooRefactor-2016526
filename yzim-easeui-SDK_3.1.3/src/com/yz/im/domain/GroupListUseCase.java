package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.repository.IMGroupRepository;
import com.yz.im.model.repository.impl.IMGroupRepositoryImpl;

import rx.Observable;

/**
 * Created by cys on 2016/7/22
 */
public class GroupListUseCase extends IMBaseCase<Object>{

    private IMGroupRepository mGroupRepository;

    public GroupListUseCase(Context context) {
        super(MAIN_THREAD, EXECUTOR_THREAD);
        mGroupRepository = new IMGroupRepositoryImpl(context);
    }

    @Override
    public Observable createObservable(Object... parameter) {
        return mGroupRepository.queryGroupList();
    }
}

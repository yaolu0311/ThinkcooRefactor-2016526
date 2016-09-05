package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.repository.IMGroupRepository;
import com.yz.im.model.repository.impl.IMGroupRepositoryImpl;

import rx.Observable;

/**
 * Created by cys on 2016/7/22
 */
public class GroupInfoUseCase extends IMBaseCase<String>{

    private IMGroupRepository mGroupRepository;

    public GroupInfoUseCase(Context context) {
        super(MAIN_THREAD, EXECUTOR_THREAD);
        mGroupRepository = new IMGroupRepositoryImpl(context);
    }

    @Override
    public Observable createObservable(String... parameter) {
        return mGroupRepository.loadGroupInfo(parameter[0]);
    }
}

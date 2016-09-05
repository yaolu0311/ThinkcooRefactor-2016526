package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.repository.IMUserRepository;
import com.yz.im.model.repository.impl.IMUserRepositoryImpl;

import rx.Observable;

/**
 * Created by cys on 2016/7/25
 */
public class DeleteFriendUseCase extends IMBaseCase<String>{

    private Context mContext;
    private IMUserRepository mRepository;

    public DeleteFriendUseCase(Context context) {
        super(MAIN_THREAD, EXECUTOR_THREAD);
        mContext = context;
        mRepository = new IMUserRepositoryImpl(context);
    }

    @Override
    public Observable createObservable(String... parameter) {
        return mRepository.deleteFriend(parameter[0], parameter[1]);
    }
}

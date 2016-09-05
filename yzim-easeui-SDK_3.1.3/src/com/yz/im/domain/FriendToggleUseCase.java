package com.yz.im.domain;

import android.content.Context;

import com.yz.im.model.repository.IMUserRepository;
import com.yz.im.model.repository.impl.IMUserRepositoryImpl;

import rx.Observable;

/**
 * Created by cys on 2016/7/25
 */
public class FriendToggleUseCase extends IMBaseCase<String> {

    private Context mContext;
    private IMUserRepository mRepository;

    public FriendToggleUseCase(Context context) {
        super(MAIN_THREAD, EXECUTOR_THREAD);
        mContext = context;
        mRepository = new IMUserRepositoryImpl(mContext);
    }

    @Override
    public Observable createObservable(String... parameter) {
        return mRepository.updateFriendToggleStatus(parameter[0],parameter[1],parameter[2],parameter[3]);
    }
}

package com.thinkcoo.mobile.domain.user;


import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/5.
 */
public class UploadPhotoUseCase extends UseCase<String> {


    UserRepository mUserRepository;

    public UploadPhotoUseCase(Scheduler mUiThread, Scheduler mExecutorThread, UserRepository userRepository) {
        super(mUiThread, mExecutorThread);
        mUserRepository = userRepository;
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable(String... q) {
        return mUserRepository.uploadPhoto(q[0]);
    }
}

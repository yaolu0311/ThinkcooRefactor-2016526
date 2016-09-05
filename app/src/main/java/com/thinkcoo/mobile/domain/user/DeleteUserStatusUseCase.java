package com.thinkcoo.mobile.domain.user;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.model.repository.UserRepository;
import com.thinkcoo.mobile.model.repository.UserStatusRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/5/31.
 */
public class DeleteUserStatusUseCase extends UseCase<UserStatus> {

    UserStatusRepository mUserStatusRepository;

    @Inject
    public DeleteUserStatusUseCase(
            @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
            @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,
            UserStatusRepository userStatusRepository) {
        super(mUiThread, mExecutorThread);
        this.mUserStatusRepository = userStatusRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(UserStatus... userStatus) {
        return mUserStatusRepository.deleteUserStatus(userStatus[0]);
    }
}

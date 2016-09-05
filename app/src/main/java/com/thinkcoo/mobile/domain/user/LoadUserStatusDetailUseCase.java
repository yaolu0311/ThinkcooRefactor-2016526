package com.thinkcoo.mobile.domain.user;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.model.repository.UserStatusRepository;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by Administrator on 2016/6/2.
 */
public class LoadUserStatusDetailUseCase extends UseCase<Object> {//FIXME 为什么USER STATUS 不支持呢

    UserStatusRepository mUserRepository;

    @Inject
    public LoadUserStatusDetailUseCase(
            @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
            @Named(ApplicationModule.EXECUTOR_THREAD_NAMED)  Scheduler mExecutorThread,
            UserStatusRepository mUserRepository) {
        super(mUiThread, mExecutorThread);
        this.mUserRepository = mUserRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mUserRepository.loadUserStatusDetail((UserStatus) q[0]);
    }
}


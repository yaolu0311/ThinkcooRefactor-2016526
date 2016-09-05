package com.thinkcoo.mobile.injector.modules;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.domain.user.AddUserHarvestUseCase;
import com.thinkcoo.mobile.domain.user.AddUserStatusUseCase;
import com.thinkcoo.mobile.domain.user.DeleteUserStatusUseCase;
import com.thinkcoo.mobile.domain.user.GetUserMainInfoUseCase;
import com.thinkcoo.mobile.domain.user.GetUserStatusListUseCase;
import com.thinkcoo.mobile.domain.user.LoadEducationStatusDetailUseCase;
import com.thinkcoo.mobile.domain.user.LoadFullTimeStatusDetailUseCase;
import com.thinkcoo.mobile.domain.user.LoadPartTimeStatusDetailUseCase;
import com.thinkcoo.mobile.domain.user.LoadTrainStatusDetailUseCase;
import com.thinkcoo.mobile.domain.user.LoadUserHarvestDetailUseCase;
import com.thinkcoo.mobile.domain.user.LoadUserStatusDetailUseCase;
import com.thinkcoo.mobile.domain.user.ToggleUserStatusUseCase;
import com.thinkcoo.mobile.domain.user.UpdateUserHarvestUseCase;
import com.thinkcoo.mobile.domain.user.UpdateUserStatusUseCase;
import com.thinkcoo.mobile.domain.user.UploadPhotoUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.repository.UserRepository;
import com.thinkcoo.mobile.model.repository.UserStatusRepository;
import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/3/22.
 */
@Module
public class UserModule {


    @ActivityScope
    @Provides
    @Named("addUserHarvest")
    UseCase provideAddUserHarvestUseCase(UserRepository userRepository, @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread) {
        return new AddUserHarvestUseCase(mUiThread, mExecutorThread, userRepository);
    }

    @ActivityScope
    @Provides
    @Named("loadUserHarvestDetail")
    UseCase provideLoadUserHarvestDetailUseCase(UserRepository userRepository, @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread) {
        return new LoadUserHarvestDetailUseCase(mUiThread, mExecutorThread, userRepository);
    }

    @ActivityScope
    @Provides
    @Named("updateUserHarvest")
    UseCase provideUpdateUserHarvestUseCase(UserRepository userRepository, @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread) {
        return new UpdateUserHarvestUseCase(mUiThread, mExecutorThread, userRepository);
    }

    @ActivityScope
    @Provides
    UseCase provideGetUserMainInfoUseCase(UserRepository userRepository, @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread) {
        return new GetUserMainInfoUseCase(mUiThread, mExecutorThread, userRepository);
    }

    @ActivityScope
    @Provides
    ToggleUserStatusUseCase provideGetToggleUserStatusUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, UserStatusRepository userStatusRepository) {
        return new ToggleUserStatusUseCase(mUiThread, mExecutorThread, userStatusRepository);
    }

    @ActivityScope
    @Provides
    GetUserStatusListUseCase provideGetUserStatusListUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, UserStatusRepository userStatusRepository) {
        return new GetUserStatusListUseCase(mUiThread, mExecutorThread, userStatusRepository);
    }

    @ActivityScope
    @Provides
    DeleteUserStatusUseCase provideDeleteUserStatusUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, UserStatusRepository userStatusRepository) {
        return new DeleteUserStatusUseCase(mUiThread, mExecutorThread, userStatusRepository);
    }

    @ActivityScope
    @Provides
    @Named("addUserStatus")
    UseCase provideAddUserStatusUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, UserStatusRepository userStatusRepository) {
        return new AddUserStatusUseCase(mUiThread, mExecutorThread, userStatusRepository);
    }

    @ActivityScope
    @Provides
    @Named("updateUserStatus")
    UseCase provideUpdateUserStatusUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, UserStatusRepository userStatusRepository) {
        return new UpdateUserStatusUseCase(mUiThread, mExecutorThread, userStatusRepository);
    }

    @ActivityScope
    @Provides
    UseCase provideLoadEducationStatusDetailUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, UserStatusRepository userStatusRepository) {
        return new LoadEducationStatusDetailUseCase(mUiThread, mExecutorThread, userStatusRepository);
    }

    @ActivityScope
    @Provides
    UseCase provideLoadTrainStatusDetailUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, UserStatusRepository userStatusRepository) {
        return new LoadTrainStatusDetailUseCase(mUiThread, mExecutorThread, userStatusRepository);
    }

    @ActivityScope
    @Provides
    UseCase provideLoadFullTimeStatusDetailUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, UserStatusRepository userStatusRepository) {
        return new LoadFullTimeStatusDetailUseCase(mUiThread, mExecutorThread, userStatusRepository);
    }

    @ActivityScope
    @Provides
    UseCase provideLoadPartTimeStatusDetailUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, UserStatusRepository userStatusRepository) {
        return new LoadPartTimeStatusDetailUseCase(mUiThread, mExecutorThread, userStatusRepository);
    }

    @ActivityScope
    @Provides
    @Named("loadUserStatusDetail")
    UseCase provideLoadUserStatusDetailUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, UserStatusRepository userStatusRepository) {
        return new LoadUserStatusDetailUseCase(mUiThread, mExecutorThread, userStatusRepository);
    }

    @ActivityScope
    @Provides
    UploadPhotoUseCase provideUploadPhotoUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,UserRepository userRepository){
        return new UploadPhotoUseCase(mUiThread,mExecutorThread,userRepository);
    }

}

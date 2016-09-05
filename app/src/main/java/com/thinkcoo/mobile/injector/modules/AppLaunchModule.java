package com.thinkcoo.mobile.injector.modules;

import com.thinkcoo.mobile.domain.applaunch.CompatOldVersionDbUseCase;
import com.thinkcoo.mobile.domain.applaunch.CopyDataDictionaryDbFileUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.repository.AppLaunchRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by robert on 2016/5/22.
 */
@Module
public class AppLaunchModule {

    @ActivityScope
    @Provides
    CopyDataDictionaryDbFileUseCase provideCopyDataDictionaryDbFileUseCase(AppLaunchRepository appLaunchRepository,@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED)Scheduler mExecutorThread){
        return new CopyDataDictionaryDbFileUseCase(mUiThread,mExecutorThread,appLaunchRepository);
    }

    @ActivityScope
    @Provides
    CompatOldVersionDbUseCase provideCompatOldVersionDbUseCase(AppLaunchRepository appLaunchRepository,@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED)Scheduler mExecutorThread){
        return  new CompatOldVersionDbUseCase(mUiThread,mExecutorThread,appLaunchRepository);
    }

}

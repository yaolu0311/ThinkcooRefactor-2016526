package com.thinkcoo.mobile.injector.modules;

import com.thinkcoo.mobile.domain.banner.LoadBannerListUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.repository.BannerRepository;
import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/7/18.
 */
@Module
public class BannerModule {

    @Provides
    @ActivityScope
    LoadBannerListUseCase provideLoadBannerListUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread,@Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler workThread , BannerRepository bannerRepository){
        return new LoadBannerListUseCase(uiThread,workThread,bannerRepository);
    }
}

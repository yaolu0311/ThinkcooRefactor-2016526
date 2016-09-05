package com.thinkcoo.mobile.injector.components;

import android.content.Context;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.cache.AddressCache;
import com.thinkcoo.mobile.model.cache.DataDictionaryCache;
import com.thinkcoo.mobile.model.cache.LoginUserCache;
import com.thinkcoo.mobile.model.cache.UserCacheProviders;
import com.thinkcoo.mobile.model.entity.License;
import com.thinkcoo.mobile.model.repository.AccountRepository;
import com.thinkcoo.mobile.model.repository.AppLaunchRepository;
import com.thinkcoo.mobile.model.repository.BaiduRepository;
import com.thinkcoo.mobile.model.repository.BannerRepository;
import com.thinkcoo.mobile.model.repository.DataDictionaryRepository;
import com.thinkcoo.mobile.model.repository.GetJobRepository;
import com.thinkcoo.mobile.model.repository.LocationRepository;
import com.thinkcoo.mobile.model.repository.ResourceRepository;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;
import com.thinkcoo.mobile.model.repository.TradeRepository;
import com.thinkcoo.mobile.model.repository.UserRepository;
import com.thinkcoo.mobile.model.repository.UserStatusRepository;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseLceActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;
import com.thinkcoo.mobile.presentation.views.service.UploadLocationOnRockService;
import com.yz.im.IMHelper;

import javax.inject.Named;
import javax.inject.Singleton;
import dagger.Component;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/3/21.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    /** inject to BaseActivity */
    void inject(BaseActivity baseActivity);
    void inject(BaseSimpleActivity baseSimpleActivity);
    //void inject(BaseLceActivity baseLceActivity);
    void inject(UploadLocationOnRockService uploadLocationOnRockService);

    /** global App Context */
    /** Exposed to sub-graphs. */
    Context context();
    //Context provideActivityContext();
    /** Threads **/
    @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler provideExecutorThread();
    @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler provideUiThread();
    @Named(ApplicationModule.EXECUTOR_SINGLE_THREAD_NAMED) Scheduler provideSigleThreadExecutor();
    @Named("user") License provideUserLicense();
    /** Repositorys **/
    AccountRepository provideAccountRepository();
    AppLaunchRepository provideAppLaunchRepository();
    UserRepository provideUserRepository();
    UserStatusRepository provideUserStatusRepository();
    DataDictionaryRepository provideDataDictionaryRepository();
    ScheduleRepository provideScheduleScheduleRepository();
    ResourceRepository provideResourceRepository();
    LocationRepository provideLocationRepository();
    TradeRepository provideTradeRepository();
    BannerRepository provideBannerRepository();
    BaiduRepository provideBaiduRepository();
    GetJobRepository provideGetJobRepository();
    IMHelper provideImHelper();
    /** LoginUserCache **/
    LoginUserCache provideLoginUserCache();
    /** Cache **/
    UserCacheProviders provideUserCahceProvides();
    AddressCache provideAddressCache();
    DataDictionaryCache provideDataDictionaryCache();

}

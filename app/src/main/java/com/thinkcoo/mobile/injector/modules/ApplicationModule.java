package com.thinkcoo.mobile.injector.modules;

import android.content.Context;

import com.baidu.location.LocationClient;
import com.example.administrator.publicmodule.model.db.base.DbManagerProvider;
import com.example.administrator.publicmodule.model.rest.ApiFactory;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.ThinkcooApp;
import com.thinkcoo.mobile.model.cache.AddressCache;
import com.thinkcoo.mobile.model.cache.AddressCacheImpl;
import com.thinkcoo.mobile.model.cache.DataDictionaryCache;
import com.thinkcoo.mobile.model.cache.DataDictionaryCacheImpl;
import com.thinkcoo.mobile.model.cache.LoginUserCache;
import com.thinkcoo.mobile.model.cache.ScheduleCacheProvides;
import com.thinkcoo.mobile.model.cache.TradeCacheProvides;
import com.thinkcoo.mobile.model.cache.UserCacheProviders;
import com.thinkcoo.mobile.model.db.BannerDao;
import com.thinkcoo.mobile.model.db.DataDictionaryDao;
import com.thinkcoo.mobile.model.db.UserDao;
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
import com.thinkcoo.mobile.model.repository.impl.AccountRepositoryImpl;
import com.thinkcoo.mobile.model.repository.impl.AppLaunchRepositoryImpl;
import com.thinkcoo.mobile.model.repository.impl.BaiduRepositoryImpl;
import com.thinkcoo.mobile.model.repository.impl.BannerRepositoryImpl;
import com.thinkcoo.mobile.model.repository.impl.DataDictionaryRepositoryImpl;
import com.thinkcoo.mobile.model.repository.impl.GetJobRepositoryImpl;
import com.thinkcoo.mobile.model.repository.impl.LocationRepositoryImpl;
import com.thinkcoo.mobile.model.repository.impl.ResourceRepositoryImpl;
import com.thinkcoo.mobile.model.repository.impl.ScheduleRepositoryImpl;
import com.thinkcoo.mobile.model.repository.impl.TradeRepositoryImpl;
import com.thinkcoo.mobile.model.repository.impl.UserRepositoryImpl;
import com.thinkcoo.mobile.model.repository.impl.UserStatusRepositoryImpl;
import com.thinkcoo.mobile.model.rest.apis.AccountApi;
import com.thinkcoo.mobile.model.rest.apis.BaiduApi;
import com.thinkcoo.mobile.model.rest.apis.BannerApi;
import com.thinkcoo.mobile.model.rest.apis.GetJobApi;
import com.thinkcoo.mobile.model.rest.apis.ScheduleApi;
import com.thinkcoo.mobile.model.rest.apis.TradeApi;
import com.thinkcoo.mobile.model.rest.apis.UserApi;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.views.activitys.base.Navigator;
import com.thinkcoo.mobile.presentation.views.dialog.GlobalToast;
import com.thinkcoo.mobile.utils.FieldCheckUtil;
import com.thinkcoo.mobile.utils.PhotoUriUtils;
import com.yz.im.IMHelper;
import com.yzkj.android.orm.DbManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Robert.yao on 2016/3/21.
 */
@Module
public class ApplicationModule {

    public static final String TAG  = "ApplicationModule";

    public static final String UI_THREAD_NAMED = "ui_thread";
    public static final String EXECUTOR_THREAD_NAMED = "executor_thread";
    public static final String EXECUTOR_SINGLE_THREAD_NAMED = "executor_single_thread";

    private final ThinkcooApp application;

    public ApplicationModule(ThinkcooApp application) {
        this.application = application;
    }

    /**
     * 提供全局唯一的应用程序Context对象
     * @return
     */
    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ApiFactory provideApiFactory(){
        return application.getApiFactory();
    }

    @Provides
    @Singleton
    DbManagerProvider provideDbManagerProvider(){
        return application.getDbManagerProvider();
    }

    @Provides
    @Singleton
    LocationClient provideLocationClient(){
        return application.getLocationClient();
    }

    /**
     * 提供全局唯一的导航器
     */
    @Provides
    @Singleton
    Navigator provideNavigator(){
        return new Navigator();
    }

    /**
     * 提供全局唯一的Toast工具对象
     */
    @Provides
    @Singleton
    GlobalToast provideGlobalToast(Context context){
        return new GlobalToast(context);
    }
    /**
     * 提供全局唯一的图片地址工具
     * @return
     */
    @Provides
    @Singleton
    PhotoUriUtils providePhotoUriUtils(){
        return new PhotoUriUtils();
    }

    /**
     * 提供全局唯一的 RxAndroid Scheduler（工作线程）
     * @return
     */
    @Provides @Named(EXECUTOR_THREAD_NAMED) @Singleton
    Scheduler provideExecutorThread() {
        return Schedulers.io();
    }

    /**
     * 提供全局唯一的单线程队列式的线程框架
     */
    @Provides @Named(EXECUTOR_SINGLE_THREAD_NAMED) @Singleton
    Scheduler provideSignleThreadExecutor(){
        return Schedulers.from(Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"Rx SingleThread Schedule");
            }
        }));
    }

    /**
     * 提供全局唯一的 RxAndroid Scheduler（UI线程）
     * @return
     */
    @Provides  @Named(UI_THREAD_NAMED) @Singleton
    Scheduler provideUiThread() {
        return AndroidSchedulers.mainThread();
    }

    /**             Cache                   **/
    @Provides @Singleton
    LoginUserCache provideLoginUserCache(){
        return application.getLoginUserCache();
    }

    @Provides @Singleton
    AddressCache provideAddressCache(DataDictionaryDao dataDictionaryDao){
        return new AddressCacheImpl(dataDictionaryDao);
    }

    @Provides @Singleton
    DataDictionaryCache provideDataDictionaryCache(DataDictionaryDao dataDictionaryDao){
        return new DataDictionaryCacheImpl(dataDictionaryDao);
    }

    /** Im **/
    @Provides
    @Singleton
    IMHelper provideIMHelper(){
        return IMHelper.getInstance();
    }

    /**
     * 提供全局唯一的错误消息工厂
     * @return
     */
    @Provides @Singleton
    ErrorMessageFactory provideErrorMessageFactory(){
        return new ErrorMessageFactory(application.getApplicationContext());
    }

    @Provides @Singleton @Named("user")
    License provideUserLicense(){
        String userLicenseUrl = application.getResources().getString(R.string.html_protocol);
        return  new License(userLicenseUrl,"");
    }

    /********************************* Db manager and dao *******************************************/

    @Provides @Singleton @Named("public")
    DbManager providePublicDbManager(DbManagerProvider dbManagerProvider){
        return dbManagerProvider.getPublicDbManager();
    }
    @Provides @Singleton @Named("dd")
    DbManager provideDataDictionaryDbManager(DbManagerProvider dbManagerProvider){
        return dbManagerProvider.getDataDictionaryDbManager();
    }
    @Provides @Singleton @Named("user")
    DbManager provideUserDbManager(DbManagerProvider dbManagerProvider){
        return dbManagerProvider.getUserDbManager();
    }

    /** DAO **/

    @Provides @Singleton
    UserDao provideUserDao(UserDao userDao){
        return userDao;
    }

    /********************************** Repository and Api **********************************/

    @Singleton
    @Provides
    AppLaunchRepository provideAppLaunchRepository(AppLaunchRepositoryImpl appLaunchRepository){
        return  appLaunchRepository;
    }
    @Singleton
    @Provides
    AccountApi provideAccountApi(){
        return application.getApiFactory().createApiByClass(AccountApi.class, ApiFactory.LOGIN_PREFIX);
    }
    @Singleton
    @Provides
    UserApi provideUserApi(){
        return application.getApiFactory().createApiByClass(UserApi.class, ApiFactory.PERSONAL_PREFIX);
    }
    @Singleton
    @Provides
    ScheduleApi provideScheduleApi(){
        return application.getApiFactory().createApiByClass(ScheduleApi.class,ApiFactory.COURSE_PREFIX);
    }
    @Singleton
    @Provides
    BannerApi provideBannerApi(){
        return application.getApiFactory().createApiByClass(BannerApi.class,ApiFactory.LOGIN_PREFIX);
    }
    @Singleton
    @Provides
    TradeApi provideTradeApi(){
        return application.getApiFactory().createApiByClass(TradeApi.class,ApiFactory.TRADE_PREFIX);
    }

    @Singleton
    @Provides
    BaiduApi provideBaiduApi(){
        return application.getApiFactory().createApiByClass(BaiduApi.class,ApiFactory.BAIDUAPI);
    }

    @Singleton
    @Provides
    GetJobApi provideGetJobApi(){
        return application.getApiFactory().createApiByClass(GetJobApi.class,ApiFactory.GETJOB_PREFIX);
    }

    @Singleton
    @Provides
    AccountRepository provideAccountRepository(AccountRepositoryImpl accountRepository){
        return accountRepository;
    }
    @Singleton
    @Provides
    UserRepository provideUserRepository(UserRepositoryImpl userRepository){
        return userRepository;
    }
    @Singleton
    @Provides
    LocationRepository provideLocationRepository(LocationRepositoryImpl locationRepository){
        return locationRepository;
    }
    @Singleton
    @Provides
    TradeRepository provideTradeRepository(TradeRepositoryImpl tradeRepository){
        return tradeRepository;
    }

    @Singleton
    @Provides
    BaiduRepository provideBaiduRepository(BaiduRepositoryImpl baiduRepository){
        return baiduRepository;
    }

    @Singleton
    @Provides
    GetJobRepository provideGetJobRepository(GetJobRepositoryImpl getJobRepository){
        return getJobRepository;
    }

    @Singleton
    @Provides
    FieldCheckUtil provideFieldCheckUtil(){
        return new FieldCheckUtil();
    }

    @Singleton
    @Provides
    UserStatusRepository provideUserStatusRepository(UserStatusRepositoryImpl userStatusRepository){
        return userStatusRepository;
    }

    @Singleton
    @Provides
    DataDictionaryRepository provideDataDictionaryRepository(DataDictionaryRepositoryImpl dataDictionaryRepository){
        return dataDictionaryRepository;
    }

    @Singleton
    @Provides
    ScheduleRepository provideScheduleRepository(ScheduleRepositoryImpl scheduleRepository){
        return scheduleRepository;
    }

    @Singleton
    @Provides
    ResourceRepository provideResourceRepository(ResourceRepositoryImpl resourceRepository){
        return resourceRepository;
    }

    @Singleton
    @Provides
    BannerRepository provideBannerRepository(BannerRepositoryImpl bannerRepository){
        return bannerRepository;
    }

    /******************************** RX Cache **************************************************/


    @Singleton
    @Provides
    UserCacheProviders provideUserCacheProvides(){
        return new RxCache.Builder()
                .persistence(application.getFilesDir(),new GsonSpeaker())
                .using(UserCacheProviders.class);
    }

    @Singleton
    @Provides
    ScheduleCacheProvides provideScheduleCache(){
        return new RxCache.Builder()
                .persistence(application.getFilesDir(),new GsonSpeaker())
                .using(ScheduleCacheProvides.class);
    }

    @Singleton
    @Provides
    TradeCacheProvides provideTradeCacheProvides(){
        return new RxCache.Builder()
                .persistence(application.getFilesDir(),new GsonSpeaker())
                .using(TradeCacheProvides.class);
    }

}

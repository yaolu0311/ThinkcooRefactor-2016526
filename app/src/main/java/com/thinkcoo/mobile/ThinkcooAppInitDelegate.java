package com.thinkcoo.mobile;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.administrator.publicmodule.model.db.base.DbManagerProvider;
import com.example.administrator.publicmodule.model.rest.ApiFactory;
import com.example.administrator.publicmodule.model.rest.ApiFactoryImpl;
import com.example.administrator.publicmodule.model.rest.encrypt.UrlDecodeUtil;
import com.example.administrator.publicmodule.util.PinyinHelper;
import com.thinkcoo.mobile.injector.components.ApplicationComponent;
import com.thinkcoo.mobile.injector.components.DaggerApplicationComponent;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.cache.LoginUserCache;
import com.thinkcoo.mobile.model.cache.LoginUserCacheImpl;
import com.thinkcoo.mobile.utils.HxAppKeyUtils;
import com.yz.im.IMHelper;
import com.yzkj.android.orm.x;

/**
 * Created by Robert.yao on 2016/5/16.
 */
public class ThinkcooAppInitDelegate {

    private ApplicationComponent mApplicationComponent;
    private ThinkcooApp mContext;
    private ApiFactory mApiFactory;
    private DbManagerProvider mDbManagerProvider;
    private LoginUserCache mLoginUserCache;
    private LocationClient mLocationClient;

    public ThinkcooAppInitDelegate(ThinkcooApp context) {
        this.mContext = context;
    }

    public void init(){

        initApplicationComponent();
        initOrm();
        initDbManagerProvider();
        initLoginUserCache();
        initApiEncryptUtil();
        initApiFactory();
        initJpush();
        initHx();
        initXlog();
        initLeakCanary();
        initFresco();
        initBaiduMap();
        initPinyinHelper();
    }

    private void initPinyinHelper() {
        PinyinHelper.getInstance();
    }

    private void initBaiduMap() {
        mLocationClient = new LocationClient(mContext);
        mLocationClient.setLocOption(getBaiduMapLocation());
    }

    private LocationClientOption getBaiduMapLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        return option;
    }

    private void initLoginUserCache() {
        mLoginUserCache = new LoginUserCacheImpl();
    }

    private void initOrm() {
        x.Ext.init(mContext);
        x.Ext.setDebug(true);
    }

    private void initDbManagerProvider() {
        mDbManagerProvider = DbManagerProvider.getInstance();
    }

    private void initApiEncryptUtil() {
        UrlDecodeUtil.init(mContext);
    }

    private void initApiFactory() {
        mApiFactory = ApiFactoryImpl.getInstance();
    }

    /**
     * 初始化应用程序依赖组件
     */
    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(mContext)).build();
    }

    /**
     * 初始化内存监控
     */
    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {
            //LeakCanary.install(this);
        }
    }
    /**
     * 初始化文件日志框架
     */
    private void initXlog() {

    }
    /**
     * 初始化环信
     */
    private void initHx() {
        IMHelper.getInstance().init(mContext, HxAppKeyUtils.getAppKeyByLoginApiAddress(ApiFactoryImpl.WORK_LOGINPREFIX));
    }
    /**
     * 初始化极光推送
     */
    private void initJpush() {

    }

    /**
     * 初始化加载图片库
     */
    private void initFresco() {
       // Fresco.initialize(mContext);
    }

    public ApplicationComponent getmApplicationComponent() {
        return mApplicationComponent;
    }

    public ApiFactory getApiFactory() {
        return mApiFactory;
    }

    public DbManagerProvider getDbManagerProvider() {
        return mDbManagerProvider;
    }
    public LoginUserCache getLoginUserCache() {
        return mLoginUserCache;
    }
    public LocationClient getLocationClient() {
        return mLocationClient;
    }
}

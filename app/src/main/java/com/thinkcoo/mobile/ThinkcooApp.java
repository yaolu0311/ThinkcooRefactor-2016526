package com.thinkcoo.mobile;

import android.support.multidex.MultiDexApplication;
import com.baidu.location.LocationClient;
import com.example.administrator.publicmodule.model.db.base.DbManagerProvider;
import com.example.administrator.publicmodule.model.rest.ApiFactory;
import com.thinkcoo.mobile.injector.components.ApplicationComponent;
import com.thinkcoo.mobile.model.cache.LoginUserCache;
import com.thinkcoo.mobile.utils.OsUtils;

/**
 * Created by Administrator on 2016/3/16.
 */
public class ThinkcooApp extends MultiDexApplication{

    public static final String TAG = "ThinkcooApp";

    private ThinkcooAppInitDelegate mThinkcooAppInitDelegate;

    private static ThinkcooApp sAppInstance;

    public static ThinkcooApp getsAppInstance() {
        return sAppInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInit();
    }
    private void appInit() {
        if (isFirstCreate()){
            mThinkcooAppInitDelegate = new ThinkcooAppInitDelegate(this);
            mThinkcooAppInitDelegate.init();
            sAppInstance = this;
        }
    }
    public ApplicationComponent getApplicationComponent() {
        return mThinkcooAppInitDelegate.getmApplicationComponent();
    }
    public ApiFactory getApiFactory() {
        return mThinkcooAppInitDelegate.getApiFactory();
    }
    public DbManagerProvider getDbManagerProvider() {
        return mThinkcooAppInitDelegate.getDbManagerProvider();
    }
    public LoginUserCache getLoginUserCache(){
        return mThinkcooAppInitDelegate.getLoginUserCache();
    }
    public LocationClient getLocationClient() {
        return mThinkcooAppInitDelegate.getLocationClient();
    }
    private boolean isFirstCreate(){
        String processName = OsUtils.getProcessName(this,android.os.Process.myPid());
        return processName.equals(this.getPackageName());
    }
}
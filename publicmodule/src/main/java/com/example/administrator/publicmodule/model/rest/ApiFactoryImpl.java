package com.example.administrator.publicmodule.model.rest;

import android.content.Context;
import android.text.TextUtils;
import com.example.administrator.publicmodule.entity.BaseStringResponse;
import com.example.administrator.publicmodule.model.rest.deserializers.BaseStringResponseDes;
import com.example.administrator.publicmodule.model.rest.encrypt.HttpEncryptInterceptor;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Robert.yao on 2016/3/25.
 */
public class ApiFactoryImpl implements ApiFactory {

    public static final String TAG = "ApiFactoryImpl";
    public static final String THINKCOO_HTTP_LOG_TAG = "thinkcoo_http";

    public static String LOGINPREFIX_PUBLIC_UAT = "http://117.34.109.231/";//UAT登录前缀
    public static String LOGINPREFIX_PUBLIC = "http://www.thinkcoo.com/";//线上登录前缀
    public static String LOGINPREFIX_PRIVATE = "http://10.21.4.43:8080/";//内网登录前缀
    public static String LOGINPREFIX_TEEST = "http://10.21.4.115:8082/";//test
    public static String LOGINPREFIX_LIUYI = "http://10.21.4.108:8081/";

    public static String BAIDU_API = "http://api.map.baidu.com/";

    @Override
    public <T> T createApiByClass(Class<T> accountApiClass, String webNodeKey) {
        T t = createApiByWebNodeKey(webNodeKey, accountApiClass);
        return t;
    }

    /**
     * 切换网络环境请修改以下变量
     */
    public static String WORK_LOGINPREFIX = LOGINPREFIX_PUBLIC_UAT + "yingzi-entry-mobile/";


    private Map<Class, WeakReference<Object>> mApiCache;
    private OkHttpClient mOkHttpClient;
    private WebNodeMap mWebNodeMap;

    public static volatile ApiFactoryImpl instance;

    public static ApiFactoryImpl getInstance(Context context) {
        if (instance == null) {
            synchronized (ApiFactoryImpl.class) {
                if (instance == null) {
                    instance = new ApiFactoryImpl(context);
                }
            }
        }
        return instance;
    }

    private ApiFactoryImpl(Context context) {
        initWebNodeMap(context);
        initApiCache();
        initOkHttpClient();
    }

    private void initWebNodeMap(Context context) {
        mWebNodeMap = new WebNodeMap(context);
        putLoginApiWebNodeInMap();
    }

    private void putLoginApiWebNodeInMap() {
        mWebNodeMap.put(LOGIN_PREFIX, WORK_LOGINPREFIX);
    }

    private void initApiCache() {
        mApiCache = new HashMap<>();
    }

    private void initOkHttpClient() {

        HttpLoggingInterceptor logginInterceptor = new HttpLoggingInterceptor();
        logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        HttpEncryptInterceptor encryptInterceptor = new HttpEncryptInterceptor();
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.addInterceptor(logginInterceptor);
        builder.addInterceptor(encryptInterceptor);
        mOkHttpClient = builder.build();
    }

    private <T> T createApiByWebNodeKey(String key, Class<T> tClass) {
        ThinkcooLog.d(TAG, "=== 根据key : " + key + "请求生成 " + tClass.getName() + "实例 ===");
        if (TextUtils.isEmpty(key)) {
            throw new NullPointerException("webNodeKey is null");
        }
        T t = (T) getApiFromCache(tClass);
        if (null == t) {
            t = (T) createApi(key, tClass);
            cacheToMap(tClass, t);
        }
        return t;
    }

    private void cacheToMap(Class apiClass, Object t) {
        WeakReference<Object> apiWeakReference = new WeakReference<>(t);
        mApiCache.put(apiClass, apiWeakReference);
    }

    private Object createApi(String key, Class aClass) {
        String baseUrl;
        if (BAIDUAPI.equals(key)) {
            baseUrl = BAIDU_API;
        } else {
            baseUrl = getBaseUrl(key);
        }
        ThinkcooLog.d(TAG, "=== 根据key : " + key + " 获得baseUrl : " + baseUrl + " ===");
        Retrofit retrofit = newRetrofitInstanceByBaseUrl(baseUrl);
        return retrofit.create(aClass);
    }

    private Retrofit newRetrofitInstanceByBaseUrl(String baseUrl) {

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getConfigGson()))
                .client(getConfigOkHttpClient())
                .build();
    }

    private Gson getConfigGson() {
        return new GsonBuilder()
                .registerTypeAdapter(new TypeToken<BaseStringResponse>() {
                        }.getType(),
                        new BaseStringResponseDes())
                .create();
    }

    private OkHttpClient getConfigOkHttpClient() {
        return mOkHttpClient;
    }

    private String getBaseUrl(String key) {
        return mWebNodeMap.get(key);
    }

    private Object getApiFromCache(Class apiClass) {
        WeakReference<Object> apiBaseWeakReference = mApiCache.get(apiClass);
        if (null != apiBaseWeakReference)
            return apiBaseWeakReference.get();
        else
            return null;
    }

    @Override
    public int cacheSize() {
        return mApiCache.size();
    }

    @Override
    public void putWebNode(String webNodeKey, String webNodeUrl) {
        mWebNodeMap.put(webNodeKey, webNodeUrl);
    }

    @Override
    public int getWebNodeSize() {
        return mWebNodeMap.size();
    }

}

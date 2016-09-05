package com.thinkcoo.mobile.model.cache;

import com.thinkcoo.mobile.model.entity.serverresponse.UserBasicInfoResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserHarvestsResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserStatusInfoResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.rx_cache.DynamicKey;
import io.rx_cache.DynamicKeyGroup;
import io.rx_cache.EvictProvider;
import io.rx_cache.LifeCache;
import io.rx_cache.Reply;
import rx.Observable;

/**
 * Created by Robert.yao on 2016/6/8.
 */
public interface UserCacheProviders {

    /**
     * 为获取用户收获列表提供缓存 (缓存生命周期是2分)
     * @param userHarvestsResponseObservable
     * @param userIdAndPageIndex
     * @param evictProvider
     * @return
     */
    @LifeCache(duration = 10 , timeUnit = TimeUnit.MINUTES)
    Observable<Reply<UserHarvestsResponse>> loadUserHarvestList(Observable<UserHarvestsResponse> userHarvestsResponseObservable, DynamicKeyGroup userIdAndPageIndex, EvictProvider evictProvider);

    /**
     * 为获取用户状态列表提供缓存
     * @param userStatusInfoResponseObservable
     * @param userId
     * @param evictProvider
     * @return
     */
    @LifeCache(duration = 10 ,timeUnit = TimeUnit.MINUTES)
    Observable<Reply<List<UserStatusInfoResponse>>> loadUserStatusList(Observable<List<UserStatusInfoResponse>> userStatusInfoResponseObservable, DynamicKey userId, EvictProvider evictProvider);


    /**
     * 为获取用户基本信息提供缓存
     * @param userBasicInfoResponseObservable
     * @param userId
     * @param evictProvider
     * @return
     */
    @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<UserBasicInfoResponse>> loadUserBasicInfo(Observable<UserBasicInfoResponse> userBasicInfoResponseObservable
            , DynamicKey userId , EvictProvider evictProvider);


}

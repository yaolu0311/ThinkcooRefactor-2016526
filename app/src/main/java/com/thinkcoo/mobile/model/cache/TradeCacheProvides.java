package com.thinkcoo.mobile.model.cache;

import com.thinkcoo.mobile.model.entity.QueryBuynformationResponseList;
import com.thinkcoo.mobile.model.entity.QuerySellInformationResponseList;
import com.thinkcoo.mobile.model.entity.serverresponse.MyBuyGoodsListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyCollectionGoodsResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MySellGoodsListResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.rx_cache.DynamicKeyGroup;
import io.rx_cache.EvictProvider;
import io.rx_cache.LifeCache;
import io.rx_cache.Reply;
import rx.Observable;

/**
 * Created by Robert.yao on 2016/7/18.
 */
public interface TradeCacheProvides {

    @LifeCache(duration = 2 , timeUnit = TimeUnit.MINUTES)
    Observable<Reply<QuerySellInformationResponseList>> getSellRecommendGoodsList(Observable<QuerySellInformationResponseList> apiObserver
            , DynamicKeyGroup userIdAndPageIndex
            , EvictProvider evictProvider);
    @LifeCache(duration = 2 , timeUnit = TimeUnit.MINUTES)
    Observable<Reply<List<MyCollectionGoodsResponse>>> getUserCollectList(Observable<List<MyCollectionGoodsResponse>> apiObserver
            , DynamicKeyGroup userIdAndCollectTypeAndPageIndex
            , EvictProvider evictProvider);
    @LifeCache(duration = 2 , timeUnit = TimeUnit.MINUTES)
    Observable<Reply<QueryBuynformationResponseList>> getBuyRecommendGoodsList(Observable<QueryBuynformationResponseList> apiObserver
            , DynamicKeyGroup userIdAndPageIndex
            , EvictProvider evictProvider);
    @LifeCache(duration = 2 , timeUnit = TimeUnit.MINUTES)
    Observable<Reply<MyBuyGoodsListResponse>> getMyBuyGoodsList(Observable<MyBuyGoodsListResponse> apiObserver
            , DynamicKeyGroup userIdAndPageIndex
            , EvictProvider evictProvider);
    @LifeCache(duration = 2 , timeUnit = TimeUnit.MINUTES)
    Observable<Reply<MySellGoodsListResponse>> getMySellGoodsList(Observable<MySellGoodsListResponse> apiObserver
            , DynamicKeyGroup userIdAndPageIndex
            , EvictProvider evictProvider);
}

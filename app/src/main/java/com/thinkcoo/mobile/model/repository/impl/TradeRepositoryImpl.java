package com.thinkcoo.mobile.model.repository.impl;



import com.example.administrator.publicmodule.entity.BaseResponse;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.model.cache.LoginUserCache;
import com.thinkcoo.mobile.model.cache.TradeCacheProvides;
import com.thinkcoo.mobile.model.db.GoodsSearchHistoryDao;
import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.model.entity.GoodsSearchHistory;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.model.entity.MyCollectGoods;
import com.thinkcoo.mobile.model.entity.MyCollectionGoodsListResponse;
import com.thinkcoo.mobile.model.entity.MyGoods;
import com.thinkcoo.mobile.model.entity.QueryBuynformationResponseList;
import com.thinkcoo.mobile.model.entity.QuerySellInformationResponseList;
import com.thinkcoo.mobile.model.entity.RequestParam.GetMyGoodsRequestParam;
import com.thinkcoo.mobile.model.entity.RequestParam.GoodsSearchContainDistanceParam;
import com.thinkcoo.mobile.model.entity.RequestParam.GoodsSearchParam;
import com.thinkcoo.mobile.model.entity.RequestParam.LoadMyCollectGoodsParam;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.model.entity.serverresponse.BuyGoodsDetailResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.GoodsSearchResultResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyBuyGoodsListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyCollectionBuyResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyCollectionGoodsResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MySellGoodsListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.QuerySellInformationResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.SellGoodsDetailResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;
import com.thinkcoo.mobile.model.exception.ServerResponseException;
import com.thinkcoo.mobile.model.exception.trade.GoodsSearchFailureException;
import com.thinkcoo.mobile.model.exception.trade.GoodsSearchResultEmptyException;
import com.thinkcoo.mobile.model.exception.trade.MyCollectGoodsEmptyException;
import com.thinkcoo.mobile.model.exception.trade.MyGoodsEmptyException;
import com.thinkcoo.mobile.model.repository.TradeRepository;
import com.thinkcoo.mobile.model.rest.apis.TradeApi;
import com.thinkcoo.mobile.presentation.views.PageMachine;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.rx_cache.DynamicKeyGroup;
import io.rx_cache.EvictProvider;
import io.rx_cache.Reply;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Robert.yao on 2016/7/18.
 */
public class TradeRepositoryImpl implements TradeRepository{

    private static final String TAG = "TradeRepositoryImpl";

    @Inject
    LoginUserCache mLoginUserCache;
    @Inject
    TradeApi mTradeApi;
    @Inject
    TradeCacheProvides mTradeCacheProvides;
    @Inject
    GoodsSearchHistoryDao mGoodsSearchHistoryDao;
    @Inject
    ServerDataConverter mServerDataConverter;

    @Inject
    public TradeRepositoryImpl() {

    }

    @Override
    public Observable<List<Goods>> getSellRecommendGoodsList(final Location location, final PageMachine pageMachine, final boolean isUpdateNow) {

        return mLoginUserCache.get().flatMap(new Func1<User, Observable<QuerySellInformationResponseList>>() {
            @Override
            public Observable<QuerySellInformationResponseList> call(User user) {
                Observable<QuerySellInformationResponseList> obs = mTradeApi.querySellInformation(user.getUserId(),"1",location.getCity(),location.getLatString(),location.getLatString(),pageMachine.getPageIndex(),pageMachine.getPageContentCount()).map(new Func1<BaseResponse<QuerySellInformationResponseList>, QuerySellInformationResponseList>() {
                    @Override
                    public QuerySellInformationResponseList call(BaseResponse<QuerySellInformationResponseList> listBaseResponse) {
                        pageMachine.setTotalPage(listBaseResponse.getPage().getPageCount());
                        return listBaseResponse.getData();
                    }
                });
                return mTradeCacheProvides.getSellRecommendGoodsList(obs,new DynamicKeyGroup(user.getUserId(),pageMachine.getPageIndex()),new EvictProvider(isUpdateNow)).map(new Func1<Reply<QuerySellInformationResponseList>, QuerySellInformationResponseList>() {
                    @Override
                    public QuerySellInformationResponseList call(Reply<QuerySellInformationResponseList> listReply) {
                        ThinkcooLog.d(TAG,"从：" + listReply.getSource() + " 获得数据 ===");
                        return listReply.getData();
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG,throwable.getMessage(),throwable);
                    }
                });
            }
        }).map(new Func1<QuerySellInformationResponseList, List<Goods>>() {
            @Override
            public List<Goods> call(QuerySellInformationResponseList querySellInformationResponseList) {
                List<QuerySellInformationResponse> querySellInformationResponses = querySellInformationResponseList.getList();
                List<Goods> goodsList = new ArrayList<>(querySellInformationResponses.size());
                for (int i = 0; i < querySellInformationResponses.size(); i++) {
                    goodsList.add(Goods.create(querySellInformationResponses.get(i)));
                }
                return goodsList;
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Goods>>>() {
            @Override
            public Observable<? extends List<Goods>> call(Throwable throwable) {
                return Observable.error(throwable);
            }
        });
    }

    @Override
    public Observable<List<Goods>> getBuyRecommendGoodsList(final Location location, final PageMachine pageMachine, final boolean isUpdateNow) {

        return mLoginUserCache.get().flatMap(new Func1<User, Observable<QueryBuynformationResponseList>>() {
            @Override
            public Observable<QueryBuynformationResponseList> call(User user) {
                Observable<QueryBuynformationResponseList> obs = mTradeApi.queryBuyInformation(user.getUserId(),"2",location.getCity(),location.getLatString(),location.getLatString(),pageMachine.getPageIndex(),pageMachine.getPageContentCount()).map(new Func1<BaseResponse<QueryBuynformationResponseList>, QueryBuynformationResponseList>() {
                    @Override
                    public QueryBuynformationResponseList call(BaseResponse<QueryBuynformationResponseList> queryBuynformationResponseListBaseResponse) {
                        pageMachine.setTotalPage(queryBuynformationResponseListBaseResponse.getPage().getPageCount());
                        return queryBuynformationResponseListBaseResponse.getData();
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG,"看看错误 : " + throwable.getMessage() , throwable) ;
                    }
                });
                return mTradeCacheProvides.getBuyRecommendGoodsList(obs,new DynamicKeyGroup(user.getUserId(),pageMachine.getPageIndex()),new EvictProvider(isUpdateNow)).map(new Func1<Reply<QueryBuynformationResponseList>, QueryBuynformationResponseList>() {
                    @Override
                    public QueryBuynformationResponseList call(Reply<QueryBuynformationResponseList> queryBuynformationResponseReply) {
                        return queryBuynformationResponseReply.getData();
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG,throwable.getMessage(),throwable);
                    }
                });
            }
        }).map(new Func1<QueryBuynformationResponseList, List<Goods>>() {
            @Override
            public List<Goods> call(QueryBuynformationResponseList queryBuynformationResponses) {
                List<Goods> goodsList = new ArrayList<>();
                for (int i = 0; i < queryBuynformationResponses.getList().size(); i++) {
                    goodsList.add(Goods.create(queryBuynformationResponses.getList().get(i)));
                }
                return goodsList;
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Goods>>>() {
            @Override
            public Observable<? extends List<Goods>> call(Throwable throwable) {
                return Observable.error(throwable);
            }
        });
    }
    @Override
    public Observable<List<String>> getGoodsSearchHistoryByType(int goodsType) {
        return Observable.just(mGoodsSearchHistoryDao.queryByGoodsType(goodsType)).map(new Func1<List<GoodsSearchHistory>, List<String>>() {
            @Override
            public List<String> call(List<GoodsSearchHistory> goodsCategories) {
                List<String> result = new ArrayList<>();
                for (int i = 0; i < goodsCategories.size(); i++) {
                    result.add(goodsCategories.get(i).getContent());
                }
                return result;
            }
        });
    }

    @Override
    public Observable<List<Goods>> goodsSearch(final GoodsSearchParam goodsSearchParam) {

        return mLoginUserCache.get().flatMap(new Func1<User, Observable<List<Goods>>>() {
            @Override
            public Observable<List<Goods>> call(User user) {

                return mTradeApi.goodsSearch(user.getUserId()
                        ,goodsSearchParam.getType()
                        ,goodsSearchParam.getPlace()
                        ,goodsSearchParam.getSchool()
                        ,goodsSearchParam.getCommodityCategory()
                        ,goodsSearchParam.getKeyWord()
                        ,goodsSearchParam.getOrderBy()
                        ,goodsSearchParam.getLongitude()
                        ,goodsSearchParam.getLatitude()
                        ,goodsSearchParam.getPageMachine().getPageIndex()
                        ,goodsSearchParam.getPageMachine().getPageContentCount())
                        .flatMap(new Func1<BaseResponse<List<GoodsSearchResultResponse>>, Observable<List<Goods>>>() {
                            @Override
                            public Observable<List<Goods>> call(BaseResponse<List<GoodsSearchResultResponse>> listBaseResponse) {
                                if (!listBaseResponse.isSuccess()){
                                    return Observable.error(new GoodsSearchFailureException());
                                }else {
                                    goodsSearchParam.getPageMachine().setTotalPage(listBaseResponse.getPage().getPageCount());
                                    List<GoodsSearchResultResponse> goodsSearchResultResponseList = listBaseResponse.getData();
                                    if (null == goodsSearchResultResponseList || goodsSearchResultResponseList.isEmpty()){
                                        Observable.error(new GoodsSearchResultEmptyException(goodsSearchParam.getKeyWord()));
                                    }
                                    List<Goods> goodsList = new ArrayList();
                                    for (int i = 0; i < goodsSearchResultResponseList.size(); i++) {
                                        goodsList.add(Goods.create(goodsSearchResultResponseList.get(i),goodsSearchParam.getType()));
                                    }
                                    return Observable.just(goodsList);
                                }
                            }
                        });
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG,throwable.getMessage(),throwable);
            }
        });
    }

    @Override
    public Observable<List<Goods>> goodsSearchContainDistance(final GoodsSearchContainDistanceParam goodsSearchContainDistanceParam) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<List<Goods>>>() {
            @Override
            public Observable<List<Goods>> call(User user) {
                return mTradeApi.goodsSearchContainDistance(user.getUserId()
                        ,goodsSearchContainDistanceParam.getType()
                        ,goodsSearchContainDistanceParam.getPlace()
                        ,goodsSearchContainDistanceParam.getCommodityCategory()
                        ,goodsSearchContainDistanceParam.getDistance()
                        ,goodsSearchContainDistanceParam.getLongitude()
                        ,goodsSearchContainDistanceParam.getKeyWord()
                        ,goodsSearchContainDistanceParam.getOrderBy()
                        ,goodsSearchContainDistanceParam.getLatitude()
                        ,goodsSearchContainDistanceParam.getPageMachine().getPageIndex()
                        ,goodsSearchContainDistanceParam.getPageMachine().getPageContentCount())
                        .flatMap(new Func1<BaseResponse<List<GoodsSearchResultResponse>>, Observable<List<Goods>>>() {
                    @Override
                    public Observable<List<Goods>> call(BaseResponse<List<GoodsSearchResultResponse>> listBaseResponse) {

                        if (!listBaseResponse.isSuccess()){
                            return Observable.error(new GoodsSearchFailureException());
                        }else {
                            goodsSearchContainDistanceParam.getPageMachine().setTotalPage(listBaseResponse.getPage().getPageCount());
                            List<GoodsSearchResultResponse> goodsSearchResultResponseList = listBaseResponse.getData();
                            if (null == goodsSearchResultResponseList || goodsSearchResultResponseList.isEmpty()){
                                Observable.error(new GoodsSearchResultEmptyException(goodsSearchContainDistanceParam.getKeyWord()));
                            }
                            List<Goods> goodsList = new ArrayList();
                            for (int i = 0; i < goodsSearchResultResponseList.size(); i++) {
                                goodsList.add(Goods.create(goodsSearchResultResponseList.get(i),goodsSearchContainDistanceParam.getType()));
                            }
                            return Observable.just(goodsList);
                        }
                    }
                });
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG,throwable.getMessage(),throwable);
            }
        });
    }

    @Override
    public Observable saveGoodsSearchHistory(final GoodsSearchHistory goodsSearchHistory) {
        return Observable.create(new Observable.OnSubscribe() {
            @Override
            public void call(Object o) {
                try {
                    mGoodsSearchHistoryDao.insert(goodsSearchHistory);
                } catch (Exception e) {
                    ThinkcooLog.e(TAG,e.getMessage(),e);
                }
            }
        });
    }
    @Override
    public Observable<Boolean> clearGoodsSearchHistory(final int type) {

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean result = mGoodsSearchHistoryDao.deleteAllByType(type);
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<MyGoods>> getMyGoodsList(GetMyGoodsRequestParam getMyGoodsRequestParam) {
        if (getMyGoodsRequestParam.isSellType()){
            return getMySellGoodsList(getMyGoodsRequestParam);
        }else {
            return getMyBuyGoodsList(getMyGoodsRequestParam);
        }
    }

    private Observable<List<MyGoods>> getMyBuyGoodsList(final GetMyGoodsRequestParam getMyGoodsRequestParam) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<List<MyGoods>>>() {
            @Override
            public Observable<List<MyGoods>> call(User user) {
                Observable<MyBuyGoodsListResponse> apiObs = mTradeApi.loadMyBuyGoodsList(user.getUserId(),getMyGoodsRequestParam.getPageMachine().getPageIndex(),getMyGoodsRequestParam.getPageMachine().getPageContentCount()).flatMap(new Func1<BaseResponse<MyBuyGoodsListResponse>, Observable<MyBuyGoodsListResponse>>() {
                    @Override
                    public Observable<MyBuyGoodsListResponse> call(BaseResponse<MyBuyGoodsListResponse> myBuyGoodsListResponseBaseResponse) {
                        if (!myBuyGoodsListResponseBaseResponse.isSuccess()){
                            return Observable.error(new ServerResponseException(myBuyGoodsListResponseBaseResponse.getMsg()));
                        }
                        if (null == myBuyGoodsListResponseBaseResponse.getData() || myBuyGoodsListResponseBaseResponse.getData().isEmpty()){
                            return Observable.error(new MyGoodsEmptyException(getMyGoodsRequestParam.getMyGoodsType()));
                        }
                        getMyGoodsRequestParam.getPageMachine().setTotalPage(myBuyGoodsListResponseBaseResponse.getPage().getPageCount());
                        return Observable.just(myBuyGoodsListResponseBaseResponse.getData());
                    }
                });
                return mTradeCacheProvides.getMyBuyGoodsList(apiObs,new DynamicKeyGroup(user.getUserId(),getMyGoodsRequestParam.getPageMachine().getPageIndex()),new EvictProvider(getMyGoodsRequestParam.isUpdateNow())).flatMap(new Func1<Reply<MyBuyGoodsListResponse>, Observable<List<MyGoods>>>() {
                    @Override
                    public Observable<List<MyGoods>> call(Reply<MyBuyGoodsListResponse> myBuyGoodsListResponseReply) {
                        return Observable.just(MyGoods.createList(myBuyGoodsListResponseReply.getData(),mServerDataConverter));
                    }
                }).onErrorResumeNext(new Func1<Throwable, Observable<? extends List<MyGoods>>>() {
                    @Override
                    public Observable<? extends List<MyGoods>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                });
            }
        });
    }

    private Observable<List<MyGoods>> getMySellGoodsList(final GetMyGoodsRequestParam getMyGoodsRequestParam) {

        return mLoginUserCache.get().flatMap(new Func1<User, Observable<List<MyGoods>>>() {
            @Override
            public Observable<List<MyGoods>> call(User user) {
                Observable<MySellGoodsListResponse> apiObs = mTradeApi.loadMySellGoodsList(user.getUserId(),getMyGoodsRequestParam.getPageMachine().getPageIndex(),getMyGoodsRequestParam.getPageMachine().getPageContentCount()).flatMap(new Func1<BaseResponse<MySellGoodsListResponse>, Observable<MySellGoodsListResponse>>() {
                    @Override
                    public Observable<MySellGoodsListResponse> call(BaseResponse<MySellGoodsListResponse> mySellGoodsListResponseBaseResponse) {
                        getMyGoodsRequestParam.getPageMachine().setTotalPage(mySellGoodsListResponseBaseResponse.getPage().getPageCount());
                        return Observable.just(mySellGoodsListResponseBaseResponse.getData());
                    }
                });
                return mTradeCacheProvides.getMySellGoodsList(apiObs,new DynamicKeyGroup(user.getUserId(),getMyGoodsRequestParam.getPageMachine().getPageIndex()),new EvictProvider(getMyGoodsRequestParam.isUpdateNow())).flatMap(new Func1<Reply<MySellGoodsListResponse>, Observable<List<MyGoods>>>() {
                    @Override
                    public Observable<List<MyGoods>> call(Reply<MySellGoodsListResponse> myBuyGoodsListResponseReply) {
                        return Observable.just(MyGoods.createList(myBuyGoodsListResponseReply.getData(),mServerDataConverter));
                    }
                }).onErrorResumeNext(new Func1<Throwable, Observable<? extends List<MyGoods>>>() {
                    @Override
                    public Observable<? extends List<MyGoods>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                });
            }
        });
    }


    @Override
    public Observable soldOutMyGoods(final MyGoods myGoods) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<?>>() {
            @Override
            public Observable<?> call(User user) {
                return mTradeApi.selloff(user.getUserId(),myGoods.getId(),myGoods.getStatus());
            }
        });
    }

    @Override
    public Observable refreshMyGoods(final MyGoods myGoods) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<?>>() {
            @Override
            public Observable<?> call(User user) {
                if (myGoods.typeIsBuy()){
                    return mTradeApi.refreshbuy(user.getUserId(),myGoods.getId());
                }else {
                    return mTradeApi.refreshsell(user.getUserId(),myGoods.getId());
                }
            }
        });
    }

    @Override
    public Observable deleteMyGoods(final MyGoods myGoods) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<?>>() {
            @Override
            public Observable<?> call(User user) {
                if (myGoods.typeIsBuy()){
                    return mTradeApi.delbuy(user.getUserId(),myGoods.getId());
                }else {
                    return mTradeApi.delsell(user.getUserId(),myGoods.getId());
                }
            }
        });
    }

    @Override
    public Observable updateMyGoods(final MyGoods myGoods) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<?>>() {
            @Override
            public Observable<?> call(User user) {
                if (myGoods.typeIsBuy()){
                    return mTradeApi.updatebuy(user.getUserId()
                            ,myGoods.getMyGoodsDetail().getLocation().getCity()
                            ,myGoods.getId(),myGoods.getName()
                            ,myGoods.getCategory(),myGoods.getPrice()
                            ,myGoods.getDetail()
                            ,myGoods.getSchoolName()
                            ,myGoods.getMyGoodsDetail().getLocation().getLonString()
                            ,myGoods.getMyGoodsDetail().getLocation().getLatString()
                            ,myGoods.getMyGoodsDetail().getLocation().getAddress());
                }else {

                    return mTradeApi.updatesell(user.getUserId()
                            ,myGoods.getId()
                            ,myGoods.getName()
                            ,myGoods.getPrice()
                            ,myGoods.getCategory()
                            ,myGoods.getMyGoodsDetail().getQuality()
                            ,myGoods.getSchoolName()
                            ,myGoods.getMyGoodsDetail().getLocation().getAddress()
                            ,myGoods.getMyGoodsDetail().getIntroduce()
                            ,myGoods.getMyGoodsDetail().getLocation().getCity()
                            ,myGoods.getMyGoodsDetail().getLocation().getLonString()
                            ,myGoods.getMyGoodsDetail().getLocation().getLatString()
                            ,myGoods.getMyGoodsDetail().getFilePaths()
                    );
                }
            }
        });
    }

    @Override
    public Observable deleteMyGoodsImage(final String attachmentId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<?>>() {
            @Override
            public Observable<?> call(User user) {
                return mTradeApi.delattachment(user.getUserId(),attachmentId);
            }
        });
    }
    @Override
    public Observable createMyGoods(MyGoods myGoods) {
        if (myGoods.typeIsBuy()){
            return createMyBuyGoods(myGoods);
        }else {
            return createMySellGoods(myGoods);
        }
    }

    private Observable createMySellGoods(MyGoods myGoods) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<?>>() {
            @Override
            public Observable<?> call(User user) {
                return null;
            }
        });
    }

    private Observable createMyBuyGoods(final MyGoods myGoods) {

        return mLoginUserCache.get().flatMap(new Func1<User, Observable<?>>() {
            @Override
            public Observable<?> call(User user) {

                return mTradeApi.addsell(
                        user.getUserId(),
                        myGoods.getName(),
                        myGoods.getPrice(),
                        myGoods.getCategory(),
                        myGoods.getMyGoodsDetail().getQuality(),
                        myGoods.getSchoolName(),
                        myGoods.getMyGoodsDetail().getLocation().getAddress(),
                        myGoods.getMyGoodsDetail().getIntroduce(),
                        myGoods.getMyGoodsDetail().getLocation().getCity(),
                        myGoods.getMyGoodsDetail().getLocation().getLonString(),
                        myGoods.getMyGoodsDetail().getLocation().getLatString(),
                        "");
            }
        });
    }

    @Override
    public Observable loadMyGoodsDetail(MyGoods myGoods) {
        return null;
    }
    @Override
    public Observable editMyGoods(MyGoods myGoods) {
        return null;
    }

    @Override
    public Observable<List<MyCollectGoods>> loadMyCollectGoods(final LoadMyCollectGoodsParam loadMyCollectGoodsParam) {

        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<MyCollectionGoodsListResponse>>>() {
            @Override
            public Observable<BaseResponse<MyCollectionGoodsListResponse>> call(User user) {
                return mTradeApi.loadMyCollectionGoods(user.getUserId(),loadMyCollectGoodsParam.getQueryType(),loadMyCollectGoodsParam.getPageMachine().getPageIndex(),loadMyCollectGoodsParam.getPageMachine().getPageContentCount());
            }
        }).flatMap(new Func1<BaseResponse<MyCollectionGoodsListResponse>, Observable<List<MyCollectGoods>>>() {
            @Override
            public Observable<List<MyCollectGoods>> call(BaseResponse<MyCollectionGoodsListResponse> myCollectionGoodsListResponseBaseResponse) {
                if (!myCollectionGoodsListResponseBaseResponse.isSuccess()){
                    return Observable.error(new ServerResponseException(myCollectionGoodsListResponseBaseResponse.getMsg()));
                }
                if (null == myCollectionGoodsListResponseBaseResponse.getData() || null == myCollectionGoodsListResponseBaseResponse.getData().getList() || myCollectionGoodsListResponseBaseResponse.getData().getList().isEmpty()){
                    return Observable.error(new MyCollectGoodsEmptyException(loadMyCollectGoodsParam.getQueryType()));
                }
                loadMyCollectGoodsParam.getPageMachine().setTotalPage(myCollectionGoodsListResponseBaseResponse.getPage().getPageCount());
                MyCollectionGoodsListResponse myCollectionGoodsListResponse = myCollectionGoodsListResponseBaseResponse.getData();
                return Observable.just(MyCollectGoods.createMyCollectGoodsList(myCollectionGoodsListResponse));
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG,throwable.getMessage(),throwable);
            }
        });
    }

    @Override
    public Observable<BuyGoodsDetailResponse> loadBuyGoodsDetail(final String buyId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BuyGoodsDetailResponse>>() {
            @Override
            public Observable<BuyGoodsDetailResponse> call(User user) {
                return mTradeApi.loadBuyGoodsDetail(user.getUserId(),buyId).flatMap(new Func1<BaseResponse<BuyGoodsDetailResponse>, Observable<BuyGoodsDetailResponse>>() {
                    @Override
                    public Observable<BuyGoodsDetailResponse> call(BaseResponse<BuyGoodsDetailResponse> buyGoodsDetailResponseBaseResponse) {
                        if (!buyGoodsDetailResponseBaseResponse.isSuccess()){
                            return Observable.error(new ServerResponseException(buyGoodsDetailResponseBaseResponse.getMsg()));
                        }
                        return Observable.just(buyGoodsDetailResponseBaseResponse.getData());
                    }
                });
            }
        });
    }

    @Override
    public Observable<SellGoodsDetailResponse> loadSellGoodsDetail(final String sellId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<SellGoodsDetailResponse>>() {
            @Override
            public Observable<SellGoodsDetailResponse> call(User user) {
                return mTradeApi.loadSellGoodsDetail(user.getUserId(),sellId).flatMap(new Func1<BaseResponse<SellGoodsDetailResponse>, Observable<SellGoodsDetailResponse>>() {
                    @Override
                    public Observable<SellGoodsDetailResponse> call(BaseResponse<SellGoodsDetailResponse> sellGoodsDetailResponseBaseResponse) {
                        if (!sellGoodsDetailResponseBaseResponse.isSuccess()){
                            return Observable.error(new ServerResponseException(sellGoodsDetailResponseBaseResponse.getMsg()));
                        }
                        return Observable.just(sellGoodsDetailResponseBaseResponse.getData());
                    }
                });
            }
        });
    }

    @Override
    public Observable collectGoods(final String collectId, final String type) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<?>>() {
            @Override
            public Observable<?> call(User user) {
                return mTradeApi.addcollection(user.getUserId(),collectId,type).flatMap(new Func1<BaseResponse, Observable<?>>() {
                    @Override
                    public Observable<?> call(BaseResponse baseResponse) {
                        if (!baseResponse.isSuccess()){
                            return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                        }
                        return Observable.just(null);
                    }
                });
            }
        });
    }

    @Override
    public Observable unCollectGoods(final String collectId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<?>>() {
            @Override
            public Observable<?> call(User user) {
                return mTradeApi.delcollection(collectId).flatMap(new Func1<BaseResponse, Observable<?>>() {
                    @Override
                    public Observable<?> call(BaseResponse baseResponse) {
                        if (!baseResponse.isSuccess()){
                            return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                        }
                        return Observable.just(null);
                    }
                });
            }
        });
    }
}

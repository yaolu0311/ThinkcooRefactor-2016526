package com.thinkcoo.mobile.model.repository;

import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.model.entity.GoodsSearchHistory;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.model.entity.MyCollectGoods;
import com.thinkcoo.mobile.model.entity.MyGoods;
import com.thinkcoo.mobile.model.entity.RequestParam.GetMyGoodsRequestParam;
import com.thinkcoo.mobile.model.entity.RequestParam.GoodsSearchContainDistanceParam;
import com.thinkcoo.mobile.model.entity.RequestParam.GoodsSearchParam;
import com.thinkcoo.mobile.model.entity.RequestParam.LoadMyCollectGoodsParam;
import com.thinkcoo.mobile.model.entity.serverresponse.BuyGoodsDetailResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyCollectionBuyResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyCollectionGoodsResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.SellGoodsDetailResponse;
import com.thinkcoo.mobile.presentation.views.PageMachine;
import java.util.List;
import rx.Observable;

/**
 * Created by Robert.yao on 2016/7/18.
 */
public interface TradeRepository {

    Observable<List<Goods>> getSellRecommendGoodsList(Location location , PageMachine pageMachine, boolean isUpdateNow);
    Observable<List<Goods>> getBuyRecommendGoodsList(Location location , PageMachine pageMachine, boolean isUpdateNow);
    Observable<List<Goods>> goodsSearch(GoodsSearchParam goodsSearchParam);
    Observable<List<Goods>> goodsSearchContainDistance(GoodsSearchContainDistanceParam goodsSearchContainDistanceParam);
    Observable saveGoodsSearchHistory(GoodsSearchHistory goodsSearchHistory);
    Observable clearGoodsSearchHistory(int type);
    Observable<List<String>> getGoodsSearchHistoryByType(int goodsType);
    Observable<List<MyGoods>> getMyGoodsList(GetMyGoodsRequestParam getMyGoodsRequestParam);
    Observable soldOutMyGoods(MyGoods myGoods);
    Observable refreshMyGoods(MyGoods myGoods);
    Observable deleteMyGoods(MyGoods myGoods);
    Observable updateMyGoods(MyGoods myGoods);
    Observable deleteMyGoodsImage(String attachmentId);
    Observable createMyGoods(MyGoods myGoods);
    Observable loadMyGoodsDetail(MyGoods myGoods);
    Observable editMyGoods(MyGoods myGoods);
    Observable<List<MyCollectGoods>> loadMyCollectGoods(LoadMyCollectGoodsParam loadMyCollectGoodsParam);
    Observable<BuyGoodsDetailResponse> loadBuyGoodsDetail(String buyId);
    Observable<SellGoodsDetailResponse> loadSellGoodsDetail( String sellId);
    Observable collectGoods(String collectId, String type);
    Observable unCollectGoods(String goodsId);
}

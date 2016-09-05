package com.thinkcoo.mobile.domain.trade;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.GoodsRecommendParam;
import com.thinkcoo.mobile.model.repository.TradeRepository;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/1.
 */
public class LoadBuyGoodsRecommendUseCase extends UseCase<Object> {

    TradeRepository mTradeRepository;

    public LoadBuyGoodsRecommendUseCase(Scheduler mUiThread, Scheduler mExecutorThread, TradeRepository tradeRepository) {
        super(mUiThread, mExecutorThread);
        mTradeRepository = tradeRepository;
    }
    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        GoodsRecommendParam goodsRecommendParam = (GoodsRecommendParam) q[0];
        return mTradeRepository.getBuyRecommendGoodsList(goodsRecommendParam.getLocation(), goodsRecommendParam.getPageMachine(), goodsRecommendParam.isPullToRefresh());
    }
}

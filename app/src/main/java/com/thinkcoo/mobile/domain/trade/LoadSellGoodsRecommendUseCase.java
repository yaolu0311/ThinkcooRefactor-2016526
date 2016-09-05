package com.thinkcoo.mobile.domain.trade;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.GoodsRecommendParam;
import com.thinkcoo.mobile.model.repository.TradeRepository;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/7/25.
 */
public class LoadSellGoodsRecommendUseCase extends UseCase<Object> {

    TradeRepository mTradeRepository;

    public LoadSellGoodsRecommendUseCase(Scheduler mUiThread, Scheduler mExecutorThread, TradeRepository tradeRepository) {
        super(mUiThread, mExecutorThread);
        mTradeRepository = tradeRepository;
    }
    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        GoodsRecommendParam goodsRecommendParam = (GoodsRecommendParam) q[0];
        return mTradeRepository.getSellRecommendGoodsList( goodsRecommendParam.getLocation(), goodsRecommendParam.getPageMachine(), goodsRecommendParam.isPullToRefresh());
    }
}

package com.thinkcoo.mobile.domain.trade;


import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.GoodsSearchContainDistanceParam;
import com.thinkcoo.mobile.model.entity.RequestParam.GoodsSearchParam;
import com.thinkcoo.mobile.model.repository.TradeRepository;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/4.
 */
public class GoodsSearchUseCase extends UseCase<Object> {

    TradeRepository mTradeRepository;

    public GoodsSearchUseCase(Scheduler mUiThread, Scheduler mExecutorThread,TradeRepository tradeRepository) {
        super(mUiThread, mExecutorThread);
        mTradeRepository = tradeRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        if (q[0] instanceof GoodsSearchContainDistanceParam){
            GoodsSearchContainDistanceParam goodsSearchContainDistanceParam = (GoodsSearchContainDistanceParam)q[0];
            return mTradeRepository.goodsSearchContainDistance(goodsSearchContainDistanceParam);
        }else if (q[0] instanceof GoodsSearchParam){
            GoodsSearchParam goodsSearchParam = (GoodsSearchParam)q[0];
            return mTradeRepository.goodsSearch(goodsSearchParam);
        }
        throw new IllegalArgumentException("GoodsSearchUseCase put a unknown param");
    }
}

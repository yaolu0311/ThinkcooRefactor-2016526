package com.thinkcoo.mobile.domain.trade;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.LoadMyCollectGoodsParam;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.model.repository.TradeRepository;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/17.
 */
public class LoadMyCollectGoodsUseCase extends UseCase<Object>{

    TradeRepository mTradeRepository;

    public LoadMyCollectGoodsUseCase(Scheduler mUiThread, Scheduler mExecutorThread, TradeRepository tradeRepository) {
        super(mUiThread, mExecutorThread);
        mTradeRepository = tradeRepository;
    }
    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mTradeRepository.loadMyCollectGoods((LoadMyCollectGoodsParam) q[0]);
    }
}

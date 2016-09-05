package com.thinkcoo.mobile.domain.trade;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.repository.TradeRepository;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/19.
 */
public class UnCollectGoodsUseCase extends UseCase<String>{

    TradeRepository mTradeRepository;

    public UnCollectGoodsUseCase(Scheduler mUiThread, Scheduler mExecutorThread, TradeRepository tradeRepository) {
        super(mUiThread, mExecutorThread);
        mTradeRepository = tradeRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(String... q) {
        return mTradeRepository.unCollectGoods(q[0]);
    }
}

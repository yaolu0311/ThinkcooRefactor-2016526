package com.thinkcoo.mobile.domain.trade;


import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.repository.TradeRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/4.
 */
public class ClearGoodsSearchHistoryUseCase extends UseCase<Integer> {

    TradeRepository mTradeRepository;

    public ClearGoodsSearchHistoryUseCase(Scheduler mUiThread, Scheduler mExecutorThread, TradeRepository tradeRepository) {
        super(mUiThread, mExecutorThread);
        mTradeRepository = tradeRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Integer... q) {
        return mTradeRepository.clearGoodsSearchHistory(q[0]);
    }
}

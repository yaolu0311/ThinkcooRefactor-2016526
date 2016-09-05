package com.thinkcoo.mobile.domain.trade;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.repository.TradeRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/2.
 */
public class GetGoodsSearchHistoryByTypeUseCase extends UseCase<Integer>{

    TradeRepository mTradeRepository;

    public GetGoodsSearchHistoryByTypeUseCase(Scheduler mUiThread, Scheduler mExecutorThread, TradeRepository tradeRepository) {
        super(mUiThread, mExecutorThread);
        mTradeRepository = tradeRepository;
    }
    @Override
    protected Observable buildUseCaseObservable(Integer... q) {
        return mTradeRepository.getGoodsSearchHistoryByType(q[0]);
    }
}

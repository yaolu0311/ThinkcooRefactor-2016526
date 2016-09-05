package com.thinkcoo.mobile.domain.trade;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.GoodsSearchHistory;
import com.thinkcoo.mobile.model.repository.TradeRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/4.
 */
public class SaveGoodsSearchHistoryUseCase extends UseCase<GoodsSearchHistory>{

    TradeRepository mTradeRepository;


    public SaveGoodsSearchHistoryUseCase(Scheduler mUiThread, Scheduler mExecutorThread, TradeRepository tradeRepository) {
        super(mUiThread, mExecutorThread);
        mTradeRepository = tradeRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(GoodsSearchHistory... q) {
        return mTradeRepository.saveGoodsSearchHistory(q[0]);
    }
}

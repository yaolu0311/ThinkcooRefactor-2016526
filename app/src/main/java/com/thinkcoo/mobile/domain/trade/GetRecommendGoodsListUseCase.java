package com.thinkcoo.mobile.domain.trade;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.repository.TradeRepository;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/7/18.
 */
public class GetRecommendGoodsListUseCase extends UseCase<String>{

    TradeRepository mTradeRepository;

    public GetRecommendGoodsListUseCase(Scheduler mUiThread, Scheduler mExecutorThread, TradeRepository tradeRepository) {
        super(mUiThread, mExecutorThread);
        mTradeRepository = tradeRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(String... q) {
//        return mTradeRepository.getRecommendGoodsList();
        return null;
    }
}

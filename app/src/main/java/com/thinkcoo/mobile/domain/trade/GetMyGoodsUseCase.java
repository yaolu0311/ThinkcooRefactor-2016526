package com.thinkcoo.mobile.domain.trade;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.GetMyGoodsRequestParam;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.model.repository.TradeRepository;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/9.
 */
public class GetMyGoodsUseCase extends UseCase<Object>{

    TradeRepository mTradeRepository;

    public GetMyGoodsUseCase(Scheduler mUiThread, Scheduler mExecutorThread, TradeRepository tradeRepository) {
        super(mUiThread, mExecutorThread);
        mTradeRepository = tradeRepository;
    }
    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        GetMyGoodsRequestParam getMyGoodsRequestParam = (GetMyGoodsRequestParam)q[0];
        return mTradeRepository.getMyGoodsList(getMyGoodsRequestParam);
    }
}

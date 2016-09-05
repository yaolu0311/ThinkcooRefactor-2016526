package com.thinkcoo.mobile.domain.trade;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.MyGoods;
import com.thinkcoo.mobile.model.repository.TradeRepository;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/9.
 */
public class UpdateMyGoodsUseCase extends UseCase<Object>{

    TradeRepository mTradeRepository;

    public UpdateMyGoodsUseCase(Scheduler mUiThread, Scheduler mExecutorThread, TradeRepository tradeRepository) {
        super(mUiThread, mExecutorThread);
        mTradeRepository = tradeRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mTradeRepository.updateMyGoods((MyGoods)q[0]);
    }
}

package com.thinkcoo.mobile.domain.trade;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.MyGoods;
import com.thinkcoo.mobile.model.repository.TradeRepository;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/10.
 */
public class EditMyGoodsUseCase extends UseCase<Object> {

    TradeRepository mTradeRepository;

    public EditMyGoodsUseCase(Scheduler mUiThread, Scheduler mExecutorThread, TradeRepository tradeRepository) {
        super(mUiThread, mExecutorThread);
        mTradeRepository = tradeRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mTradeRepository.editMyGoods((MyGoods)q[0]);
    }
}

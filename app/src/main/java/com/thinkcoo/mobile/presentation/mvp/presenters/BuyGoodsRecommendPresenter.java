package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.trade.LoadBuyGoodsRecommendUseCase;
import com.thinkcoo.mobile.domain.trade.LoadSellGoodsRecommendUseCase;
import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.model.entity.RequestParam.GoodsRecommendParam;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.presentation.mvp.views.GoodsRecommendView;
import com.thinkcoo.mobile.presentation.views.PageMachine;
import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/7/21.
 */
public class BuyGoodsRecommendPresenter extends BaseLcePagedPresenter<Goods,GoodsRecommendView>{

    @Inject
    public BuyGoodsRecommendPresenter(LoadBuyGoodsRecommendUseCase useCase) {
        super(useCase);
    }
    @Override
    protected RequestParam buildLcePagedParams(boolean pullToRefresh, PageMachine pageMachine) {
        return GoodsRecommendParam.newBuilder()
                .isPullToRefresh(pullToRefresh)
                .mPageMachine(pageMachine)
                .goodsRecommendType(2)
                .location(getView().getLocation())
                .build();
    }


}

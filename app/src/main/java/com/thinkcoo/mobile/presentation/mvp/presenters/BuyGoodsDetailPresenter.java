package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.trade.LoadBuyGoodsDetailUseCase;
import com.thinkcoo.mobile.model.entity.serverresponse.BuyGoodsDetailResponse;
import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/18.
 */
public class BuyGoodsDetailPresenter extends GoodsDetailPresenter<BuyGoodsDetailResponse>{

    @Inject
    public BuyGoodsDetailPresenter(LoadBuyGoodsDetailUseCase mLoadBuyGoodsDetailUseCase) {
        super(mLoadBuyGoodsDetailUseCase);
    }
}

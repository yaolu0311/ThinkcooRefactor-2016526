package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.trade.LoadSellGoodsDetailUseCase;
import com.thinkcoo.mobile.model.entity.serverresponse.BuyGoodsDetailResponse;
import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/19.
 */
public class SellGoodsDetailPresenter extends GoodsDetailPresenter<BuyGoodsDetailResponse> {

    @Inject
    public SellGoodsDetailPresenter(LoadSellGoodsDetailUseCase loadUseCase) {
        super(loadUseCase);
    }
}

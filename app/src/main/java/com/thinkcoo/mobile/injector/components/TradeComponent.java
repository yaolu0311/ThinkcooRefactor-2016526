package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.ActivityModule;
import com.thinkcoo.mobile.injector.modules.TradeModule;
//import com.thinkcoo.mobile.model.entity.serverresponse.SellGoodsDetailResponse;
//import com.thinkcoo.mobile.presentation.views.activitys.BuyGoodsDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.ReleaseMyBuyGoodsActivity;
import com.thinkcoo.mobile.presentation.views.activitys.ReleaseMySellGoodsActivity;
//import com.thinkcoo.mobile.presentation.views.activitys.SellGoodsDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.TradeMainActivity;
import com.thinkcoo.mobile.presentation.views.fragment.BuyGoodsRecommendFragment;
import com.thinkcoo.mobile.presentation.views.fragment.GoodsSearchResultFragment;
import com.thinkcoo.mobile.presentation.views.fragment.MyCollectGoodsFragment;
import com.thinkcoo.mobile.presentation.views.fragment.MyGoodsFragment;
import com.thinkcoo.mobile.presentation.views.fragment.SellGoodsRecommendFragment;

import dagger.Component;

/**
 * Created by Robert.yao on 2016/7/18.
 */
@Component(dependencies = ApplicationComponent.class , modules ={TradeModule.class , ActivityModule.class})
@ActivityScope
public interface TradeComponent {

     void inject(BuyGoodsRecommendFragment buyGoodsRecommendFragment);
     void inject(SellGoodsRecommendFragment sellGoodsRecommendFragment);
     void inject(GoodsSearchResultFragment goodsSearchResultFragment);
     void inject(TradeMainActivity tradeMainActivity);
     void inject(MyGoodsFragment myGoodsFragment);
     void inject(ReleaseMySellGoodsActivity releaseMySellGoodsActivity);
     void inject(ReleaseMyBuyGoodsActivity releaseMyBuyGoodsActivity);
     void inject(MyCollectGoodsFragment myCollectGoodsFragment);
     //void inject(BuyGoodsDetailActivity buyGoodsDetailActivity);
     //void inject(SellGoodsDetailActivity buyGoodsDetailActivity);
}

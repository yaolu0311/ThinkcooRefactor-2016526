package com.thinkcoo.mobile.presentation.views.activitys;

import android.view.View;
import com.thinkcoo.mobile.injector.components.DaggerTradeComponent;
import com.thinkcoo.mobile.injector.modules.TradeModule;
import com.thinkcoo.mobile.model.entity.MyGoods;
import com.thinkcoo.mobile.model.entity.MyGoodsDetail;

/**
 * Created by Robert.yao on 2016/8/11.
 */
public class ReleaseMyBuyGoodsActivity extends ReleaseMyGoodsActivity {

    @Override
    protected View getDetailLayout() {
        return null;
    }

    @Override
    protected void bindHostObjectToView(MyGoods hostObject) {

    }

    @Override
    protected void bindDetailObjectToView(MyGoodsDetail detailObject) {

    }

    @Override
    public MyGoods getHostFromUi() {
        return null;
    }

    @Override
    protected void initDaggerInject() {
        DaggerTradeComponent.builder().tradeModule(new TradeModule()).activityModule(getActivityModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }
}

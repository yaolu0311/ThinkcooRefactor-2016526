package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.trade.LoadMyCollectGoodsUseCase;
import com.thinkcoo.mobile.model.entity.MyCollectGoods;
import com.thinkcoo.mobile.model.entity.RequestParam.LoadMyCollectGoodsParam;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.presentation.mvp.views.MyCollectGoodsView;
import com.thinkcoo.mobile.presentation.views.PageMachine;
import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/17.
 */
public class MyCollectGoodsPresenter extends BaseLcePagedPresenter<MyCollectGoods,MyCollectGoodsView<MyCollectGoods>> {

    @Inject
    public MyCollectGoodsPresenter(LoadMyCollectGoodsUseCase useCase) {
        super(useCase);
    }
    @Override
    protected RequestParam buildLcePagedParams(boolean pullToRefresh, PageMachine pageMachine) {
        LoadMyCollectGoodsParam loadMyCollectGoodsParam = new LoadMyCollectGoodsParam();
        loadMyCollectGoodsParam.setUpdateNow(pullToRefresh);
        loadMyCollectGoodsParam.setPageMachine(pageMachine);
        loadMyCollectGoodsParam.setQueryType(getView().getType());
        return loadMyCollectGoodsParam;
    }
}

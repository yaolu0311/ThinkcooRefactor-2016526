package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.trade.GoodsSearchUseCase;
import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.model.entity.GoodsFilter;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.model.entity.RequestParam.GoodsSearchContainDistanceParam;
import com.thinkcoo.mobile.model.entity.RequestParam.GoodsSearchParam;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.presentation.mvp.views.GoodsSearchResultView;
import com.thinkcoo.mobile.presentation.views.PageMachine;
import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/3.
 */
public class GoodsSearchResultPresenter extends BaseLcePagedPresenter<Goods,GoodsSearchResultView>{


    @Inject
    public GoodsSearchResultPresenter(GoodsSearchUseCase useCase) {
        super(useCase);
    }

    @Override
    protected RequestParam buildLcePagedParams(boolean pullToRefresh, PageMachine pageMachine) {

        Location location = getView().getLocation();
        GoodsFilter goodsFilter = getView().getGoodsFilter();
        int goodsType = getView().getGoodsType();
        String searchKey = getView().getSearchKey();

        RequestParam requestParam;

        if (null != goodsFilter.getGoodsDistance()){

            requestParam = GoodsSearchContainDistanceParam.newBuilder()
                    .place(location.getCityCode())
                    .commodityCategory(goodsFilter.getGoodsCategory().getCategotyCode())
                    .distance(goodsFilter.getGoodsDistance().getDistanceCode())
                    .keyWord(searchKey)
                    .latitude(location.getLat())
                    .longitude(location.getLon()).orderBy(goodsFilter.getGoodsPriceSortRule().getRuleCode())
                    .type(goodsType)
                    .school(goodsFilter.getGoodsHostSchool().getSchoolCode())
                    .pageMachine(pageMachine).place(location.getCity()).build();

        }else {

            requestParam = GoodsSearchParam.newBuilder()
                    .place(location.getCityCode())
                    .commodityCategory(goodsFilter.getGoodsCategory().getCategotyCode())
                    .keyWord(searchKey)
                    .latitude(location.getLat())
                    .longitude(location.getLon()).orderBy(goodsFilter.getGoodsPriceSortRule().getRuleCode())
                    .type(goodsType)
                    .school(goodsFilter.getGoodsHostSchool().getSchoolCode())
                    .pageMachine(pageMachine).place(location.getCity()).build();

        }

        return requestParam;
    }
}

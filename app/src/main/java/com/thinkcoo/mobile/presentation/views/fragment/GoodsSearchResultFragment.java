package com.thinkcoo.mobile.presentation.views.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.thinkcoo.mobile.injector.components.DaggerTradeComponent;
import com.thinkcoo.mobile.injector.modules.TradeModule;
import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.model.entity.GoodsFilter;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePagedPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.GoodsSearchResultPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.GoodsSearchResultView;
import com.thinkcoo.mobile.presentation.views.activitys.GoodsSearchAndFilterActivity;

import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/3.
 */
public class GoodsSearchResultFragment extends BaseLcePagedFragment<Goods> implements GoodsSearchResultView{


    @Inject
    GoodsSearchResultPresenter mGoodsSearchResultPresenter;

    @Override
    protected BaseLcePagedPresenter provideBaseLcePagedPresenter() {
        return mGoodsSearchResultPresenter;
    }

    @Override
    protected LceAdapterViewBind<Goods> provideLceAdapterViewBind() {

        return new LceAdapterViewBind<Goods>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, Goods goods) {

            }
        };
    }

    @Override
    protected void initInject() {
        DaggerTradeComponent.builder().tradeModule(new TradeModule()).activityModule(getFragmentInjectHelper().getActivityModule()).applicationComponent(getFragmentInjectHelper().getApplicationComponent()).build().inject(this);
    }

    @Override
    public Location getLocation() {
        return getHostActivity().getLocation();
    }

    @Override
    public GoodsFilter getGoodsFilter() {
        return getHostActivity().getGoodsFilter();
    }

    @Override
    public int getGoodsType() {
        return getHostActivity().getGoodsType();
    }

    @Override
    public String getSearchKey() {
        return getHostActivity().getSearchKey();
    }

    public GoodsSearchAndFilterActivity getHostActivity(){
        return (GoodsSearchAndFilterActivity)getActivity();
    }

    public void startSearch(){
        loadData(false);
    }
}

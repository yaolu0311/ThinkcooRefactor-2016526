package com.thinkcoo.mobile.presentation.views.fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerTradeComponent;
import com.thinkcoo.mobile.injector.modules.TradeModule;
import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePagedPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.BuyGoodsRecommendPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.GoodsRecommendView;
import com.thinkcoo.mobile.presentation.mvp.views.TradeMainView;
import com.thinkcoo.mobile.presentation.views.activitys.TradeMainActivity;

import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/7/21.
 */
public class BuyGoodsRecommendFragment extends BaseLcePagedFragment<Goods> implements GoodsRecommendView {

    public static final String TAG = "BuyGoodsRecommendFragment";

    @Inject
    BuyGoodsRecommendPresenter mBuyGoodsRecommendPresenter;

    @Override
    protected LceAdapterViewBind<Goods> provideLceAdapterViewBind() {

        return new LceAdapterViewBind<Goods>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new BuyGoodsRecommendViewHolder(inflaterView(parent));
            }
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, Goods goods) {
                ((BuyGoodsRecommendViewHolder)holder).bind(goods);
            }
        };
    }

    private View inflaterView(ViewGroup parent) {
        return getActivity().getLayoutInflater().inflate(R.layout.item_list_trade_buy,parent,false);
    }

    @Override
    protected BaseLcePagedPresenter provideBaseLcePagedPresenter() {
        return mBuyGoodsRecommendPresenter;
    }

    @Override
    protected void initInject() {
        DaggerTradeComponent.builder().applicationComponent(getFragmentInjectHelper().getApplicationComponent()).tradeModule(new TradeModule()).activityModule(getFragmentInjectHelper().getActivityModule()).build().inject(this);
    }

    @Override
    public Location getLocation() {
        return ((TradeMainActivity)getActivity()).getLocation();
    }

    public void refreshOnLocationChanged() {
        loadData(false);
    }

    public class BuyGoodsRecommendViewHolder extends RecyclerView.ViewHolder{

        private TextView mGoodsNameTv;
        private TextView mSchoolNameTv;
        private TextView mReleaseTimeTv;
        private Goods mGoods;

        public BuyGoodsRecommendViewHolder(View rootView) {
            super(rootView);
            mGoodsNameTv = (TextView) rootView.findViewById(R.id.ac_text_trade_goodsbuy_name);
            mSchoolNameTv = (TextView) rootView.findViewById(R.id.ac_text_tradebuy_school);
            mReleaseTimeTv = (TextView)rootView.findViewById(R.id.ac_text_tradebuy_time);
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentNavigatorHelper().getNavigator().navigateToBuyGoodsDetailActivity(getContext(),mGoods.getId());
                }
            });
        }
        public void bind(Goods buyGoods){
            mGoods = buyGoods;
            mGoodsNameTv.setText(buyGoods.getName());
            mSchoolNameTv.setText(buyGoods.getSchoolName());
            mReleaseTimeTv.setText(buyGoods.getReleaseDisplayTime());
        }
    }
}

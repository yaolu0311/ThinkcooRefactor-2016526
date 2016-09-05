package com.thinkcoo.mobile.presentation.views.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerTradeComponent;
import com.thinkcoo.mobile.injector.modules.TradeModule;
import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePagedPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.SellGoodsRecommendPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.GoodsRecommendView;
import com.thinkcoo.mobile.presentation.views.activitys.TradeMainActivity;
import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/7/25.
 */
public class SellGoodsRecommendFragment extends BaseLcePagedFragment<Goods> implements GoodsRecommendView {

    public static final String TAG = "SellGoodsRecommendFragment";

    @Inject
    SellGoodsRecommendPresenter mSellGoodsRecommendPresenter;

    @Override
    protected BaseLcePagedPresenter provideBaseLcePagedPresenter() {
        return mSellGoodsRecommendPresenter;
    }

    @Override
    protected LceAdapterViewBind<Goods> provideLceAdapterViewBind() {

        return new LceAdapterViewBind<Goods>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new InternalViewHolder(inflateView(parent));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, Goods goods) {
                ((InternalViewHolder) holder).bind(goods);
            }
        };
    }

    private View inflateView(ViewGroup parent) {
        return getActivity().getLayoutInflater().inflate(R.layout.item_list_trade, parent,false);
    }

    @Override
    protected void initInject() {
        DaggerTradeComponent.builder().applicationComponent(getFragmentInjectHelper().getApplicationComponent()).tradeModule(new TradeModule()).activityModule(getFragmentInjectHelper().getActivityModule()).build().inject(this);
    }

    private class InternalViewHolder extends RecyclerView.ViewHolder {

        private TextView mGoodsNameTv;
        private TextView mSchoolNameTv;
        private TextView mReleaseTimeTv;
        private TextView mPriceTv;

        private ImageView mGoodsPhotoIv;


        private Goods mGoods;


        public InternalViewHolder(View rootView) {

            super(rootView);
            mGoodsNameTv = (TextView) rootView.findViewById(R.id.ac_text_trade_goods_name);
            mSchoolNameTv = (TextView) rootView.findViewById(R.id.ac_text_trade_school);
            mReleaseTimeTv = (TextView) rootView.findViewById(R.id.ac_text_trade_time);
            mPriceTv = (TextView) rootView.findViewById(R.id.ac_text_trade_price);

            mGoodsPhotoIv = (ImageView) rootView.findViewById(R.id.ac_image_trade);

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentNavigatorHelper().getNavigator().navigateToSellGoodsDetailActivity(getContext(),mGoods.getId());
                }
            });
        }

        public void bind(Goods goods) {

            mGoods = goods;

            Glide.with(SellGoodsRecommendFragment.this).load(goods.getIconUrl()).into(mGoodsPhotoIv);

            mGoodsNameTv.setText(goods.getName());
            mReleaseTimeTv.setText(goods.getReleaseDisplayTime());
            mSchoolNameTv.setText(goods.getSchoolName());
            mPriceTv.setText(goods.getPrice());
        }
    }
    public void refreshOnLocationChanged(){
        loadData(false);
    }

    @Override
    public Location getLocation() {
        return ((TradeMainActivity)getActivity()).getLocation();
    }
}

package com.thinkcoo.mobile.presentation.views.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerTradeComponent;
import com.thinkcoo.mobile.injector.modules.TradeModule;
import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.model.entity.MyCollectGoods;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePagedPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.MyCollectGoodsPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.MyCollectGoodsView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Robert.yao on 2016/8/17.
 */
public class MyCollectGoodsFragment extends BaseLcePagedFragment<MyCollectGoods> implements MyCollectGoodsView<MyCollectGoods> {

    private static final String KEY_TYPE = "TYPE";


    public static MyCollectGoodsFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(KEY_TYPE, type);
        MyCollectGoodsFragment fragment = new MyCollectGoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Inject
    MyCollectGoodsPresenter mMyCollectGoodsPresenter;
    private int type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getTypeFromArguments();
    }

    private int getTypeFromArguments() {
        return getArguments().getInt(KEY_TYPE, Goods.SELL);
    }

    @Override
    protected BaseLcePagedPresenter provideBaseLcePagedPresenter() {
        return mMyCollectGoodsPresenter;
    }

    @Override
    protected LceAdapterViewBind<MyCollectGoods> provideLceAdapterViewBind() {

        if (type == Goods.SELL) {
            return createSellLceAdapterViewBind();
        } else {
            return createBuyLceAdapterViewBind();
        }


    }

    private LceAdapterViewBind<MyCollectGoods> createBuyLceAdapterViewBind() {
        return new LceAdapterViewBind<MyCollectGoods>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new InnerBuyViewHolder(inflateBuyView(parent));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, MyCollectGoods myCollectGoods) {
                ((InnerViewHolder) holder).bind(myCollectGoods);
            }
        };
    }

    private LceAdapterViewBind<MyCollectGoods> createSellLceAdapterViewBind() {
        return new LceAdapterViewBind<MyCollectGoods>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new InnerSellViewHolder(inflateSellView(parent));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, MyCollectGoods myCollectGoods) {
                ((InnerViewHolder) holder).bind(myCollectGoods);
            }
        };
    }

    private View inflateSellView(ViewGroup viewGroup) {
        return getActivity().getLayoutInflater().inflate(R.layout.item_my_collect_sell_goods, viewGroup, false);
    }

    private View inflateBuyView(ViewGroup viewGroup) {
        return getActivity().getLayoutInflater().inflate(R.layout.item_my_collect_buy_goods, viewGroup, false);
    }

     abstract class InnerViewHolder extends RecyclerView.ViewHolder {

        private MyCollectGoods mMyCollectGoods;

        public InnerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoDetail(mMyCollectGoods);
                }
            });
        }

        public void bind(MyCollectGoods myCollectGoods) {
            mMyCollectGoods = myCollectGoods;
            bindToView(myCollectGoods);
        }
        protected abstract void bindToView(MyCollectGoods myCollectGoods);
    }

     class InnerSellViewHolder extends InnerViewHolder {

        @Bind(R.id.ac_image_trade)
        ImageView mAcImageTrade;
        @Bind(R.id.ac_text_trade_goods_name)
        TextView mAcTextTradeGoodsName;
        @Bind(R.id.ac_text_trade_school)
        TextView mAcTextTradeSchool;
        @Bind(R.id.ac_text_trade_price)
        TextView mAcTextTradePrice;
        @Bind(R.id.ac_text_trade_time)
        TextView mAcTextTradeTime;

        public InnerSellViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindToView(MyCollectGoods myCollectGoods) {
            Glide.with(MyCollectGoodsFragment.this).load(myCollectGoods.getIconUrl()).into(mAcImageTrade);
            mAcTextTradeGoodsName.setText(myCollectGoods.getName());
            mAcTextTradeSchool.setText(myCollectGoods.getSchoolName());
            mAcTextTradePrice.setText(myCollectGoods.getDisplayPrice());
            mAcTextTradeTime.setText(myCollectGoods.getReleaseDisplayTime());
        }
    }


    class InnerBuyViewHolder extends InnerViewHolder {

        @Bind(R.id.ac_text_trade_goodsbuy_name)
        TextView mAcTextTradeGoodsbuyName;
        @Bind(R.id.ac_text_tradebuy_school)
        TextView mAcTextTradebuySchool;
        @Bind(R.id.ac_text_tradebuy_time)
        TextView mAcTextTradebuyTime;

        public InnerBuyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindToView(MyCollectGoods myCollectGoods) {
            mAcTextTradeGoodsbuyName.setText(myCollectGoods.getName());
            mAcTextTradebuySchool.setText(myCollectGoods.getSchoolName());
            mAcTextTradebuyTime.setText(myCollectGoods.getReleaseDisplayTime());
        }
    }

    private void gotoDetail(MyCollectGoods myCollectGoods) {

    }

    @Override
    protected void initInject() {
        DaggerTradeComponent.builder().applicationComponent(getFragmentInjectHelper().getApplicationComponent())
                .tradeModule(new TradeModule())
                .activityModule(getFragmentInjectHelper().getActivityModule())
                .build()
                .inject(this);
    }

    @Override
    public int getType() {
        return type;
    }
}

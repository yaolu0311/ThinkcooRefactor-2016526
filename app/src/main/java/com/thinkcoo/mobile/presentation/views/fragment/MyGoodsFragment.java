package com.thinkcoo.mobile.presentation.views.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerTradeComponent;
import com.thinkcoo.mobile.injector.modules.TradeModule;
import com.thinkcoo.mobile.model.entity.MyGoods;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePagedPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.MyGoodsPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.MyGoodsView;
import com.thinkcoo.mobile.utils.PublicUIUtil;

import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/8/9.
 */
public class MyGoodsFragment extends BaseLcePagedFragment<MyGoods> implements MyGoodsView<MyGoods> {

    public static final String MY_GOODS_TYPE_KEY = "my_goods_type_key";

    public static Fragment getInstance(String myGoodsType) {
        Bundle bundle = new Bundle();
        bundle.putString(MY_GOODS_TYPE_KEY, myGoodsType);
        Fragment fragment = new MyGoodsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Inject
    MyGoodsPresenter mMyGoodsPresenter;
    @Inject
    PublicUIUtil mPublicUIUtil;

    private String mType;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString(MY_GOODS_TYPE_KEY);
    }

    @Override
    protected BaseLcePagedPresenter provideBaseLcePagedPresenter() {
        return mMyGoodsPresenter;
    }

    @Override
    protected LceAdapterViewBind<MyGoods> provideLceAdapterViewBind() {
        if (String.valueOf(MyGoods.BUY).equals(mType)) {
            return createBuyLceAdapterViewBind();
        } else {
            return createSellLceAdapterViewBind();
        }
    }

    private LceAdapterViewBind<MyGoods> createBuyLceAdapterViewBind() {
        return new LceAdapterViewBind<MyGoods>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyGoodsBuyItemViewHolder(inflateBuyItemView(parent));
            }
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, MyGoods myGoods) {
                ((MyGoodsBuyItemViewHolder) holder).bind(myGoods);
            }
        };
    }

    private LceAdapterViewBind<MyGoods> createSellLceAdapterViewBind() {
        return new LceAdapterViewBind<MyGoods>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyGoodsSellItemViewHolder(inflateSellItemView(parent));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, MyGoods goods) {
                ((MyGoodsSellItemViewHolder) holder).bind(goods);
            }
        };
    }

    private View inflateSellItemView(ViewGroup viewGroup) {
        return getActivity().getLayoutInflater().inflate(R.layout.item_my_goods_sell, viewGroup, false);
    }

    private View inflateBuyItemView(ViewGroup viewGroup) {
        return getActivity().getLayoutInflater().inflate(R.layout.item_my_goods_buy, viewGroup, false);
    }

    @Override
    protected void initInject() {
        DaggerTradeComponent.builder().applicationComponent(getFragmentInjectHelper().getApplicationComponent())
                .tradeModule(new TradeModule())
                .activityModule(getFragmentInjectHelper().getActivityModule())
                .build().inject(this);
    }

    class MyGoodsSellItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_my_sale_list_img_body_pic)
        ImageView mItemMySaleListImgBodyPic;
        @Bind(R.id.item_my_sale_list_txt_goods_name)
        TextView mItemMySaleListTxtGoodsName;
        @Bind(R.id.item_my_sale_list_txt_goods_price)
        TextView mItemMySaleListTxtGoodsPrice;
        @Bind(R.id.item_my_sale_list_txt_goods_browsecounts)
        TextView mItemMySaleListTxtGoodsBrowsecounts;
        @Bind(R.id.item_my_sale_list_txt_goods_createtime)
        TextView mItemMySaleListTxtGoodsCreatetime;
        @Bind(R.id.item_my_sale_list_txt_goods_sold_out)
        TextView mItemMySaleListTxtGoodsSoldOut;
        @Bind(R.id.item_my_sale_list_txt_delete)
        TextView mItemMySaleListTxtDelete;
        @Bind(R.id.item_my_sale_list_txt_refresh)
        TextView mItemMySaleListTxtRefresh;
        @Bind(R.id.item_my_sale_list_txt_update)
        TextView mItemMySaleListTxtUpdate;

        private MyGoods mBindGoods;

        public MyGoodsSellItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setupItemClick(itemView,mBindGoods);
        }

        public void bind(MyGoods goods) {
            mBindGoods = goods;
            Glide.with(MyGoodsFragment.this).load(goods.getIconUrl()).into(mItemMySaleListImgBodyPic);
            setupTextViewsDisplayText(goods);
            setupNameAndPriceColor(goods);
        }

        private void setupNameAndPriceColor(MyGoods goods) {
            if (goods.isPutawaying()) {
                mItemMySaleListTxtGoodsName.setTextColor(getActivity().getResources().getColor(R.color.text_1));
                mItemMySaleListTxtGoodsPrice.setTextColor(getActivity().getResources().getColor(R.color.red));
            } else {
                mItemMySaleListTxtGoodsName.setTextColor(getActivity().getResources().getColor(R.color.text_3));
                mItemMySaleListTxtGoodsPrice.setTextColor(getActivity().getResources().getColor(R.color.text_3));
            }
        }

        private void setupTextViewsDisplayText(MyGoods goods) {

            mItemMySaleListTxtGoodsName.setText(goods.getName());
            mItemMySaleListTxtGoodsPrice.setText("￥" + mPublicUIUtil.makePriceString(goods.getPrice()) + "元");
            mItemMySaleListTxtGoodsBrowsecounts.setText(goods.getBrowseCount() + "次");
            mItemMySaleListTxtGoodsCreatetime.setText(goods.getReleaseDisplayTime());
            mItemMySaleListTxtGoodsSoldOut.setText(goods.isPutawaying() ? "下架" : "上架");
            mItemMySaleListTxtDelete.setText("删除");
            mItemMySaleListTxtRefresh.setText("刷新");
            mItemMySaleListTxtUpdate.setText("修改");

        }

        @OnClick({R.id.item_my_sale_list_txt_goods_sold_out, R.id.item_my_sale_list_txt_delete, R.id.item_my_sale_list_txt_refresh, R.id.item_my_sale_list_txt_update})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_my_sale_list_txt_goods_sold_out:
                    mMyGoodsPresenter.soldOut(mBindGoods);
                    break;
                case R.id.item_my_sale_list_txt_delete:
                    mMyGoodsPresenter.delete(mBindGoods);
                    break;
                case R.id.item_my_sale_list_txt_refresh:
                    mMyGoodsPresenter.refresh(mBindGoods);
                    break;
                case R.id.item_my_sale_list_txt_update:
                    break;
            }
        }
    }


    class MyGoodsBuyItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_release_book_list_txt_goods_title)
        TextView mItemReleaseBookListTxtGoodsTitle;
        @Bind(R.id.item_release_book_list_txt_goods_price)
        TextView mItemReleaseBookListTxtGoodsPrice;
        @Bind(R.id.item_release_book_list_txt_goods_browcounts)
        TextView mItemReleaseBookListTxtGoodsBrowcounts;
        @Bind(R.id.item_release_book_list_txt_goods_createtime)
        TextView mItemReleaseBookListTxtGoodsCreatetime;

        private MyGoods mBindData;

        public MyGoodsBuyItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            setupItemClick(itemView,mBindData);
        }

        public void bind(MyGoods myGoods) {
            mBindData = myGoods;
            setupTextViewsDisplayText(myGoods);
        }

        private void setupTextViewsDisplayText(MyGoods myGoods) {

            mItemReleaseBookListTxtGoodsTitle.setText(myGoods.getName());
            mItemReleaseBookListTxtGoodsPrice.setText("￥" + mPublicUIUtil.makePriceString(myGoods.getPrice()) + "元");
            mItemReleaseBookListTxtGoodsBrowcounts.setText(myGoods.getBrowseCount()+"次");
            mItemReleaseBookListTxtGoodsCreatetime.setText(myGoods.getReleaseDisplayTime());

        }

        @OnClick({R.id.item_release_book_list_txt_delete, R.id.item_release_book_list_txt_refresh, R.id.item_release_book_list_txt_update})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_release_book_list_txt_delete:
                    mMyGoodsPresenter.delete(mBindData);
                    break;
                case R.id.item_release_book_list_txt_refresh:
                    mMyGoodsPresenter.refresh(mBindData);
                    break;
                case R.id.item_release_book_list_txt_update:
                    getFragmentNavigatorHelper().getNavigator().navigateToMyGoodsDetailActivityEdit(getContext(),mBindData);
                    break;
            }
        }
    }

    @Override
    public String getMyGoodsType() {
        return mType;
    }
    @Override
    public void showProgressDialog(int stringResId) {
        getFragmentHintHelper().showProgressDialog(stringResId);
    }
    @Override
    public void hideProgressDialogIfShowing() {
        getFragmentHintHelper().hideProgressDialogIfShowing();
    }
    @Override
    public void showToast(String errorMsg) {
        getFragmentHintHelper().showLongToast(errorMsg);
    }
    @Override
    public void refreshItem(MyGoods myGoods) {
        List<MyGoods> myGoodsList = mRecyclerViewAdapter.getDataList();
        for (int i = 0; i < myGoodsList.size(); i++) {
            MyGoods oldItem = myGoodsList.get(i);
            if (oldItem.getId().equals(myGoods.getId())){
                myGoodsList.set(i,myGoods);
                mRecyclerViewAdapter.notifyItemChanged(i);
            }
        }
    }

    @Override
    public void deleteItem(MyGoods myGoods) {
        List<MyGoods> myGoodsList = mRecyclerViewAdapter.getDataList();
        Iterator<MyGoods> iterator = myGoodsList.iterator();
        int i = 0 ;
        while (iterator.hasNext()){
            MyGoods oldItem = iterator.next();
            if (oldItem.getId().equals(myGoods.getId())){
                iterator.remove();
                mRecyclerViewAdapter.notifyItemRemoved(i);
            }
            i++;
        }
    }

    private void setupItemClick(View itemView,final MyGoods myGoods) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentNavigatorHelper().getNavigator().navigateToMyGoodsDetailActivity(getActivity(), myGoods);
            }
        });
    }
}

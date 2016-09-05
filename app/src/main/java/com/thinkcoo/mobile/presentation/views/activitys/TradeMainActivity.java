package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerTradeComponent;
import com.thinkcoo.mobile.injector.modules.TradeModule;
import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.model.entity.GoodsCategory;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.presentation.mvp.presenters.TradeMainPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.TradeMainView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.component.WrapContentLayoutManager;
import com.thinkcoo.mobile.presentation.views.fragment.BuyGoodsRecommendFragment;
import com.thinkcoo.mobile.presentation.views.fragment.SellGoodsRecommendFragment;
import com.thinkcoo.mobile.presentation.views.fragment.WaitLocationFragment;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/7/18.
 */
public class TradeMainActivity extends BaseActivity implements TradeMainView {

    @Bind(R.id.img_back_trade)
    ImageView mImgBackTrade;
    @Bind(R.id.ac_textview_sell)
    TextView mAcTextviewSell;
    @Bind(R.id.ac_textview_buy)
    TextView mAcTextviewBuy;
    @Bind(R.id.iv_titleComplete_trade)
    ImageView mIvTitleCompleteTrade;
    @Bind(R.id.ac_trade_location)
    TextView mAcTradeLocation;
    @Bind(R.id.ac_image_trade_location)
    ImageView mAcImageTradeLocation;
    @Bind(R.id.ac_layout_trade_location)
    RelativeLayout mAcLayoutTradeLocation;
    @Bind(R.id.ac_Textview_trade_index_select)
    RelativeLayout mAcTextviewTradeIndexSelect;
    @Bind(R.id.rv_category)
    RecyclerView mRvCategory;

    @Inject
    TradeMainPresenter mTradeMainPresenter;

    SellGoodsRecommendFragment mSellGoodsRecommendFragment;
    BuyGoodsRecommendFragment mBuyGoodsRecommendFragment;
    WaitLocationFragment mWaitLocationFragment;


    private Location mBaiduLocation;
    private Location mSelectLocation = Location.NULL_LOCATION;

    private GoodsCategoryAdapter mCategoryAdapter;


    private byte mCurrentSelectGoodsType = Goods.SELL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_main_new);
        ButterKnife.bind(this);
        mTradeMainPresenter.getLocation();
        initCategoryView();
        initFragment();
    }

    private void initCategoryView() {
        RecyclerView.LayoutManager gridLayoutManager = new WrapContentLayoutManager(this,4);
        mRvCategory.setLayoutManager(gridLayoutManager);
        mCategoryAdapter = new GoodsCategoryAdapter(GoodsCategory.createList(true));
        mRvCategory.setAdapter(mCategoryAdapter);
    }

    private void initFragment() {
        mSellGoodsRecommendFragment = new SellGoodsRecommendFragment();
        mBuyGoodsRecommendFragment = new BuyGoodsRecommendFragment();
        mWaitLocationFragment = new WaitLocationFragment();
        addFragment(R.id.ac_trade_index_framelayout, mWaitLocationFragment);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mTradeMainPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerTradeComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).tradeModule(new TradeModule()).build().inject(this);
    }

    public static Intent getJumpIntent(Context context) {
        return new Intent(context, TradeMainActivity.class);
    }

    private void changeGoodsTypeTvStatus(byte changeTo) {
        if (mCurrentSelectGoodsType == changeTo) {
            return;
        }
        if (changeTo == Goods.SELL) {
            mAcTextviewSell.setBackgroundDrawable(getResources().getDrawable(R.drawable.textviewborder_white));
            mAcTextviewBuy.setBackgroundDrawable(getResources().getDrawable(R.drawable.textviewborder_blue));
            mAcTextviewSell.setTextColor(getResources().getColor(R.color.color_blue));
            mAcTextviewBuy.setTextColor(getResources().getColor(R.color.color_white));
        } else {
            mAcTextviewSell.setBackgroundDrawable(getResources().getDrawable(R.drawable.textviewborder_blue));
            mAcTextviewBuy.setBackgroundDrawable(getResources().getDrawable(R.drawable.textviewborder_white));
            mAcTextviewSell.setTextColor(getResources().getColor(R.color.color_white));
            mAcTextviewBuy.setTextColor(getResources().getColor(R.color.color_blue));
        }
        mCurrentSelectGoodsType = changeTo;
    }

    @Override
    public void setLocation(Location location) {
        mBaiduLocation = location;
        mSelectLocation = location;
        mAcTradeLocation.setText(location.getCity());
        showSellAndBuyFragment();
    }

    private void showSellAndBuyFragment() {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.add(R.id.ac_trade_index_framelayout, mBuyGoodsRecommendFragment, BuyGoodsRecommendFragment.TAG);
        fragmentTransaction.add(R.id.ac_trade_index_framelayout, mSellGoodsRecommendFragment, SellGoodsRecommendFragment.TAG);
        fragmentTransaction.show(mSellGoodsRecommendFragment).hide(mWaitLocationFragment).hide(mBuyGoodsRecommendFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void getLocationFailure() {
        mAcTradeLocation.setText(R.string.get_loacation_failure);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            getLocationAndRefreshFragmentAndCategory(data);
        }
    }

    private void getLocationAndRefreshFragmentAndCategory(Intent data) {
        if (data.hasExtra(SelectLocationActivity.RESULT_LOCATION_EXTRA_KEY)) {
            Location location = data.getParcelableExtra(SelectLocationActivity.RESULT_LOCATION_EXTRA_KEY);
            if (!locationEquals(mSelectLocation,location)) {
                mSellGoodsRecommendFragment.refreshOnLocationChanged();
                mBuyGoodsRecommendFragment.refreshOnLocationChanged();
                mSelectLocation = location;
            }
            refreshCategory(location);
        }
    }

    private void refreshCategory(Location location) {
        if (null == location || null == mBaiduLocation){
            return;
        }
        //当用户手动选择的地址跟百度定位的一样，那么现实"附件"选项
        if (locationEquals(mBaiduLocation,location)){
            mCategoryAdapter.showNearby();
        }else {
            mCategoryAdapter.hideNearby();
        }
    }

    public Location getLocation() {
        return mSelectLocation;
    }

    @OnClick({R.id.img_back_trade, R.id.ac_textview_sell, R.id.ac_textview_buy, R.id.iv_titleComplete_trade, R.id.ac_trade_location, R.id.ac_image_trade_location, R.id.ac_layout_trade_location, R.id.ac_Textview_trade_index_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_trade:
                finish();
                break;
            case R.id.ac_textview_sell:
                changeGoodsTypeTvStatus(Goods.SELL);
                changeToSellFragment();
                break;
            case R.id.ac_textview_buy:
                changeGoodsTypeTvStatus(Goods.BUY);
                changeToBuyFragment();
                break;
            case R.id.iv_titleComplete_trade:
                mNavigator.navigatorMyTradeActivity(this);
                break;
            case R.id.ac_trade_location:
                if (null != mSelectLocation)
                    mNavigator.navigateToSelectLocationActivity(this, mSelectLocation);
                break;
            case R.id.ac_image_trade_location:
                break;
            case R.id.ac_layout_trade_location:
                break;
            case R.id.ac_Textview_trade_index_select:
                mNavigator.navigateToGoodsSearchActivity(this,mCurrentSelectGoodsType,mSelectLocation);
                break;
        }
    }

    private void changeToBuyFragment() {
        getFragmentTransaction().show(mBuyGoodsRecommendFragment).hide(mSellGoodsRecommendFragment).hide(mWaitLocationFragment).commit();
    }

    private void changeToSellFragment() {
        getFragmentTransaction().show(mSellGoodsRecommendFragment).hide(mBuyGoodsRecommendFragment).hide(mWaitLocationFragment).commit();
    }

    private class GoodsCategoryAdapter extends RecyclerView.Adapter<GoodsCategoryVH>{

        List<GoodsCategory> mGoodsCategoryList;

        public GoodsCategoryAdapter(List<GoodsCategory> goodsCategoryList) {
            mGoodsCategoryList = goodsCategoryList;
        }

        @Override
        public GoodsCategoryVH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GoodsCategoryVH(getLayoutInflater().inflate(R.layout.item_trade_gridview_category,parent,false));
        }

        @Override
        public void onBindViewHolder(GoodsCategoryVH holder, int position) {
            GoodsCategory goodsCategory = mGoodsCategoryList.get(position);
            holder.bind(goodsCategory);
        }

        @Override
        public int getItemCount() {
            return mGoodsCategoryList.size();
        }
        public void showNearby(){
            mGoodsCategoryList = GoodsCategory.createList(true);
            notifyDataSetChanged();
        }
        public void hideNearby(){
            mGoodsCategoryList = GoodsCategory.createList(false);
            notifyDataSetChanged();
        }
    }

    private class GoodsCategoryVH  extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        private TextView mTextView;

        public GoodsCategoryVH(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.ac_image_trade_category);
            mTextView = (TextView) itemView.findViewById(R.id.ac_text_trade_category_name);
        }

        public void bind(GoodsCategory goodsCategory){
            mImageView.setBackgroundResource(goodsCategory.getImageHead());
            mTextView.setText(goodsCategory.getCategoryName());
        }
    }

    private boolean locationEquals(Location location1 , Location location2 ){
        return location1.getCity().equals(location2.getCity());
    }
}

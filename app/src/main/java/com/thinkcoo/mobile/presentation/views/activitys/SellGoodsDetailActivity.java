package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerTradeComponent;
import com.thinkcoo.mobile.injector.modules.TradeModule;
import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.model.entity.serverresponse.SellGoodsDetailResponse;
import com.thinkcoo.mobile.presentation.mvp.presenters.SellGoodsDetailPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.GoodsDetailView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseLceActivity;
import com.thinkcoo.mobile.utils.PublicUIUtil;
import com.yz.im.IMHelper;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/8/18.
 */
public class     SellGoodsDetailActivity extends BaseLceActivity<SellGoodsDetailResponse> implements GoodsDetailView<SellGoodsDetailResponse>{

    private static final String TAG = "SellGoodsDetailActivity";
    private static final String KEY_GOODS_ID = "GOODS_ID";

    public static Intent getJumpIntent(Context context , String goodsId){

        Intent intent = new Intent(context,SellGoodsDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_GOODS_ID,goodsId);
        intent.putExtras(bundle);

        return  intent;

    }

    @Bind(R.id.convenientBanner)
    ConvenientBanner mConvenientBanner;
    @Bind(R.id.sell_goods_details_relativelayout_one)
    RelativeLayout mSellGoodsDetailsRelativelayoutOne;
    @Bind(R.id.sell_goods_name)
    TextView mSellGoodsName;
    @Bind(R.id.sell_goods_price_trade)
    TextView mSellGoodsPriceTrade;
    @Bind(R.id.linearLayout3)
    LinearLayout mLinearLayout3;
    @Bind(R.id.sell_goods_timer)
    TextView mSellGoodsTimer;
    @Bind(R.id.sell_goods_scan)
    TextView mSellGoodsScan;
    @Bind(R.id.linearLayout7)
    LinearLayout mLinearLayout7;
    @Bind(R.id.linearLayout1)
    LinearLayout mLinearLayout1;
    @Bind(R.id.sell_goods_sort)
    TextView mSellGoodsSort;
    @Bind(R.id.linearLayout2)
    LinearLayout mLinearLayout2;
    @Bind(R.id.sell_goods_old)
    TextView mSellGoodsOld;
    @Bind(R.id.linearLayout120)
    LinearLayout mLinearLayout120;
    @Bind(R.id.sell_goods_description_title)
    TextView mSellGoodsDescriptionTitle;
    @Bind(R.id.sell_goods_description)
    TextView mSellGoodsDescription;
    @Bind(R.id.linearLayout6)
    LinearLayout mLinearLayout6;
    @Bind(R.id.sell_goods_seller)
    TextView mSellGoodsSeller;
    @Bind(R.id.linearLayout15)
    LinearLayout mLinearLayout15;
    @Bind(R.id.sell_goods_school)
    TextView mSellGoodsSchool;
    @Bind(R.id.linearlayout7)
    LinearLayout mLinearlayout7;
    @Bind(R.id.sell_goods_address_text)
    TextView mSellGoodsAddressText;
    @Bind(R.id.linearLayout5)
    LinearLayout mLinearLayout5;
    @Bind(R.id.sell_goods_scrollview)
    ScrollView mSellGoodsScrollview;
    @Bind(R.id.view)
    View mView;
    @Bind(R.id.ac_image_trade_call)
    ImageView mAcImageTradeCall;
    @Bind(R.id.ac_text_trade_call)
    TextView mAcTextTradeCall;
    @Bind(R.id.ac_layout_trade_call)
    LinearLayout mAcLayoutTradeCall;
    @Bind(R.id.ac_image_trade_collect)
    ImageView mAcImageTradeCollect;
    @Bind(R.id.ac_text_trade_collect)
    TextView mAcTextTradeCollect;
    @Bind(R.id.ac_layout_trade_collect)
    LinearLayout mAcLayoutTradeCollect;
    @Bind(R.id.sell_goods_linear)
    LinearLayout mSellGoodsLinear;
    @Bind(R.id.sell_goods_bc)
    TextView mBc;

    @Inject
    IMHelper mIMHelper;
    @Inject
    SellGoodsDetailPresenter mSellGoodsDetailPresenter;
    @Inject
    PublicUIUtil mPublicUIUtil;

    private String mGoodsIdFromIntent;
    private SellGoodsDetailResponse mSellGoodsDetailResponse;


    private String getGoodsIdFromIntent() {
        return getIntent().getExtras().getString(KEY_GOODS_ID);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mSellGoodsDetailPresenter;
    }

    @Override
    protected void initDaggerInject() {

        DaggerTradeComponent.builder()
                .activityModule(getActivityModule())
                .applicationComponent(getApplicationComponent())
                .tradeModule(new TradeModule())
                .build()
                .inject(this);
    }

    @Override
    protected void setTitle(TextView tvTitle) {
        tvTitle.setText("商品");
    }

    @Override
    protected int getRealContentLayoutResId() {
        return R.layout.activity_buy_goods_detail;
    }

    @Override
    public void setData(SellGoodsDetailResponse data) {
        mSellGoodsDetailResponse = data;
        setupViewStatus(data);
        setupViewContent(data);
    }


    private void setupViewStatus(SellGoodsDetailResponse data) {

        mSellGoodsDetailsRelativelayoutOne.setVisibility(View.VISIBLE);

        if (data.isMyGoods()) {
            mAcLayoutTradeCall.setVisibility(View.GONE);
        }else {
            mAcLayoutTradeCall.setVisibility(View.VISIBLE);
        }
        if (data.isCoolected()){
            mAcTextTradeCollect.setText("取消收藏");
        }else {
            mAcTextTradeCollect.setText("收藏");
        }
        mSellGoodsOld.setVisibility(View.GONE);
    }

    private void setupViewContent(SellGoodsDetailResponse data) {

        setupImageBanner(data.getCimmodityImgList());

        mSellGoodsName.setText(data.getCommodityName());
        mSellGoodsPriceTrade.setText(mPublicUIUtil.getGoodsDisplayPrice(data.getPrice()));
        mSellGoodsTimer.setText(mPublicUIUtil.getGoodsFormatTime(data.getPublishTime()));
        mBc.setText(mBc.getText() + data.getBrowseCnt());
        mSellGoodsSort.setText(mPublicUIUtil.getCategoryDisplayString(data.getCommodityCategory()));
        mSellGoodsOld.setText(data.getQuality());
        mSellGoodsDescription.setText(data.getIntroduce());
        mSellGoodsSeller.setText(data.getRealName());
        mSellGoodsSchool.setText(data.getSchool());
        mSellGoodsAddressText.setText(data.getAddress());

    }

    private void setupImageBanner(List<SellGoodsDetailResponse.CimmodityImgListBean> cimmodityImgList) {

        ThinkcooLog.e(TAG,"=== 准备显示 :  " + cimmodityImgList.size() +" 张图片 ");

        mConvenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new NetworkImageHolderView();
            }
        }, cimmodityImgList);
        if (cimmodityImgList.size()>1){
            mConvenientBanner.setPageIndicator(createPageIndicator());
        }
        mConvenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        mConvenientBanner.setCanLoop(false);
    }

    public class NetworkImageHolderView implements Holder<SellGoodsDetailResponse.CimmodityImgListBean> {

        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            mImageView = new ImageView(context);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            return mImageView;
        }
        @Override
        public void UpdateUI(Context context, int position, SellGoodsDetailResponse.CimmodityImgListBean data) {
            Glide.with(context).load(data.getFilePath()).into(mImageView);
        }
    }

    private int[] createPageIndicator() {
        return new int[]{R.mipmap.icon_point,R.mipmap.icon_point_pre};
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        mSellGoodsDetailPresenter.loadDetail(mGoodsIdFromIntent);
    }

    @Override
    protected void getIntentExtras() {
        mGoodsIdFromIntent = getGoodsIdFromIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ac_layout_trade_call, R.id.ac_layout_trade_collect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ac_layout_trade_call:
                mIMHelper.getNavigator().navigateToSingleChatActivity(this,mSellGoodsDetailResponse.getAccountId());
                break;
            case R.id.ac_layout_trade_collect:
                if (mSellGoodsDetailResponse.isCoolected()){
                    mSellGoodsDetailPresenter.unCollect(mSellGoodsDetailResponse.getCollectId());
                }else {
                    mSellGoodsDetailPresenter.collect(String.valueOf(Goods.SELL),mSellGoodsDetailResponse.getCollectId());
                }
                break;
        }
    }

    @Override
    public String getGoodsId() {
        return mGoodsIdFromIntent;
    }
}

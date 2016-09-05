package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerGoodsSearchComponent;
import com.thinkcoo.mobile.injector.modules.GoodsSearchModule;
import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.model.entity.GoodsFilter;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.presentation.mvp.presenters.BlankPresenter;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.component.GoodsFilterView;
import com.thinkcoo.mobile.presentation.views.fragment.GoodsSearchResultFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/8/3.
 */
public class GoodsSearchAndFilterActivity extends BaseActivity {

    public static final String EXTRA_KEY_GOOD_FILTER_VIEW_TYPE = "goodFilterViewType";
    public static final String EXTRA_KEY_GOODS_TYPE = "goodsType";
    public static final String EXTRA_KEY_LOCATION = "location";
    public static final String EXTRA_KEY_SEARCH_KEY = "searchKey";


    public static Intent getJumpIntent(Context context, int goodFilterViewType, int goodsType, String searchKey, Location location) {
        Intent intent = new Intent(context, GoodsSearchAndFilterActivity.class);
        intent.putExtra(EXTRA_KEY_GOOD_FILTER_VIEW_TYPE, goodFilterViewType);
        intent.putExtra(EXTRA_KEY_GOODS_TYPE, goodsType);
        intent.putExtra(EXTRA_KEY_LOCATION, location);
        intent.putExtra(EXTRA_KEY_SEARCH_KEY, searchKey);
        return intent;
    }

    @Bind(R.id.img_back_trade_search)
    ImageView mImgBackTradeSearch;
    @Bind(R.id.ac_edittext_search_title)
    EditText mAcEdittextSearchTitle;
    @Bind(R.id.img_look_pwd)
    ImageView mImgLookPwd;
    @Bind(R.id.ac_textview_trade_comper)
    TextView mAcTextviewTradeComper;
    @Bind(R.id.ac_imageview_trade_comper)
    ImageView mAcImageviewTradeComper;
    @Bind(R.id.filter_content_view)
    LinearLayout mFilterContentView;

    @Inject
    GoodsFilterView mGoodsFilterView;

    private int mGoodFilterViewType;
    private int mGoodsType;
    private Location mLocation;
    private String mSearchKey;

    private GoodsSearchResultFragment mGoodsSearchResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_search_and_filter);
        ButterKnife.bind(this);
        processExtraData();
        initViews();
        initFragment();
    }

    private void processExtraData() {

        mGoodFilterViewType = getIntent().getIntExtra(EXTRA_KEY_GOOD_FILTER_VIEW_TYPE, GoodsFilterView.TYPE_GOODS_SELL);
        mGoodsType = getIntent().getIntExtra(EXTRA_KEY_GOODS_TYPE, Goods.SELL);
        mLocation = getIntent().getParcelableExtra(EXTRA_KEY_LOCATION);
        mSearchKey = getIntent().getStringExtra(EXTRA_KEY_SEARCH_KEY);

    }

    private void initFragment() {
        mGoodsSearchResultFragment = new GoodsSearchResultFragment();
        addFragment(R.id.goods_search_result_content, mGoodsSearchResultFragment);
    }

    private void initViews() {
        mFilterContentView.addView(mGoodsFilterView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mGoodsFilterView.setType(mGoodFilterViewType);
        mAcEdittextSearchTitle.setText(mSearchKey);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return new BlankPresenter();
    }

    @Override
    protected void initDaggerInject() {
        DaggerGoodsSearchComponent.builder().goodsSearchModule(new GoodsSearchModule(this)).applicationComponent(getApplicationComponent()).build().inject(this);
    }

    public GoodsFilter getGoodsFilter() {
        return mGoodsFilterView.getGoodsFilter();
    }

    public Location getLocation() {
        return mLocation;
    }

    public String getSearchKey() {
        return mAcEdittextSearchTitle.getText().toString().trim();
    }

    public int getGoodsType() {
        return mGoodsType;
    }

    @OnClick({R.id.img_back_trade_search, R.id.ac_textview_trade_comper})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_trade_search:
                finish();
                break;
            case R.id.ac_textview_trade_comper:
                mGoodsSearchResultFragment.startSearch();
                break;
        }
    }
}

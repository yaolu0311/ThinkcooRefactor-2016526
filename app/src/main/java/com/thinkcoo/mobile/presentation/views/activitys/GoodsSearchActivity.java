package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerGoodsSearchComponent;
import com.thinkcoo.mobile.injector.modules.GoodsSearchModule;
import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.presentation.mvp.presenters.GoodsSearchPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.GoodsSearchView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.component.GoodsFilterView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/8/2.
 */
public class GoodsSearchActivity extends BaseActivity implements GoodsSearchView {

    private static final String GOODS_TYPE = "goods_type";
    private static final String LOCATION = "location";

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
    @Bind(R.id.ac_history_search)
    TextView mAcHistorySearch;
    @Bind(R.id.ac_clear_history_search)
    TextView mAcClearHistorySearch;
    @Bind(R.id.ac_layout)
    RelativeLayout mAcLayout;
    @Bind(R.id.ac_add_labellay)
    RecyclerView mHistoryRecyclerView;

    private int mGoodsTypeFromIntent;
    private Location mLocationFromIntent;

    private HistoryListAdapter mHistoryListAdapter;

    @Inject
    GoodsSearchPresenter mGoodsSearchPresenter;

    public static Intent getJumpIntent(Context context, int goodsType, Location location) {
        Intent intent = new Intent(context, GoodsSearchActivity.class);
        intent.putExtra(GOODS_TYPE, goodsType);
        intent.putExtra(LOCATION, location);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_trade_select_index);
        ButterKnife.bind(this);
        setupHistoryRecyclerView();
        getIntentExtraData();
        mGoodsSearchPresenter.loadSearchHistory(mGoodsTypeFromIntent);
    }

    private void getIntentExtraData() {
        mGoodsTypeFromIntent = getIntent().getIntExtra(GOODS_TYPE, Goods.SELL);
        mLocationFromIntent = getIntent().getParcelableExtra(LOCATION);
    }

    private void setupHistoryRecyclerView() {

        mHistoryListAdapter = new HistoryListAdapter();
        mHistoryRecyclerView.setAdapter(mHistoryListAdapter);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mHistoryRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mGoodsSearchPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerGoodsSearchComponent.builder().applicationComponent(getApplicationComponent()).goodsSearchModule(new GoodsSearchModule(this)).build().inject(this);
    }

    @Override
    public void setSearchHistoryList(List<String> historyList) {
        mHistoryListAdapter.refresh(historyList);
    }

    private void clearSearchHistory() {
        mGoodsSearchPresenter.clearSearchHistory(mGoodsTypeFromIntent);
    }

    private void addSearchHistory() {
        String searchKey = getSearchKey();
        if (!TextUtils.isEmpty(searchKey)) {
            mGoodsSearchPresenter.addSearchHistory(searchKey, mGoodsTypeFromIntent);
        }
    }
    private String getSearchKey(){
        return mAcEdittextSearchTitle.getText().toString();
    }

    @OnClick({R.id.img_back_trade_search, R.id.ac_textview_trade_comper, R.id.ac_clear_history_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_trade_search:
                finish();
                break;
            case R.id.ac_textview_trade_comper:
                addSearchHistory();
                mNavigator.navigateToGoodsSearchAndFilterActivity(this,getFilterViewType(),mGoodsTypeFromIntent,getSearchKey(),mLocationFromIntent);
                break;
            case R.id.ac_clear_history_search:
                clearSearchHistory();
                break;
        }
    }

    private int getFilterViewType() {
        return mGoodsTypeFromIntent == Goods.BUY ? GoodsFilterView.TYPE_GOODS_BUY:GoodsFilterView.TYPE_GOODS_SELL;
    }

    private class HistoryListAdapter extends RecyclerView.Adapter<HistoryListViewHolder> {

        private List<String> mDataList;

        public HistoryListAdapter() {
            mDataList = new ArrayList<>();
        }

        @Override
        public HistoryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HistoryListViewHolder(inflateView(parent));
        }

        @Override
        public void onBindViewHolder(HistoryListViewHolder holder, int position) {
            String history = mDataList.get(position);
            holder.setHistory(history);
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        public void refresh(List<String> dataList) {
            mDataList = dataList;
            notifyDataSetChanged();
        }
    }

    private TextView inflateView(ViewGroup parent) {
        return (TextView) getLayoutInflater().inflate(R.layout.trade_item_label_tv, parent, false);
    }

    private class HistoryListViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public HistoryListViewHolder(TextView itemView) {
            super(itemView);
            mTextView = itemView;
        }

        public void setHistory(String history) {
            mTextView.setText(history);
        }
    }
}

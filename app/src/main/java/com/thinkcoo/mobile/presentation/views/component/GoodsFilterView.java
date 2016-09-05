package com.thinkcoo.mobile.presentation.views.component;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.layout.MvpLinearLayout;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.GoodsCategory;
import com.thinkcoo.mobile.model.entity.GoodsDistance;
import com.thinkcoo.mobile.model.entity.GoodsFilter;
import com.thinkcoo.mobile.model.entity.GoodsHostSchool;
import com.thinkcoo.mobile.model.entity.GoodsPriceSortRule;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.presentation.mvp.presenters.GoodsFilterPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.IGoodsFilterView;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Robert.yao on 2016/8/2.
 */
public class GoodsFilterView extends MvpLinearLayout<IGoodsFilterView, GoodsFilterPresenter> implements IGoodsFilterView {

    public static final int TYPE_GOODS_BUY = 0x0001;
    public static final int TYPE_GOODS_SELL = 0x0002;
    public static final int TYPE_GOODS_BUY_NEARBY = 0x0003;
    public static final int TYPE_GOODS_SELL_NEARBY = 0x0004;

    private final int GOODS_CATEGORY = 0x0001;
    private final int SCHOOL = 0x0002;
    private final int PRICE = 0x0003;
    private final int DISTANCE = 0x0004;
    private final int NONE = 0x0005;

    GoodsFilterPresenter mGoodsFilterPresenter;

    @Bind(R.id.category_item_view)
    GoodsFilterItemView mCategoryItemView;
    @Bind(R.id.goods_filter_view_line_1)
    View mGoodsFilterViewLine1;
    @Bind(R.id.school_item_view)
    GoodsFilterItemView mSchoolItemView;
    @Bind(R.id.goods_filter_view_line_2)
    View mGoodsFilterViewLine2;
    @Bind(R.id.price_item_view)
    GoodsFilterItemView mPriceItemView;
    @Bind(R.id.goods_filter_view_line_3)
    View mGoodsFilterViewLine3;
    @Bind(R.id.distance_item_view)
    GoodsFilterItemView mDistanceItemView;

    private GoodsFilter mGoodsFilter = GoodsFilter.createDefaule();
    private Location mLocation;

    private PopupWindow mPopupWindow;
    private RecyclerView mPopContentRecyclerView;
    private PopContentAdapter mPopContentAdapter;

    private int mCurrentPop = NONE;
    private int mCurrentType = TYPE_GOODS_SELL;

    public GoodsFilterView(Context context,GoodsFilterPresenter goodsFilterPresenter) {
        super(context);
        View view = inflate(context, R.layout.view_goods_filter, this);
        ButterKnife.bind(this,view);
        initViews();
        mGoodsFilterPresenter = goodsFilterPresenter;
    }

    public void setType(int type){

        mCurrentType = type;

        switch (type) {
            case TYPE_GOODS_BUY:
                viewSetupToBuyType();
                break;
            case TYPE_GOODS_SELL:
                viewSetupToSellByType();
                break;
            case TYPE_GOODS_BUY_NEARBY:
                viewSetupToBuyNearByType();
                break;
            case TYPE_GOODS_SELL_NEARBY:
                viewSetupToSellNearByType();
                break;
            default:
                throw new IllegalArgumentException("Unknown goods filterView type!!!");
        }
    }

    private void initViews() {

        mCategoryItemView.setOnStatusChangeListener(new GoodsFilterItemView.OnStatusChangeListener() {
            @Override
            public void onStatusChange(boolean isOpen) {
                if (isOpen){
                    //resetAllItem();
                    showCategoryPop();
                }else {
                    hidePop();
                }
            }
        });
        mCategoryItemView.setTag(mGoodsFilter.getGoodsCategory());
        mCategoryItemView.setText(mGoodsFilter.getGoodsCategory().getContentName());

        mSchoolItemView.setOnStatusChangeListener(new GoodsFilterItemView.OnStatusChangeListener() {
            @Override
            public void onStatusChange(boolean isOpen) {
                if (isOpen){
                    mGoodsFilterPresenter.loadSchoolList(mLocation);
                }else {
                    hidePop();
                }
            }
        });
        mSchoolItemView.setTag(mGoodsFilter.getGoodsHostSchool());
        mSchoolItemView.setText(mGoodsFilter.getGoodsHostSchool().getContentName());

        mPriceItemView.setOnStatusChangeListener(new GoodsFilterItemView.OnStatusChangeListener() {
            @Override
            public void onStatusChange(boolean isOpen) {
                if (isOpen){
                    //resetAllItem();
                    showPriceRulePop();
                }else {
                    hidePop();
                }
            }
        });
        mPriceItemView.setTag(mGoodsFilter.getGoodsPriceSortRule());
        mPriceItemView.setText(mGoodsFilter.getGoodsPriceSortRule().getContentName());
        mDistanceItemView.setOnStatusChangeListener(new GoodsFilterItemView.OnStatusChangeListener() {
            @Override
            public void onStatusChange(boolean isOpen) {
                if (isOpen) {
                    //resetAllItem();
                    showDistancePop();
                }else {
                    hidePop();
                }
            }
        });
        mDistanceItemView.setTag(mGoodsFilter.getGoodsDistance());
        mDistanceItemView.setText(mGoodsFilter.getGoodsDistance().getContentName());
    }

    private void resetAllItem() {
        mCategoryItemView.reset();
        mSchoolItemView.reset();
        mPriceItemView.reset();
        mDistanceItemView.reset();
    }

    private void hidePop() {
        if (null != mPopupWindow){
            mPopupWindow.dismiss();
        }
    }

    private void viewSetupToSellNearByType() {

        mCategoryItemView.setVisibility(View.VISIBLE);
        mSchoolItemView.setVisibility(View.GONE);
        mPriceItemView.setVisibility(View.VISIBLE);
        mDistanceItemView.setVisibility(View.VISIBLE);

        mGoodsFilterViewLine1.setVisibility(View.GONE);
    }

    private void viewSetupToBuyNearByType() {
        mCategoryItemView.setVisibility(View.VISIBLE);
        mSchoolItemView.setVisibility(View.GONE);
        mPriceItemView.setVisibility(View.GONE);
        mDistanceItemView.setVisibility(View.VISIBLE);

        mGoodsFilterViewLine1.setVisibility(View.GONE);
        mGoodsFilterViewLine2.setVisibility(View.GONE);
    }

    private void viewSetupToSellByType() {

        mCategoryItemView.setVisibility(View.VISIBLE);
        mSchoolItemView.setVisibility(View.VISIBLE);
        mPriceItemView.setVisibility(View.VISIBLE);
        mDistanceItemView.setVisibility(View.GONE);

        mGoodsFilterViewLine3.setVisibility(View.GONE);

    }

    private void viewSetupToBuyType() {

        mCategoryItemView.setVisibility(View.VISIBLE);
        mSchoolItemView.setVisibility(View.VISIBLE);
        mPriceItemView.setVisibility(View.GONE);
        mDistanceItemView.setVisibility(View.GONE);

        mGoodsFilterViewLine2.setVisibility(View.GONE);
        mGoodsFilterViewLine3.setVisibility(View.GONE);

    }

    @Override
    public GoodsFilterPresenter createPresenter() {
        return mGoodsFilterPresenter;
    }

    private void showPriceRulePop() {
        mCurrentPop = PRICE;
        initPopIfNull();
        mPopContentAdapter.refresh(getPriceRuleList());
        showPopUnderThisView();
    }

    private void showDistancePop() {
        mCurrentType = DISTANCE;
        initPopIfNull();
        mPopContentAdapter.refresh(getDistanceList());
        showPopUnderThisView();
    }

    private List<GoodsDistance> getDistanceList() {
        return GoodsDistance.createList();
    }

    private List<GoodsPriceSortRule> getPriceRuleList() {
        return GoodsPriceSortRule.createList();
    }

    @Override
    public void showSchoolPop(List<GoodsHostSchool> goodsHostSchoolList) {
        resetAllItem();
        mCurrentPop = SCHOOL;
        initPopIfNull();
        mPopContentAdapter.refresh(goodsHostSchoolList);
        showPopUnderThisView();
    }

    private void showCategoryPop() {
        mCurrentPop = GOODS_CATEGORY;
        initPopIfNull();
        mPopContentAdapter.refresh(getCategoryList());
        showPopUnderThisView();
    }

    private List<GoodsCategory> getCategoryList() {
        return GoodsCategory.createFilterList();
    }

    private void showPopUnderThisView() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        mPopupWindow.showAsDropDown(this);
    }

    private void initPopIfNull() {
        if (null == mPopupWindow) {
            View contentView = LayoutInflater.from(getContext()).inflate(R.layout.view_goods_filter_pop,null,false);
            mPopupWindow = new PopupWindow(contentView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setFocusable(false);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

            mPopContentRecyclerView = (RecyclerView) contentView.findViewById(R.id.goods_filter_pop_rview);
            mPopContentAdapter = new PopContentAdapter();
            mPopContentRecyclerView.setAdapter(mPopContentAdapter);
            mPopContentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        }
    }

    private class PopContentAdapter<T extends GoodsFilterContent> extends RecyclerView.Adapter<PopContentViewHolder> {

        private List<T> mContentList;

        public PopContentAdapter() {
            this.mContentList = new ArrayList<>();
        }

        @Override
        public PopContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(getContext()).inflate(R.layout.item_goods_filter_content,parent,false);
            return new PopContentViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(PopContentViewHolder holder, int position) {
            T t = mContentList.get(position);
            holder.bind(t);
        }

        @Override
        public int getItemCount() {
            return mContentList.size();
        }

        public void setSelect(T select) {
            select.setSelected(true);
            int selectItemIndex = 0;
            for (int i = 0; i < mContentList.size(); i++) {
                GoodsFilterContent goodsFilterContent = mContentList.get(i);
                if (goodsFilterContent.isSelected()) {
                    goodsFilterContent.setSelected(false);
                    notifyItemChanged(i);
                }
                if (goodsFilterContent.getContentName().equals(select.getContentName())) {
                    selectItemIndex = i;
                }
            }
            notifyItemChanged(selectItemIndex);
        }

        public void refresh(List<T> contentList) {
            mContentList = contentList;
            notifyDataSetChanged();
        }
    }

    private class PopContentViewHolder<T extends GoodsFilterContent> extends RecyclerView.ViewHolder {

        TextView mContentTv;
        ImageView mSelectIm;

        T bindObject;

        public PopContentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopContentAdapter.setSelect(bindObject);
                    setToItemViewTagAndTest(bindObject);
                    closeCurrentPop();
                }
            });
            mContentTv = (TextView) itemView.findViewById(R.id.filter_content_name);
            mSelectIm = (ImageView) itemView.findViewById(R.id.filter_select_iv);
        }

        private void setToItemViewTagAndTest(T bindObject) {

            if (mCurrentPop == GOODS_CATEGORY){
                mCategoryItemView.setText(bindObject.getContentName());
                mCategoryItemView.setTag(bindObject);
            }else if (mCurrentPop == SCHOOL){
                mSchoolItemView.setText(bindObject.getContentName());
                mSchoolItemView.setTag(bindObject);
            }else if (mCurrentPop == PRICE){
                mPriceItemView.setText(bindObject.getContentName());
                mPriceItemView.setTag(bindObject);
            }else if (mCurrentPop == DISTANCE){
                mDistanceItemView.setText(bindObject.getContentName());
                mDistanceItemView.setTag(bindObject);
            }
        }

        void bind(T t) {
            bindObject = t;
            mContentTv.setText(t.getContentName());
            mSelectIm.setVisibility(t.isSelected() ? View.VISIBLE : View.GONE);
        }
    }



    public interface GoodsFilterContent {
        String getContentName();

        boolean isSelected();

        void setSelected(boolean selected);
    }

    public void closeCurrentPop(){

        hidePop();

        switch (mCurrentPop){
            case GOODS_CATEGORY:
                mCategoryItemView.close();
                break;
            case SCHOOL:
                mSchoolItemView.close();
                break;
            case PRICE:
                mPriceItemView.close();
                break;
            case DISTANCE:
                mDistanceItemView.close();
                break;
        }
    }

    public GoodsFilter getGoodsFilter() {

        if (mCategoryItemView.getTag() != null){
            mGoodsFilter.setGoodsCategory((GoodsCategory) mCategoryItemView.getTag());
        }
        if (mSchoolItemView.getTag() != null){
            mGoodsFilter.setGoodsHostSchool((GoodsHostSchool)mSchoolItemView.getTag());
        }
        if (mPriceItemView.getTag() != null){
            mGoodsFilter.setGoodsPriceSortRule((GoodsPriceSortRule)mPriceItemView.getTag());
        }
        if (mDistanceItemView.getTag()!= null){
            mGoodsFilter.setGoodsDistance((GoodsDistance)mDistanceItemView.getTag());
        }

        return mGoodsFilter;
    }

    public void setGoodsFilter(GoodsFilter goodsFilter) {

        mGoodsFilter.setGoodsCategory(goodsFilter.getGoodsCategory() == null ? mGoodsFilter.getGoodsCategory() : goodsFilter.getGoodsCategory());
        mGoodsFilter.setGoodsHostSchool(goodsFilter.getGoodsHostSchool() == null ? mGoodsFilter.getGoodsHostSchool() : goodsFilter.getGoodsHostSchool());
        mGoodsFilter.setGoodsPriceSortRule(goodsFilter.getGoodsHostSchool() == null ? mGoodsFilter.getGoodsPriceSortRule() : goodsFilter.getGoodsPriceSortRule());
        mGoodsFilter.setGoodsDistance(goodsFilter.getGoodsDistance() == null ? mGoodsFilter.getGoodsDistance() : goodsFilter.getGoodsDistance());

        updateFilterItem();
    }

    private void updateFilterItem() {

        if(null != mGoodsFilter.getGoodsCategory()){
            mCategoryItemView.setText(mGoodsFilter.getGoodsCategory().getContentName());
            mCategoryItemView.setTag(mGoodsFilter.getGoodsCategory());
            mCategoryItemView.selected();
        }
        if (null != mGoodsFilter.getGoodsHostSchool()){
            mSchoolItemView.setText(mGoodsFilter.getGoodsHostSchool().getContentName());
            mSchoolItemView.setTag(mGoodsFilter.getGoodsHostSchool());
            mSchoolItemView.selected();
        }
        if (null != mGoodsFilter.getGoodsPriceSortRule()){
            mPriceItemView.setText(mGoodsFilter.getGoodsPriceSortRule().getContentName());
            mPriceItemView.setTag(mGoodsFilter.getGoodsPriceSortRule());
            mPriceItemView.selected();
        }
        if (null != mGoodsFilter.getGoodsDistance()){
            mDistanceItemView.setText(mGoodsFilter.getGoodsDistance().getContentName());
            mDistanceItemView.setTag(mGoodsFilter.getGoodsDistance());
            mDistanceItemView.selected();
        }
    }
}

package com.thinkcoo.mobile.presentation.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemCreator;
import com.thinkcoo.mobile.presentation.LceErrorBundle;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePagedPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePresenter;
import java.util.List;


/**
 * Created by Robert.yao on 2016/7/25.
 */
public abstract class BaseLcePagedFragment<D> extends BaseLceFragment<D> implements Paginate.Callbacks{

    private Paginate mPaginate;
    private boolean isLoadMoreWorking;
    private BaseLcePagedPresenter mBaseLcePagedPresenter;
    private boolean isUserRefresh = false;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupPaginate();
    }

    private void setupPaginate() {
        mPaginate = Paginate.with(mRecyclerView,this)
                .setLoadingTriggerThreshold(mBaseLcePagedPresenter.getPageContent())
                .addLoadingListItem(true)
                .setLoadingListItemCreator(new CustomLoadingListItemCreator()).build();
    }

    @Override
    public void onLoadMore() {
        isLoadMoreWorking = true;
        mSwipeRefreshLayout.setRefreshing(false);
        mBaseLcePagedPresenter.loadMore(isUserRefresh);
    }

    @Override
    public boolean isLoading() {
        return isLoadMoreWorking || isPullToRefreshing();
    }

    @Override
    public boolean hasLoadedAllItems() {
        return mBaseLcePagedPresenter.hasLoadedAllItems();
    }
    @Override
    public void setData(Object data) {
        // keep empty
    }
    @Override
    public void setDataList(List<D> dataList) {
            if (isLoadMoreWorking){
                addDataList(dataList);
                isLoadMoreWorking = false;
                if (hasLoadedAllItems()){
                    isUserRefresh = false;
                }
            }else {
                super.setDataList(dataList);
            }
    }
    private void addDataList(List<D> dataList) {
        mRecyclerViewAdapter.addDataList(dataList);
    }
    private class CustomLoadingListItemCreator implements LoadingListItemCreator{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(com.paginate.R.layout.loading_row, parent, false);
            return new RecyclerView.ViewHolder(view) {};
        }
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            // No binding for default loading row
        }
    }
    @Override
    public void onRefresh() {
        if (!isLoadMoreWorking){
            super.onRefresh();
            isUserRefresh = true;
        }else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
       if (isLoadMoreWorking){
           LceErrorBundle lceErrorBundle = mLceErrorBundleFactory.create(e,getContext());
           String errorMsg = getResources().getString(lceErrorBundle.getErrorMsg());
           showLightError(errorMsg);
           isLoadMoreWorking = false;
       }else {
           super.showError(e, pullToRefresh);
       }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        showLoading(pullToRefresh);
        mBaseLcePagedPresenter.loadFirstPageData(pullToRefresh);
    }
    @Override
    protected BaseLcePresenter providePresenter() {
        mBaseLcePagedPresenter = provideBaseLcePagedPresenter();
        return mBaseLcePagedPresenter;
    }
    protected abstract BaseLcePagedPresenter provideBaseLcePagedPresenter();
    protected void refresh(){
        loadData(false);
    }

}

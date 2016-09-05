package com.thinkcoo.mobile.presentation.views.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceFragment;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.ErrorProcessIntent;
import com.thinkcoo.mobile.presentation.LceErrorBundle;
import com.thinkcoo.mobile.presentation.LceErrorBundleFactory;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePresenter;
import com.thinkcoo.mobile.presentation.mvp.views.BaseLceView;
import com.thinkcoo.mobile.presentation.views.activitys.base.FragmentHintHelper;
import com.thinkcoo.mobile.presentation.views.activitys.base.FragmentInjectHelper;
import com.thinkcoo.mobile.presentation.views.activitys.base.FragmentNavigatorHelper;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Robert.yao on 2016/7/21.
 */
public abstract class BaseLceFragmentV2<D> extends MvpLceFragment implements BaseLceView<D>, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "BaseLceFragment";

    @Inject
    LceErrorBundleFactory mLceErrorBundleFactory;

    @Bind(R.id.content_RecyclerView)
    protected RecyclerView mRecyclerView;
    @Bind(R.id.contentView)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected LceAdapter<D> mRecyclerViewAdapter;

    private ErrorProcessIntent mLceErrorProcessIntent;
    private BaseLcePresenter mBaseLcePresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInject();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_lce, container,false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        setupSwipeRefreshLayout();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isSearchMode())
            loadData(false);
    }

    protected void setupSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }
    protected void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerViewAdapter = new LceAdapter(provideLceAdapterViewBind());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public MvpPresenter createPresenter() {
        mBaseLcePresenter =  providePresenter();
        return mBaseLcePresenter;
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        LceErrorBundle lceErrorBundle = mLceErrorBundleFactory.create(e,getContext());
        String errorMsg = getResources().getString(lceErrorBundle.getErrorMsg());
        mLceErrorProcessIntent = lceErrorBundle.getErrorProcessIntent();
        if (pullToRefresh) {
            mSwipeRefreshLayout.setRefreshing(false);
            showLightError(errorMsg);
        } else {
            buildErrorImg(errorView, lceErrorBundle.getErrorImg());
            errorView.setText(errorMsg);
            animateErrorViewIn();
        }
    }

    private void buildErrorImg(TextView errorView, int errorImg) {
        if (errorImg == LceErrorBundle.NON_IMG) {
            return;
        }
        Drawable drawable = getResources().getDrawable(errorImg);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        errorView.setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.px_20));
        errorView.setCompoundDrawables(null, drawable, null, null);
    }

    @Override
    protected void onErrorViewClicked() {
        if (null != mLceErrorProcessIntent){
            if (mLceErrorProcessIntent.isStartActivityType()){
                startActivity(mLceErrorProcessIntent.getRealIntent());
            }else {
                getContext().sendBroadcast(mLceErrorProcessIntent.getRealIntent());
            }
        }else {
            super.onErrorViewClicked();
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        showLoading(pullToRefresh);
        mBaseLcePresenter.startLoadData(pullToRefresh);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);
        if (pullToRefresh && !mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void setDataList(List<D> dataList) {
        loadSuccessRestLoadingView();
        mRecyclerViewAdapter.refresh(dataList);
    }

    protected void loadSuccessRestLoadingView() {
        if (isPullToRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            showContent();
        }
    }

    protected boolean isPullToRefreshing() {
        return null != mSwipeRefreshLayout && mSwipeRefreshLayout.isRefreshing();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected class LceAdapter<D> extends RecyclerView.Adapter {

        private List<D> mDataList;
        private LceAdapterViewBind mLceAdapterViewBind;

        public LceAdapter(LceAdapterViewBind lceAdapterViewBind) {
            this.mDataList = new LinkedList<>();
            mLceAdapterViewBind = lceAdapterViewBind;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return  mLceAdapterViewBind.onCreateViewHolder(parent, viewType);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            D d = mDataList.get(position);
            mLceAdapterViewBind.onBindViewHolder(holder, d);
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }
        public void refresh(List<D> dataList) {
            mDataList = dataList;
            notifyDataSetChanged();
        }
        public void addDataList(List<D> dataList){
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
        public List<D> getDataList(){
            return mDataList;
        }
    }


    protected interface LceAdapterViewBind<D> {
        RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
        void onBindViewHolder(RecyclerView.ViewHolder holder, D d);
    }


    @Override
    public void setData(Object data) {
        // keep Empty
    }

    protected FragmentInjectHelper getFragmentInjectHelper(){
        return (FragmentInjectHelper)getActivity();
    }
    protected FragmentNavigatorHelper getFragmentNavigatorHelper(){
        return (FragmentNavigatorHelper)getActivity();
    }
    protected FragmentHintHelper getFragmentHintHelper(){
        return (FragmentHintHelper)getActivity();
    }

    protected abstract LceAdapterViewBind<D> provideLceAdapterViewBind();
    protected abstract BaseLcePresenter providePresenter();
    protected abstract void initInject();
    protected boolean isSearchMode(){
        return false;
    }

}

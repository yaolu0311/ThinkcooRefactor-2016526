package com.thinkcoo.mobile.presentation.views.activitys.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.SortedAndSectionEntity;
import com.thinkcoo.mobile.presentation.views.component.SideBar;
import com.thinkcoo.mobile.presentation.views.fragment.BaseLceFragment;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Robert.yao on 2016/7/26.
 */
public abstract class BaseLceSectionSortedFragment<D extends SortedAndSectionEntity> extends BaseLceFragment<D>{

    private SideBar mSideBar;
    private TextView mSortHeadTv;
    private LceSectionSortedAdapter mLceSectionSortedAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lce_section_sorted,container,false);
        initViews(view);
        setupRecyclerView();
        return view;
    }
    private void setupSideBar(List<String> sortHeadList) {
        mSideBar.setTextView(mSortHeadTv,sortHeadList);
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                mRecyclerView.scrollToPosition(mLceSectionSortedAdapter.getFirstPositionByHead(s));
            }
        });
    }
    private void initViews(View view) {
        mRecyclerView = findView(view,R.id.content_RecyclerView);
        mSideBar =findView(view,R.id.v_firstword_sidrbar);
        mSortHeadTv = findView(view,R.id.v_firstword_dialog);
    }

    protected void setupRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mLceSectionSortedAdapter = new LceSectionSortedAdapter(provideLceSectionSortedAdapterViewBind());
        mRecyclerView.setAdapter(mLceSectionSortedAdapter);
        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mLceSectionSortedAdapter);
        mRecyclerView.addItemDecoration(headersDecor);

    }

    private <T extends View> T findView(View view, int layoutId) {
        return (T)view.findViewById(layoutId);
    }

    @Override
    public void setDataList(List<D> dataList) {
        loadSuccessRestLoadingView();
        setupSideBar(dataList2sortHeadList(dataList));
        mLceSectionSortedAdapter.refresh(dataList);
    }

    private List<String> dataList2sortHeadList(List<D> dataList) {
        LinkedHashSet<String>  linkedHashSet = new LinkedHashSet<>();
        for (D d: dataList) {
            linkedHashSet.add(d.getSectionNameFirstChar());
        }
        return new ArrayList<>(linkedHashSet);
    }

    public interface LceSectionSortedAdapterViewBind<D>{
        RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
        void onBindViewHolder(RecyclerView.ViewHolder holder, D d);
        RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent);
        void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, String sectionName);
    }
    @Override
    protected LceAdapterViewBind<D> provideLceAdapterViewBind() {
        return null;//使用provideLceSectionSortedAdapterViewBind
    }
    protected abstract LceSectionSortedAdapterViewBind<D> provideLceSectionSortedAdapterViewBind();

    private class LceSectionSortedAdapter extends RecyclerView.Adapter implements StickyRecyclerHeadersAdapter {

        List<D> dataList;
        LceSectionSortedAdapterViewBind<D> mLceAdapterViewBind;

        public LceSectionSortedAdapter(LceSectionSortedAdapterViewBind<D> lceAdapterViewBind) {
            this.dataList = new LinkedList<>();
            mLceAdapterViewBind = lceAdapterViewBind;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return mLceAdapterViewBind.onCreateViewHolder(parent,viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            D d = dataList.get(position);
            mLceAdapterViewBind.onBindViewHolder(holder,d);
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            return mLceAdapterViewBind.onCreateHeaderViewHolder(parent);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            D d = dataList.get(position);
            mLceAdapterViewBind.onBindHeaderViewHolder(holder,d.getSectionName());
        }
        @Override
        public int getItemCount() {
            return dataList.size();
        }

        @Override
        public long getHeaderId(int position) {
            D d = dataList.get(position);
            return d.getSectionId();
        }

        public void refresh(List<D> dataList) {
            this.dataList = dataList;
            notifyDataSetChanged();
        }

        public int getFirstPositionByHead(String s) {
            for (int i = 0; i < dataList.size(); i++) {
                SortedAndSectionEntity sortedAndSectionEntity = dataList.get(i);
                if (s.equalsIgnoreCase(sortedAndSectionEntity.getSectionNameFirstChar())){
                    return i;
                }
            }
            return -1;
        }
    }


}

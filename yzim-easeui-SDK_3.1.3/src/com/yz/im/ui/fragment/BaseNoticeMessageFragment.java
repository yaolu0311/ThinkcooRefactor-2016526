package com.yz.im.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.publicmodule.ui.listener.OnActivityTouchListener;
import com.example.administrator.publicmodule.ui.listener.RecyclerTouchListener;
import com.hyphenate.easeui.R;
import com.yz.im.model.entity.serverresponse.NoticeMessageResponse;
import com.yz.im.ui.activity.NoticeMessageActivity;
import com.yz.im.ui.adapter.NoticeMessageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cys on 2016/8/5
 */
public abstract class BaseNoticeMessageFragment extends Fragment implements RecyclerTouchListener.RecyclerTouchListenerHelper {

    protected RecyclerView mRecyclerView;
    protected NoticeMessageAdapter mAdapter;
    private List<NoticeMessageResponse> mList;
    private String mType;
    private RecyclerTouchListener onTouchListener;
    private OnActivityTouchListener touchListener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = setMessageType();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView = createRecyclerView();
        initData();
        lazyLoad();
        return mRecyclerView;
    }

    private RecyclerView createRecyclerView() {
        RecyclerView recyclerView = new RecyclerView(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        recyclerView.setLayoutParams(params);
        return recyclerView;
    }

    protected void initData() {
        mList = new ArrayList<>();
        mAdapter = new NoticeMessageAdapter(getContext(), mList, mType);
        mRecyclerView.setAdapter(mAdapter);
        addItemOnTouchListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.addOnItemTouchListener(onTouchListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        mRecyclerView.removeOnItemTouchListener(onTouchListener);
    }

    public void refreshData(List<NoticeMessageResponse> list) {
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private void addItemOnTouchListener() {
        onTouchListener = new RecyclerTouchListener(getActivity(), mRecyclerView);
        onTouchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
                        // Do something
                    }
                })
                .setSwipeOptionViews(R.id.btn_delete)
                .setSwipeable(R.id.item_notice, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        if (viewID == R.id.btn_delete) {
                            NoticeMessageResponse response = mList.get(position);
                            ((NoticeMessageActivity)getContext()).getPresenter().doWithMessage(response, getDeleteMsgType());
                        }
                    }
                });
    }

    @Override
    public void setOnActivityTouchListener(OnActivityTouchListener listener) {
        this.touchListener = listener;
    }

    protected abstract String setMessageType();

    protected abstract void lazyLoad();

    protected abstract String getDeleteMsgType();
}

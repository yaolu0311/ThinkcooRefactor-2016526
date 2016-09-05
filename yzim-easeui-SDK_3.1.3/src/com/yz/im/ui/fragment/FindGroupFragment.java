package com.yz.im.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.easeui.R;
import com.yz.im.IMHelper;
import com.yz.im.model.entity.serverresponse.FindGroupResponse;
import com.yz.im.ui.adapter.FindGroupAdapter;
import com.yz.im.ui.base.IMNavigator;

import java.util.ArrayList;
import java.util.List;

public class FindGroupFragment extends Fragment implements FindGroupAdapter.JoinGroupInterface{

    public static String KEY_LIST = "key_list";

    private View rootView;
    private RecyclerView mRecyclerView;
    private FindGroupAdapter mAdapter;
    private List<FindGroupResponse> mList;

    private IMNavigator mNavigator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_find_group, null);
        getDataFromIntent();
        initRecyclerView();
        initVariable();
        return rootView;
    }

    private void getDataFromIntent() {
        mList = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null) {
            List<FindGroupResponse> list = bundle.getParcelableArrayList(KEY_LIST);
            if (list != null) {
                mList.clear();
                mList.addAll(list);
            }
        }
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_interest_group);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        setAdapter();
    }

    private void setAdapter() {
        mAdapter = new FindGroupAdapter(getContext(), mList);
        mAdapter.setInterface(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initVariable() {
        mNavigator = IMHelper.getInstance().getNavigator();
    }

    public void setData(List<FindGroupResponse> list){
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void joinGroup(int position, FindGroupResponse response) {
        mNavigator.navigateToSendInviteJoinGroupReasonPage(getContext(), response.getGroupId());
    }

    @Override
    public void onItemClick(int position, FindGroupResponse response) {
        mNavigator.navigateToUnJoinGroupInfoActivity(getContext(), response);
    }

}

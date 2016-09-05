package com.thinkcoo.mobile.presentation.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerResourceComponent;
import com.thinkcoo.mobile.injector.modules.ResourceModule;
import com.thinkcoo.mobile.model.entity.ResourceEntity;
import com.thinkcoo.mobile.presentation.mvp.presenters.LoadResourceListPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.ResourceView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseScheduleDetailActivity;
import com.thinkcoo.mobile.presentation.views.adapter.Schedule.ResourceListAdapter;
import com.thinkcoo.mobile.presentation.views.component.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * Created by Leevin
 * CreateTime: 2016/8/10  15:43
 */
public class ResourceFragment extends BaseFragment implements ResourceView, ResourceListAdapter.OnItemClickListener {

//    @Bind(R.id.add_resource)
    ImageButton mAddResource;
//    @Bind(R.id.resource_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    LoadResourceListPresenter mLoadResourceListPresenter;

    public static ResourceFragment getInstance() {
        return new ResourceFragment();
    }

    public ResourceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        init();
    }

    private void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getHostActivity(),DividerItemDecoration.VERTICAL_LIST));
        ResourceListAdapter adapter = new ResourceListAdapter(getMockData());
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    private List<String> getMockData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i <30 ; i++) {
            list.add("item" + i);
        }
        return list;
    }

    @OnClick(R.id.add_resource)
    public void onClick() {
        getHostActivity().mNavigator.navigateToAddResourceActivity(getActivity());
    }

    public BaseScheduleDetailActivity getHostActivity() {
        return (BaseScheduleDetailActivity) getActivity();
    }

    @Override
    protected int getLayoutId() {
//        return R.layout.layout_resource_view;
        return R.layout.coding;
    }

    @Override
    protected void initDaggerInject() {
        DaggerResourceComponent.builder().resourceModule(new ResourceModule()).applicationComponent(getFragmentInjectHelper().getApplicationComponent()).build().inject(this);
    }

    @Override
    protected MvpBasePresenter generatePresenter() {
        return mLoadResourceListPresenter;
    }

    @Override
    public void setResourceList(List<ResourceEntity> resourceList) {

    }

    @Override
    public void onItemClick(View itemView, int position) {
        Toast.makeText(getHostActivity(), ""+position, Toast.LENGTH_SHORT).show();
    }
}

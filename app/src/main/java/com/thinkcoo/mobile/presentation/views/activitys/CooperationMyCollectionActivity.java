package com.thinkcoo.mobile.presentation.views.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.CooperationCollection;
import com.thinkcoo.mobile.presentation.mvp.presenters.MyCooperationCollectionPresent;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.adapter.cooperation.MyCollectAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CooperationMyCollectionActivity extends BaseActivity implements MvpLceView {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_other)
    TextView tvOther;
    @Bind(R.id.iv_more)
    ImageView ivMore;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.lv_collect_list)
    ListView lvCollectList;
    @Inject
    MyCooperationCollectionPresent mMyCooperationCollectionPresent;
    private List<CooperationCollection> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_mycollection);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        tvTitle.setText(R.string.train_mycollection);
        mlist = new ArrayList<>();
        // TODO: 2016/7/22  先写这个
        MyCollectAdapter myCollectAdapter = new MyCollectAdapter(mlist,this);
        lvCollectList.setAdapter(myCollectAdapter);

        loadMyCooperationCollectionData();

    }


    private void loadMyCooperationCollectionData() {
        mMyCooperationCollectionPresent.loadCooperationCollect();
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mMyCooperationCollectionPresent;
    }

    @Override
    protected void initDaggerInject() {
        // TODO: 2016/7/22
    }

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {

    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }


    @OnClick({R.id.iv_back, R.id.lv_collect_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.lv_collect_list:
                // TODO: 2016/7/22  跳转我的收藏详情界面
                skip();
                break;
        }
    }

    private void skip() {
        lvCollectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 2016/7/22  跳转 fragment 项目

            }
        });
    }


}

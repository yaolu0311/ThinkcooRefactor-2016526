package com.thinkcoo.mobile.presentation.views.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.mvp.presenters.CooperationCompanyDetailPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyInfoFragment extends BaseFragment {


    @Bind(R.id.ac_text_company_name)
    TextView acTextCompanyName;
    @Bind(R.id.ac_text_hangye)
    TextView acTextHangye;
    @Bind(R.id.ac_text_hangye_name)
    TextView acTextHangyeName;
    @Bind(R.id.ac_text_xingzhi)
    TextView acTextXingzhi;
    @Bind(R.id.ac_text_guimo)
    TextView acTextGuimo;
    @Bind(R.id.ac_text_wangzhan)
    TextView acTextWangzhan;
    @Bind(R.id.ac_linee_one)
    View acLineeOne;
    @Bind(R.id.ac_text_dizhi)
    TextView acTextDizhi;
    @Bind(R.id.layout01)
    RelativeLayout layout01;
    @Bind(R.id.ac_linee_twow)
    View acLineeTwow;
    @Bind(R.id.ac_linee_two)
    View acLineeTwo;
    @Bind(R.id.ac_company_text)
    TextView acCompanyText;
    @Bind(R.id.ac_linee_three)
    View acLineeThree;
    @Bind(R.id.ac_text_jianjie)
    TextView acTextJianjie;
    @Bind(R.id.ac_company_image)
    ImageView acCompanyImage;
    @Bind(R.id.layout03)
    RelativeLayout layout03;
    @Inject
    CooperationCompanyDetailPresenter mCooperationCompanyDetailPresenter;

    public CompanyInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_company_infor, null);
        initView();
        ButterKnife.bind(this, view);
        return view;
    }

    private void initView() {
        // TODO: 2016/7/27   bean  这个接口有点小问题   暂时无bean
    }

    private void loadCompanyDetailInfo() {
        mCooperationCompanyDetailPresenter.loadData();

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initDaggerInject() {
        // TODO: 2016/7/27
    }

    @Override
    protected MvpBasePresenter generatePresenter() {
        return mCooperationCompanyDetailPresenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) loadCompanyDetailInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCompanyDetailInfo();
    }
}

package com.thinkcoo.mobile.presentation.views.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/27.
 */
public class PersonFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    public PersonFragment() {
    }

    @Override
    public MvpBasePresenter<MvpView> createPresenter() {
        return null;
    }

    public static PersonFragment newInstance(String param1) {
        PersonFragment fragment = new PersonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        //// TODO: 2016/6/27
        return rootView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_title;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected MvpBasePresenter generatePresenter() {
        return null;
    }

    @Override
    protected void initArguments() {
        //// TODO: 2016/6/27 获取参数
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

package com.thinkcoo.mobile.presentation.views.fragment;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.mvp.presenters.BlankPresenter;

/**
 * Created by Robert.yao on 2016/7/29.
 */
public class WaitLocationFragment extends BaseFragment{

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wait_location;
    }

    @Override
    protected void initDaggerInject() {
        //keep empty
    }
    @Override
    protected MvpBasePresenter generatePresenter() {
        return new BlankPresenter();
    }
}

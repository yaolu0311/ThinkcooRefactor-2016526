package com.thinkcoo.mobile.presentation.views.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.presentation.views.activitys.base.FragmentHintHelper;
import com.thinkcoo.mobile.presentation.views.activitys.base.FragmentInjectHelper;
import com.thinkcoo.mobile.presentation.views.activitys.base.FragmentNavigatorHelper;

public abstract class BaseFragment extends MvpFragment<MvpView,MvpBasePresenter<MvpView>> {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    public BaseFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkFragmentStatus(savedInstanceState);
        if (getArguments() != null) {
            initArguments();
        }
    }

    private void checkFragmentStatus(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                transaction.hide(this);
            } else {
                transaction.show(this);
            }
            transaction.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public MvpBasePresenter<MvpView> createPresenter() {
        initDaggerInject();
        return generatePresenter();
    }

    public FragmentInjectHelper getFragmentInjectHelper(){
        return (FragmentInjectHelper)getActivity();
    }
    public FragmentNavigatorHelper getFragmentNavigatorHelper(){
        return (FragmentNavigatorHelper)getActivity();
    }
    public FragmentHintHelper getFragmentHintHelper(){
        return (FragmentHintHelper)getActivity();
    }

    protected void initArguments(){}

    protected abstract int getLayoutId();
    protected abstract void initDaggerInject();
    protected abstract MvpBasePresenter generatePresenter();
}

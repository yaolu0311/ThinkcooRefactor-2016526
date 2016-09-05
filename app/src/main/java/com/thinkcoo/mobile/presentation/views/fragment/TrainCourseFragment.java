package com.thinkcoo.mobile.presentation.views.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainCourseFragment extends BaseFragment {


    public TrainCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected MvpBasePresenter generatePresenter() {
        return null;
    }

}

package com.thinkcoo.mobile.presentation.views.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkcoo.mobile.R;

/**
 * Created by Leevin
 * CreateTime: 2016/8/17  10:39
 */
public class UploadFragment extends Fragment {

    public static UploadFragment getInstance() {
        return new UploadFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_upload, container, false);
        return root;
    }
}

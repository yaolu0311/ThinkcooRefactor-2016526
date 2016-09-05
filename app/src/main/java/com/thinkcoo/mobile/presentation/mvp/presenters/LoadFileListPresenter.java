package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.presentation.mvp.views.AddResourceView;

import javax.inject.Inject;

/**
 * Created by Leevin
 * CreateTime: 2016/8/13  17:06
 */
public class LoadFileListPresenter extends MvpBasePresenter<AddResourceView>{

    @Inject
    public LoadFileListPresenter() {
    }

    public void loadFileList() {
        if (!isViewAttached()) {
            return;
        }

    }
}

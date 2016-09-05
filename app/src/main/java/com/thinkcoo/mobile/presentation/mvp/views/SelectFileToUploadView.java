package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Leevin
 * CreateTime: 2016/8/11  20:42
 */
public interface SelectFileToUploadView extends MvpView ,BaseHintView,BaseActivityHelpView{

    void uploadFile();
}

package com.thinkcoo.mobile.presentation.mvp.views;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.UserBasicInfo;

/**
 * Created by WYY on 2016/3/22.
 */
public interface CreateCodeView extends MvpView , BaseHintView , BaseActivityHelpView{
    void setUserMainInfo(UserBasicInfo userBasicInfo);
}

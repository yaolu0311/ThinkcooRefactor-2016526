package com.thinkcoo.mobile.presentation.mvp.views;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.UserBasicInfo;

/**
 * Created by Robert.yao on 2016/3/22.
 */
public interface UserMainInfoView extends MvpView , BaseHintView , BaseActivityHelpView{
    void setUserMainInfo(UserBasicInfo userBuasicInfo);
    String getSelectSex();
//    String getLocalPhotoPath();
}

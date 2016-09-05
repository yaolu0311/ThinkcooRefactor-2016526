package com.thinkcoo.mobile.presentation.mvp.views;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.UserBasicInfo;

/**
 * Created by WangYY on 2016/3/22.
 */
public interface UserBasicInfoView extends MvpView , BaseHintView , BaseActivityHelpView{
    void setUserBasicInfo(UserBasicInfo userBasicInfo);
    void resultToUserMainInfoActivity();
    UserBasicInfo getUserBasicInfo();
}

package com.thinkcoo.mobile.presentation.mvp.views;

import com.thinkcoo.mobile.model.entity.UserWorkExperienceEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16.
 */
public interface UserWorkExperienceView extends BaseHintView, BaseActivityHelpView{
    void setData(List<UserWorkExperienceEntity> list);
}

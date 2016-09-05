package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.ResourceEntity;

import java.util.List;

/**
 * Created by Leevin
 * CreateTime: 2016/8/13  9:45
 */
public interface ResourceView extends MvpView{

    void setResourceList(List<ResourceEntity> resourceList);

}

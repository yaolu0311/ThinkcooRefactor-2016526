package com.thinkcoo.mobile.presentation.mvp.views;

import com.thinkcoo.mobile.model.entity.TrainCourseFilter;

/**
 * Created by Leevin
 * CreateTime: 2016/8/20  13:48
 */
public interface TrainSearchResultView<D> extends BaseLceView<D> {

    TrainCourseFilter getFilter();

}

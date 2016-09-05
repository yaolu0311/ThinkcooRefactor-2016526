package com.thinkcoo.mobile.presentation.mvp.views;

import com.thinkcoo.mobile.model.entity.JobFilter;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public interface JobResultView<T> extends  BaseLceView<T>{
    JobFilter getJobFilter();
}

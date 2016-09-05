package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.ClassGroup;
import com.thinkcoo.mobile.model.entity.serverresponse.ClassResponse;

import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
public interface RockCallResultView extends MvpView ,BaseHintView,BaseActivityHelpView{
    void setClassList(List<ClassGroup> classResponseList);
}

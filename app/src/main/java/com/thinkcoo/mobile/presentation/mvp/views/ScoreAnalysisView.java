package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.ClassGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public interface ScoreAnalysisView extends MvpView, BaseHintView , BaseActivityHelpView{
    void setClassList(List<ClassGroup> classGroupList);
}

package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.ClassGroup;
import com.thinkcoo.mobile.model.entity.serverresponse.RockSingleResByUuidResponse;

import java.util.List;

/**
 * Created by Administrator on 2016/8/19
 */
public interface RockCallSingleResultView extends MvpView ,BaseHintView,BaseActivityHelpView {
    void setClassList(List<ClassGroup> classResponseList);
    void setStudentList(RockSingleResByUuidResponse studentResponseList);
    void toggleCheckBoxStatus();
}
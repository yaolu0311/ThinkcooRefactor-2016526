package com.thinkcoo.mobile.presentation.mvp.views;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.model.entity.UserBasicInfo;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import java.util.List;

/**
 * Created by Robert.yao on 2016/3/22.
 */
public interface ManualAddView extends MvpView , BaseHintView , BaseActivityHelpView{
    void setStudentList(List<Student> studentList);
    String getContent();
    void setResult();
    Event getEvent();
}

package com.thinkcoo.mobile.presentation.mvp.views;

import android.widget.RelativeLayout;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import java.util.List;

/**
 * Created by Robert.yao on 2016/3/22.
 */
public interface CreateClassView extends MvpView , BaseHintView , BaseActivityHelpView{

    String getSchoolName();
    String getClassName();
    String getSelectStudentId();
    RelativeLayout getSerchLayout();
    RelativeLayout getNoSerchLayout();
    void setStudents(List<Student> students);
    void setResult();
    Event getevent();
}

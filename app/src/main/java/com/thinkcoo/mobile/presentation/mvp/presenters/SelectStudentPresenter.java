package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.schedule.SerchStudentsCase;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.model.entity.RequestParam.SelectStudentParam;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.presentation.mvp.views.SelectStudentView;
import com.thinkcoo.mobile.presentation.views.PageMachine;
import javax.inject.Inject;

/**
 * Created by Administrator on 2016/8/6.
 */
public class SelectStudentPresenter extends BaseLcePagedPresenter<Student,SelectStudentView<Student>> {

    @Inject
    public SelectStudentPresenter(SerchStudentsCase mSerchStudentsCase) {
        super(mSerchStudentsCase);
    }

    @Override
    protected RequestParam buildLcePagedParams(boolean pullToRefresh, PageMachine pageMachine) {
        return SelectStudentParam.newBuilder().pageMachine(pageMachine).classProfession(getView().getClassProfession()).eventId(getView().getEventId()).school(getView().getSchool()).keyWord("").build();
    }
}

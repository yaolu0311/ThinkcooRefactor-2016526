package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.schedule.MnualAddStudentsCase;
import com.thinkcoo.mobile.domain.schedule.SerchStudentsCase;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.model.entity.RequestParam.SelectStudentParam;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.presentation.mvp.views.MaualAddStudentView;
import com.thinkcoo.mobile.presentation.mvp.views.SelectStudentView;
import com.thinkcoo.mobile.presentation.views.PageMachine;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/8/6.
 */
public class MaualAddStudentPresenter extends BaseLcePagedPresenter<Student,MaualAddStudentView<Student>> {

    @Inject
    public MaualAddStudentPresenter(MnualAddStudentsCase mnualAddStudentsCase) {
        super(mnualAddStudentsCase);
    }

    @Override
    protected RequestParam buildLcePagedParams(boolean pullToRefresh, PageMachine pageMachine) {
        return SelectStudentParam.newBuilder().pageMachine(pageMachine).eventId(getView().getEventId()).school("").classProfession("").keyWord(getView().getContent()).build();
    }
}

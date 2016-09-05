package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.ScheduleModule;
import com.thinkcoo.mobile.presentation.views.activitys.ActiveMemberActivity;
import com.thinkcoo.mobile.presentation.views.activitys.AddEditScheduleActivity;
import com.thinkcoo.mobile.presentation.views.activitys.CreateActivityGroupActivity;
import com.thinkcoo.mobile.presentation.views.activitys.CreateClassActivity;
import com.thinkcoo.mobile.presentation.views.activitys.ManualAddActivity;
import com.thinkcoo.mobile.presentation.views.activitys.MerberClasstActivity;
import com.thinkcoo.mobile.presentation.views.activitys.NoticeActivity;
import com.thinkcoo.mobile.presentation.views.activitys.RockCallResultActivity;
import com.thinkcoo.mobile.presentation.views.activitys.RockCallSingleResultActivity;
import com.thinkcoo.mobile.presentation.views.activitys.RollCallActivity;
import com.thinkcoo.mobile.presentation.views.activitys.ScoreAnalysisActivity;
import com.thinkcoo.mobile.presentation.views.activitys.StudentManageActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseScheduleDetailActivity;
import com.thinkcoo.mobile.presentation.views.fragment.MaualAddFragment;
import com.thinkcoo.mobile.presentation.views.fragment.RockResultStudentListFragment;
import com.thinkcoo.mobile.presentation.views.fragment.StudentFragment;

import dagger.Component;

/**
 * Created by Robert.yao on 2016/6/29.
 */
@Component(dependencies = ApplicationComponent.class , modules ={ScheduleModule.class })
@ActivityScope
public interface ScheduleComponent {

    void inject(StudentManageActivity studentManageActivity);
    void inject(CreateClassActivity createClassActivity);
    void inject(ManualAddActivity manualAddActivity);
    void inject(BaseScheduleDetailActivity baseScheduleDetailActivity);
    void inject(NoticeActivity noticeActivity);
    void inject(RollCallActivity rollCallActivity);
    void inject(RockCallResultActivity rockCallResultActivity);
    void inject(ActiveMemberActivity activeMemberActivity);
    void inject(AddEditScheduleActivity addEditScheduleActivity);
    void inject(CreateActivityGroupActivity createActivityGroupActivity);
    void inject(StudentFragment studentFragment);
    void inject(MaualAddFragment maualAddFragment);
    void inject(RockResultStudentListFragment rockResultStudentListFragment);
    void inject(RockCallSingleResultActivity rockCallSingleResultActivity);

    void inject(ScoreAnalysisActivity scoreAnalysisActivity);
    void inject(MerberClasstActivity merberClasstActivity);

}

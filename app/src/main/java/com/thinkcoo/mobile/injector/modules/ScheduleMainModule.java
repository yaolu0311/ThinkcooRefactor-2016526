package com.thinkcoo.mobile.injector.modules;

import android.app.Activity;
import android.widget.ViewSwitcher;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.domain.schedule.AddScheduleUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;
import com.thinkcoo.mobile.presentation.mvp.presenters.LoadEventListPresenter;
import com.thinkcoo.mobile.presentation.views.component.mydayview.CalendarController;
import com.thinkcoo.mobile.presentation.views.component.mydayview.MyDayView;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/7/29.
 */
@Module
public class ScheduleMainModule {

    private static final String TAG = "ScheduleMainModule";

    private final Activity activity;
    private final ViewSwitcher mViewSwitcher;

    public ScheduleMainModule(Activity activity, ViewSwitcher viewSwitcher) {
        this.activity = activity;
        mViewSwitcher = viewSwitcher;
    }
    @Provides
    @ActivityScope
    Activity activity() {
        return this.activity;
    }
    @Provides
    @ActivityScope
    ViewSwitcher provideViewSwitcher(){
        return this.mViewSwitcher;
    }
    @ActivityScope
    @Provides
    AddScheduleUseCase provideAddScheduleUseCase(ScheduleRepository scheduleRepository, @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread){
        return new AddScheduleUseCase(mUiThread,mExecutorThread,scheduleRepository);
    }
    @ActivityScope
    @Provides
    CalendarController provideCalendarController(Activity activity){
        return CalendarController.getInstance(activity);
    }

    @ActivityScope
    @Provides
    @Named("current1")
    MyDayView provide1CurrentMyDayView(Activity activity, CalendarController calendarController, ViewSwitcher viewSwitcher, LoadEventListPresenter loadEventListPresenter){
        ThinkcooLog.e("See","=== current1 ===");
        return new MyDayView(activity,calendarController,viewSwitcher,loadEventListPresenter,1);
    }

    @ActivityScope
    @Provides
    @Named("next1")
    MyDayView provide1NextMyDayView(Activity activity, CalendarController calendarController, ViewSwitcher viewSwitcher, LoadEventListPresenter loadEventListPresenter){
        ThinkcooLog.e("See","=== next1 ===");
        return new MyDayView(activity,calendarController,viewSwitcher,loadEventListPresenter,1);
    }

    @ActivityScope
    @Provides
    @Named("current7")
    MyDayView provide7CurrentMyDayView(Activity activity, CalendarController calendarController, ViewSwitcher viewSwitcher, LoadEventListPresenter loadEventListPresenter){
        ThinkcooLog.e("See","=== current7 ===");
        return new MyDayView(activity,calendarController,viewSwitcher,loadEventListPresenter,7);
    }

    @ActivityScope
    @Provides
    @Named("next7")
    MyDayView provide7NextMyDayView(Activity activity, CalendarController calendarController, ViewSwitcher viewSwitcher, LoadEventListPresenter loadEventListPresenter){
        ThinkcooLog.e("See","=== next7 ===");
        return new MyDayView(activity,calendarController,viewSwitcher,loadEventListPresenter,7);
    }


}
